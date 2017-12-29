package com.diegovaldesjr.tennistats.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.diegovaldesjr.tennistats.R;
import com.diegovaldesjr.tennistats.data.SessionPrefs;
import com.diegovaldesjr.tennistats.io.TennisApiAdapter;
import com.diegovaldesjr.tennistats.io.response.LoginResponse;
import com.diegovaldesjr.tennistats.io.body.LoginBody;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    private ImageView logo;
    private EditText username;
    private EditText contraseña;
    private View mProgressView;
    private View mLoginFormView;
    private TextInputLayout mFloatLabelUserId;
    private TextInputLayout mFloatLabelPassword;
    private TextView registrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        logo = (ImageView) findViewById(R.id.logo);
        username = (EditText) findViewById(R.id.username);
        contraseña = (EditText) findViewById(R.id.password);

        Button boton = (Button) findViewById(R.id.login);
        boton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isOnline()) {
                    showLoginError(getString(R.string.error_network));
                    return;
                }
                attemptLogin();
            }
        });

        mFloatLabelUserId = (TextInputLayout) findViewById(R.id.float_label_user_id);
        mFloatLabelPassword = (TextInputLayout) findViewById(R.id.float_label_password);

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        registrar = (TextView) findViewById(R.id.registrar_usuario);
        registrar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showRegistrar();
            }
        });
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {

        // Reset errors.
        mFloatLabelUserId.setError(null);
        mFloatLabelPassword.setError(null);

        // Store values at the time of the login attempt.
        String username = this.username.getText().toString();
        String password = this.contraseña.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            showLoginError(getString(R.string.error_field_required));
            focusView = mFloatLabelPassword;
            cancel = true;
        }else if(!isPasswordValid(password)){
            showLoginError(getString(R.string.error_invalid_password));
            focusView = mFloatLabelPassword;
            cancel = true;
        }

        // Check for a valid username.
        if (TextUtils.isEmpty(username)) {
            showLoginError(getString(R.string.error_field_required));
            focusView = mFloatLabelUserId;
            cancel = true;
        } else if (!isUsernameValid(username)) {
            showLoginError(getString(R.string.error_invalid_user_id));
            focusView = mFloatLabelUserId;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);

            Call<LoginResponse> call = TennisApiAdapter.getApiService().getLogin(new LoginBody(
                    username,
                    password
            ));

            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, retrofit2.Response<LoginResponse> response) {
                    // Mostrar progreso
                    showProgress(false);

                    if(response.isSuccessful()){
                        LoginResponse data =  response.body();

                        SessionPrefs.get(LoginActivity.this).saveAffiliate(data);
                        showAppointmentsScreen();
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    showProgress(false);
                    showLoginError(t.getMessage());
                }
            });


        }
    }

    private boolean isUsernameValid(String username) {
        //TODO: Replace this with your own logic
        return username.length() > 0;
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 0;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);

        int visibility = show ? View.GONE : View.VISIBLE;
        logo.setVisibility(visibility);
        mLoginFormView.setVisibility(visibility);
    }

    private void showAppointmentsScreen() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void showLoginError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }

    private boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }

    public void showRegistrar(){
        startActivity(new Intent(this, RegistrarActivity.class));
        finish();
    }
}

