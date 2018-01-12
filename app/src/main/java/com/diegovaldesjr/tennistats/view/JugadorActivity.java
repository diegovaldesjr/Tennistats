package com.diegovaldesjr.tennistats.view;

import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.diegovaldesjr.tennistats.R;
import com.diegovaldesjr.tennistats.adapter.PartidoAdapterRecyclerView;
import com.diegovaldesjr.tennistats.data.SessionPrefs;
import com.diegovaldesjr.tennistats.data.TennistatsContract;
import com.diegovaldesjr.tennistats.data.TennistatsDbHelper;
import com.diegovaldesjr.tennistats.model.Jugador;
import com.diegovaldesjr.tennistats.model.Partido;
import com.diegovaldesjr.tennistats.model.Set;

import java.util.ArrayList;

public class JugadorActivity extends AppCompatActivity {

    private TextView nombre;
    private TextView edad;
    private TextView genero;
    private TextView mano;
    private RecyclerView partidosRecycler;
    private Cursor c;
    private TennistatsDbHelper db;
    private Jugador jugador;
    private String idUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jugador);

        showToolbar(getResources().getString(R.string.app_name), true);

        nombre = (TextView) findViewById(R.id.nombreJugador);
        edad = (TextView) findViewById(R.id.edadJugador);
        genero = (TextView) findViewById(R.id.generoJugador);
        mano = (TextView) findViewById(R.id.manoJugador);
        partidosRecycler = (RecyclerView) findViewById(R.id.jugadorPartidoRecycler);

        jugador = (Jugador) getIntent().getSerializableExtra("jugador");
        db = new TennistatsDbHelper(JugadorActivity.this);
        idUsuario = SessionPrefs.get(this).getUsername();

        nombre.setText(jugador.getNombre()+" "+jugador.getApellido());
        edad.setText(String.valueOf(jugador.getEdad()));
        genero.setText(jugador.getGenero());
        mano.setText(jugador.getManoDiestra());

        loadPartidos();

        Button button = (Button) findViewById(R.id.verEstadisticasJugador);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

    public void loadPartidos(){
        new PartidosLoadTask().execute();
    }

    public void actualizarRecyclerView(ArrayList<Partido> partidos){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(JugadorActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        partidosRecycler.setLayoutManager(linearLayoutManager);

        PartidoAdapterRecyclerView partidoAdapterRecyclerView = new PartidoAdapterRecyclerView(partidos, R.layout.partido_list, this, db);
        partidosRecycler.setAdapter(partidoAdapterRecyclerView);
    }

    public void formatearData(){
        ArrayList<Partido> partidos = new ArrayList<>();

        if(c != null){
            for(int i=0; i<c.getCount(); i++){
                c.moveToPosition(i);

                Partido partido = new Partido(
                        c.getInt(0),
                        c.getInt(c.getColumnIndex(TennistatsContract.PartidoEntry.ID_JUGADOR)),
                        c.getString(c.getColumnIndex(TennistatsContract.PartidoEntry.FECHA)),
                        c.getString(c.getColumnIndex(TennistatsContract.PartidoEntry.CATEGORIA)),
                        c.getString(c.getColumnIndex(TennistatsContract.PartidoEntry.ID_USUARIO)),
                        null,
                        new Jugador(
                                c.getInt(c.getColumnIndex(TennistatsContract.PartidoEntry.ID_JUGADOR)),
                                c.getString(c.getColumnIndex(TennistatsContract.JugadorEntry.NOMBRE)),
                                c.getString(c.getColumnIndex(TennistatsContract.JugadorEntry.APELLIDO))
                        )
                );


                ArrayList<Set> sets = new ArrayList<>();

                for(int j=i; partido.getIdPartido() == c.getInt(c.getColumnIndex(TennistatsContract.SetEntry.ID_PARTIDO)) && j<c.getCount(); ){

                    sets.add(new Set(
                            //7
                            c.getInt(7),
                            c.getInt(c.getColumnIndex(TennistatsContract.SetEntry.ID_PARTIDO)),
                            c.getInt(c.getColumnIndex(TennistatsContract.SetEntry.NUMERO)),
                            c.getInt(c.getColumnIndex(TennistatsContract.SetEntry.PUNTAJEJ)),
                            c.getInt(c.getColumnIndex(TennistatsContract.SetEntry.PUNTAJEO)),
                            c.getString(c.getColumnIndex(TennistatsContract.SetEntry.GANADOR))
                    ));
                    i=j++;
                    if(j<c.getCount())
                        c.moveToPosition(j);
                }
                partido.setSets(sets);
                partidos.add(partido);
            }
            actualizarRecyclerView(partidos);
        }
    }

    private void showError(String error) {
        Toast.makeText(JugadorActivity.this, error, Toast.LENGTH_LONG).show();
    }

    private class PartidosLoadTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return db.getPartidosPorJugador(String.valueOf(jugador.getIdJugador()), idUsuario);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.getCount() > 0) {
                c = cursor;
                formatearData();
            } else {
                String string = "No hay partidos cargados";
                showError(string);
                c = cursor;
            }
        }
    }
}
