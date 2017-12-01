package com.diegovaldesjr.tennistats.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.diegovaldesjr.tennistats.R;
import com.diegovaldesjr.tennistats.io.TennisApiAdapter;
import com.diegovaldesjr.tennistats.io.response.LoginResponse;
import com.diegovaldesjr.tennistats.model.UserCredentials;

import retrofit2.Call;
import retrofit2.Callback;

public class LoginActivity extends AppCompatActivity {

    SharedPreferences user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        user = getSharedPreferences("user", Context.MODE_PRIVATE);
    }

    public void avanzarHome(){
        Intent intent = new Intent(this, ContainerActivity.class);
        startActivity(intent);
    }

    public void iniciarSesion(View view){

        TextInputEditText email = (TextInputEditText) findViewById(R.id.username);
        TextInputEditText password = (TextInputEditText) findViewById(R.id.password);

        Call<LoginResponse> call = TennisApiAdapter.getApiService().getLogin(new UserCredentials(
                "password",
                "2",
                "f1pzPBkliwYnfWfrel3PeMbxAmRIyMFnVx5Egbnt",
                "luis",
                "luis123"
                //email.getText().toString()
                //password.getText().toString()
        ));

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, retrofit2.Response<LoginResponse> response) {
                if(response.isSuccessful()){
                    LoginResponse data =  response.body();

                    SharedPreferences.Editor editor = user.edit();
                    editor.putString("access_token",data.getAccess_token());
                    editor.commit();

                    avanzarHome();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, t.getMessage(),
                        Toast.LENGTH_SHORT).show();
                Log.d("Error", "Error Respuesta en JSON: " +t.getMessage());
            }
        });
    }
}
