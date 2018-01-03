package com.diegovaldesjr.tennistats.view;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.diegovaldesjr.tennistats.R;
import com.diegovaldesjr.tennistats.io.TennisApiAdapter;
import com.diegovaldesjr.tennistats.io.body.LoginBody;
import com.diegovaldesjr.tennistats.io.response.RegistrarResponse;

import retrofit2.Call;
import retrofit2.Callback;

public class RegistrarActivity extends AppCompatActivity {

    private EditText username;
    private EditText contraseña;
    private TextInputLayout mFloatLabelUserId;
    private TextInputLayout mFloatLabelPassword;
    private View mProgressView;
    private View mRegistrarFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        showToolbar("", true);

        username = (EditText) findViewById(R.id.usernameRegistrar);
        contraseña = (EditText) findViewById(R.id.passwordRegistrarse);
        Button button = (Button) findViewById(R.id.boton_registrar);

        mFloatLabelUserId = (TextInputLayout) findViewById(R.id.float_label_username_registrar);
        mFloatLabelPassword = (TextInputLayout) findViewById(R.id.float_label_password_registrar);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptRegistrar();
            }
        });

        mProgressView = findViewById(R.id.login_progress);
        mRegistrarFormView = findViewById(R.id.registrar_form);
    }

    public void showToolbar(String tittle, boolean upButton){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        //Soporte para versiones anteriores
        setSupportActionBar(toolbar);

        //Actualiza texto del toolbar
        getSupportActionBar().setTitle(tittle);

        //En caso de que tenga boton lo mostrara
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
    }

    private void attemptRegistrar() {

        // Reset errors.
        mFloatLabelUserId.setError(null);
        mFloatLabelPassword.setError(null);

        String username = this.username.getText().toString();
        String password = this.contraseña.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(password)) {
            showRegistrarError(getString(R.string.error_field_required));
            focusView = mFloatLabelPassword;
            cancel = true;
        }else if(!isValid(password)){
            showRegistrarError(getString(R.string.error_invalid_password));
            focusView = mFloatLabelPassword;
            cancel = true;
        }

        // Check for a valid username.
        if (TextUtils.isEmpty(username)) {
            showRegistrarError(getString(R.string.error_field_required));
            focusView = mFloatLabelUserId;
            cancel = true;
        } else if (!isValid(username)) {
            showRegistrarError(getString(R.string.error_invalid_user_id));
            focusView = mFloatLabelUserId;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            showProgress(true);

            Call<RegistrarResponse> call = TennisApiAdapter.getApiService().registrar(new LoginBody(
                    username,
                    password
            ));

            call.enqueue(new Callback<RegistrarResponse>() {
                @Override
                public void onResponse(Call<RegistrarResponse> call, retrofit2.Response<RegistrarResponse> response) {
                    // Mostrar progreso
                    showProgress(false);

                    if(response.isSuccessful()){
                        showRegistrarError("Registro exitoso.");
                        showLogin();
                    }
                }

                @Override
                public void onFailure(Call<RegistrarResponse> call, Throwable t) {
                    showProgress(false);
                    showRegistrarError(t.getMessage());
                }
            });


        }
    }

    private boolean isValid(String username) {
        return username.length() > 0;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);

        int visibility = show ? View.GONE : View.VISIBLE;
        mRegistrarFormView.setVisibility(visibility);
    }

    private void showRegistrarError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }

    public void showLogin(){
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
