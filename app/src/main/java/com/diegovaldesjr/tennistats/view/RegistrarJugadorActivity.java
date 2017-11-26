package com.diegovaldesjr.tennistats.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

import com.diegovaldesjr.tennistats.R;
import com.diegovaldesjr.tennistats.io.TennisApiAdapter;
import com.diegovaldesjr.tennistats.io.response.JugadorResponse;
import com.diegovaldesjr.tennistats.model.Jugador;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrarJugadorActivity extends AppCompatActivity {

    SharedPreferences user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_jugador);

        user = getSharedPreferences("user", Context.MODE_PRIVATE);
        showToolbar(getResources().getString(R.string.app_name), true);
    }

    public void registrarJugador(View view){

        TextInputEditText nombre = (TextInputEditText) findViewById(R.id.nombreJugador);
        TextInputEditText apellido = (TextInputEditText) findViewById(R.id.apellidoJugador);
        TextInputEditText edad = (TextInputEditText) findViewById(R.id.edadJugador);

        Spinner genero = (Spinner) findViewById(R.id.spinnerGeneroJugador);
        Spinner mano = (Spinner) findViewById(R.id.spinnerManoJugador);

        Jugador jugador = null;

        jugador = new Jugador(
                Integer.parseInt(edad.getText().toString()),
                nombre.getText().toString(),
                apellido.getText().toString(),
                mano.getSelectedItem().toString(),
                genero.getSelectedItem().toString()
        );

        Call<JugadorResponse> call = TennisApiAdapter.getApiService().createJugador("Bearer "+user.getString("access_token", ""), jugador);

        call.enqueue(new Callback<JugadorResponse>() {
            @Override
            public void onResponse(Call<JugadorResponse> call, Response<JugadorResponse> response) {
                if(response.isSuccessful()){
                    finish();
                }
            }

            @Override
            public void onFailure(Call<JugadorResponse> call, Throwable t) {
                Toast.makeText(RegistrarJugadorActivity.this, t.getMessage().toString(),
                        Toast.LENGTH_SHORT).show();
                Log.d("Error", "Error Respuesta en JSON: " +t.getMessage());
            }
        });

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
}
