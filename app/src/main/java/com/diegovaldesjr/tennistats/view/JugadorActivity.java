package com.diegovaldesjr.tennistats.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.diegovaldesjr.tennistats.R;
import com.diegovaldesjr.tennistats.adapter.PartidoAdapterRecyclerView;
import com.diegovaldesjr.tennistats.io.TennisApiAdapter;
import com.diegovaldesjr.tennistats.io.response.JugadorResponse;
import com.diegovaldesjr.tennistats.io.response.PartidoResponse;
import com.diegovaldesjr.tennistats.model.Jugador;
import com.diegovaldesjr.tennistats.model.Partido;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JugadorActivity extends AppCompatActivity {

    SharedPreferences user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jugador);

        user = getSharedPreferences("user", Context.MODE_PRIVATE);
        Jugador jugador = (Jugador) getIntent().getSerializableExtra("jugador");

        cargarDatos(jugador.getIdJugador());

        showToolbar(getResources().getString(R.string.app_name), true);
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

    public void cargarDatos(int id){
        final ArrayList<Partido> partidos = new ArrayList<>();

        Call<JugadorResponse> call = TennisApiAdapter.getApiService().getJugador(id, "Bearer "+user.getString("access_token", ""));

        call.enqueue(new Callback<JugadorResponse>() {
            @Override
            public void onResponse(Call<JugadorResponse> call, Response<JugadorResponse> response) {
                if(response.isSuccessful()){
                    JugadorResponse jugador = response.body();

                    TextView nombre = (TextView) findViewById(R.id.nombreJugador);
                    TextView edad = (TextView) findViewById(R.id.edadJugador);
                    TextView genero = (TextView) findViewById(R.id.generoJugador);
                    TextView mano = (TextView) findViewById(R.id.manoJugador);

                    nombre.setText(jugador.getNombre()+" "+jugador.getApellido());
                    edad.setText(jugador.getEdad());
                    genero.setText(jugador.getGenero());
                    mano.setText(jugador.getManoDiestra());

                    ArrayList<PartidoResponse> data = jugador.getPartidos();

                    for(int i=0; i<data.size(); i++){
                        PartidoResponse row = data.get(i);

                        try {
                            SimpleDateFormat parseador = new SimpleDateFormat("yyyy-MM-dd");
                            Date fecha = parseador.parse(row.getFecha());

                            partidos.add(new Partido(
                                    Integer.parseInt(row.getIdPartido()),
                                    Integer.parseInt(jugador.getIdJugador()),
                                    jugador.getNombre()+" "+jugador.getApellido(),
                                    fecha,
                                    row.getCategoria(),
                                    row.getSets()
                            ));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    actualizarRecyclerView(partidos);
                }
            }

            @Override
            public void onFailure(Call<JugadorResponse> call, Throwable t) {
                Toast.makeText(JugadorActivity.this, t.getMessage(),
                        Toast.LENGTH_SHORT).show();
                Log.d("Error", "Error Respuesta en JSON: " +t.getMessage());
            }
        });
    }

    public void actualizarRecyclerView(ArrayList<Partido> partidos){
        RecyclerView partidosRecycler = (RecyclerView) findViewById(R.id.jugadorPartidoRecycler);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(JugadorActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        partidosRecycler.setLayoutManager(linearLayoutManager);

        PartidoAdapterRecyclerView partidoAdapterRecyclerView = new PartidoAdapterRecyclerView(partidos, R.layout.partido_list, this);
        partidosRecycler.setAdapter(partidoAdapterRecyclerView);
    }
}
