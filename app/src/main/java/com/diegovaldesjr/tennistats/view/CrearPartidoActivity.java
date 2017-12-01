package com.diegovaldesjr.tennistats.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.diegovaldesjr.tennistats.R;
import com.diegovaldesjr.tennistats.io.TennisApiAdapter;
import com.diegovaldesjr.tennistats.io.response.JugadorResponse;
import com.diegovaldesjr.tennistats.io.response.PartidoResponse;
import com.diegovaldesjr.tennistats.model.Jugador;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CrearPartidoActivity extends AppCompatActivity {

    SharedPreferences user;

    Spinner spinnerJugador;

    ArrayList<Jugador> jugadores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_partido);

        user = getSharedPreferences("user", Context.MODE_PRIVATE);

        spinnerJugador = (Spinner) findViewById(R.id.spinnerJugadorGameconfig);
        jugadores = new ArrayList<>();
        obtenerJugadores();

        showToolbar(getResources().getString(R.string.app_name), true);

        TextView fecha = (TextView) findViewById(R.id.fechaGameConfig);

        SimpleDateFormat parseador = new SimpleDateFormat("yyyy-MM-dd");
        fecha.setText(parseador.format(new Date()));
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

    public void obtenerJugadores() {

        Call<ArrayList<JugadorResponse>> call = TennisApiAdapter.getApiService().getJugadores("Bearer "+user.getString("access_token", ""));

        call.enqueue(new Callback<ArrayList<JugadorResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<JugadorResponse>> call, Response<ArrayList<JugadorResponse>> response) {
                if(response.isSuccessful()){
                    ArrayList<JugadorResponse> data = response.body();

                    for(int i=0; i<data.size(); i++){
                        JugadorResponse row = data.get(i);
                        jugadores.add(new Jugador(
                                Integer.parseInt(row.getIdJugador()),
                                row.getNombre(),
                                row.getApellido()
                        ));
                    }
                    addItemsOnSpinnerJugadores(jugadores, jugadores.size());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<JugadorResponse>> call, Throwable t) {
                Toast.makeText(CrearPartidoActivity.this, t.getMessage(),
                        Toast.LENGTH_SHORT).show();
                Log.d("Error", "Error Respuesta en JSON: " +t.getMessage());
            }
        });
    }

    public void addItemsOnSpinnerJugadores(ArrayList<Jugador> array, int n){

        List<String> dynamicList = new ArrayList<String>();

        for (int i=0; i<n; i++) {
            Jugador row = array.get(i);
            dynamicList.add(row.getNombre()+" "+row.getApellido());
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, dynamicList);
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerJugador.setAdapter(dataAdapter);
    }

    public void crearPartido(View view){

        int id=-1;
        Spinner categoria = (Spinner) findViewById(R.id.spinnerCategoriaGameconfig);

        for(int i=0; i<jugadores.size(); i++){
            //Busco la ocurrencia
            Jugador row = jugadores.get(i);
            if((spinnerJugador.getSelectedItem().toString().indexOf(row.getNombre()) != -1) && (spinnerJugador.getSelectedItem().toString().indexOf(row.getApellido()) != -1)){
                id=row.getIdJugador();
            }
        }

        if(id >= 0){
            Date fecha = new Date();

            PartidoBody partido = new PartidoBody(
                    id,
                    categoria.getSelectedItem().toString(),
                    new SimpleDateFormat("yyyy-MM-dd").format(fecha)
            );

            Call<PartidoResponse> call = TennisApiAdapter.getApiService().createPartido("Bearer "+user.getString("access_token", ""), partido);

            call.enqueue(new Callback<PartidoResponse>() {
                @Override
                public void onResponse(Call<PartidoResponse> call, Response<PartidoResponse> response) {
                    if(response.isSuccessful()){
                        avanzarCancha(response.body());
                        //finish();
                    }
                }

                @Override
                public void onFailure(Call<PartidoResponse> call, Throwable t) {
                    Toast.makeText(CrearPartidoActivity.this, t.getMessage(),
                            Toast.LENGTH_SHORT).show();
                    Log.d("Error", "Error Respuesta en JSON: " +t.getMessage());
                }
            });
        }
    }

    public void avanzarCancha(PartidoResponse partido){
        Intent intent = new Intent(this, CanchaActivity.class);

        intent.putExtra("partido", partido);
        startActivity(intent);
    }

    public class PartidoBody{
        private int idJugador;
        private String categoria, fecha;
        //private Date fecha;

        public PartidoBody(int idJugador, String categoria, String fecha){
            this.idJugador = idJugador;
            this.categoria = categoria;
            this.fecha = fecha;
        }

        public int getIdJugador() {
            return idJugador;
        }

        public void setIdJugador(int idJugador) {
            this.idJugador = idJugador;
        }

        public String getCategoria() {
            return categoria;
        }

        public void setCategoria(String categoria) {
            this.categoria = categoria;
        }

        public String getFecha() {
            return fecha;
        }

        public void setFecha(String fecha) {
            this.fecha = fecha;
        }
    }
}
