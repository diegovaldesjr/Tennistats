package com.diegovaldesjr.tennistats.io;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.diegovaldesjr.tennistats.data.SessionPrefs;
import com.diegovaldesjr.tennistats.data.TennistatsContract;
import com.diegovaldesjr.tennistats.data.TennistatsDbHelper;
import com.diegovaldesjr.tennistats.io.body.JugadaBody;
import com.diegovaldesjr.tennistats.io.body.JugadorBody;
import com.diegovaldesjr.tennistats.io.body.PartidoBody;
import com.diegovaldesjr.tennistats.io.body.SaqueBody;
import com.diegovaldesjr.tennistats.io.body.SetBody;
import com.diegovaldesjr.tennistats.io.response.JugadorResponse;
import com.diegovaldesjr.tennistats.io.response.PartidoResponse;
import com.diegovaldesjr.tennistats.io.response.SetResponse;
import com.diegovaldesjr.tennistats.io.response.StatusResponse;
import com.diegovaldesjr.tennistats.model.Jugador;
import com.diegovaldesjr.tennistats.model.Partido;
import com.diegovaldesjr.tennistats.model.Set;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by diego on 29/12/2017.
 */

public class ApiConsultas {

    private TennistatsDbHelper db;
    private String idUsuario, token;
    private Activity activity;

    public ApiConsultas(TennistatsDbHelper db, String idUsuario, String token, Activity activity) {
        this.db = db;
        this.idUsuario = idUsuario;
        this.token = token;
        this.activity = activity;

        new PartidosLoadTask().execute();
        new JugadoresLoadTask().execute();
    }

    public void obtenerSetsAPI(int idPartido, final ArrayList<Set> sets){
        Call<StatusResponse> call = TennisApiAdapter.getApiService().getSets(idPartido,"Bearer "+token);

        call.enqueue(new Callback<StatusResponse>() {
            @Override
            public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                if(response.isSuccessful()) {
                    Log.d("API", "Sets obtenidos");
                    StatusResponse data = response.body();

                    ArrayList<SetResponse> dataSets = data.getSets();

                    for(int i=0; i<sets.size(); i++){
                        SetBody setBody = new SetBody(
                                sets.get(i).getPuntajej(),
                                sets.get(i).getPuntajeo()
                        );

                        actualizarSetsAPI(Integer.parseInt(dataSets.get(i).getIdSet()), setBody);

                        new JugadasLoadTask(dataSets.get(i).getIdSet()).execute(String.valueOf(sets.get(i).getIdSet()));
                        new SaquesLoadTask(dataSets.get(i).getIdSet()).execute(String.valueOf(sets.get(i).getIdSet()));
                    }
                }
            }

            @Override
            public void onFailure(Call<StatusResponse> call, Throwable t) {
                Log.d("Error", "Error Respuesta en JSON: " +t.getMessage());
            }
        });
    }

    public void actualizarSetsAPI(int idSet, SetBody set){
        Call<StatusResponse> call = TennisApiAdapter.getApiService().actualizarSets(idSet, "Bearer "+token, set);

        call.enqueue(new Callback<StatusResponse>() {
            @Override
            public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                if(response.isSuccessful()){
                    Log.d("API", "Set actualizado");
                }
            }

            @Override
            public void onFailure(Call<StatusResponse> call, Throwable t) {
                Log.d("Error", "Error Respuesta en JSON: " +t.getMessage());
            }
        });
    }

    public void crearPartidoAPI(final Partido partido){
        PartidoBody partidoBody = new PartidoBody(
                partido.getIdJugador(),
                partido.getCategoria(),
                partido.getFecha()

        );

        Call<StatusResponse> call = TennisApiAdapter.getApiService().createPartido("Bearer "+token, partidoBody);

        call.enqueue(new Callback<StatusResponse>() {
            @Override
            public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                if(response.isSuccessful()){
                    Log.d("API", "Partido guardado");

                    StatusResponse data = response.body();
                    int idPartido = Integer.parseInt(data.getPartido().getIdPartido());

                    obtenerSetsAPI(idPartido, partido.getSets());

                }
            }

            @Override
            public void onFailure(Call<StatusResponse> call, Throwable t) {
                Log.d("Error", "Error Respuesta en JSON: " +t.getMessage());
            }
        });
    }

    public void formatearPartidos(Cursor c){
        ArrayList<Partido> partidos = new ArrayList<>();

        if(c != null){
            for(int i=0; i<c.getCount(); i++){
                c.moveToPosition(i);

                Partido partido = new Partido(
                        c.getInt(0),
                        c.getInt(c.getColumnIndex(TennistatsContract.PartidoEntry.ID_JUGADOR)),
                        c.getString(c.getColumnIndex(TennistatsContract.PartidoEntry.CATEGORIA)),
                        c.getString(c.getColumnIndex(TennistatsContract.PartidoEntry.FECHA)),
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

            for(int i=0; i<partidos.size(); i++){
                crearPartidoAPI(partidos.get(i));
            }
        }
    }

    private class PartidosLoadTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return db.getAllPartidos(idUsuario);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.getCount() > 0) {
                formatearPartidos(cursor);
            } else {
                Log.d("APICONSULTAS", "Error SQLITE PARTIDO");
            }
        }
    }

    public void crearJugadorAPI(JugadorBody jugador){
        Call<StatusResponse> call = TennisApiAdapter.getApiService().createJugador("Bearer "+ token, jugador);

        call.enqueue(new Callback<StatusResponse>() {
            @Override
            public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                if(response.isSuccessful()){
                    Log.d("API", "Jugador guardado");
                }
            }

            @Override
            public void onFailure(Call<StatusResponse> call, Throwable t) {
                //Toast.makeText(CrearJugadorActivity.this, t.getMessage(),Toast.LENGTH_SHORT).show();
                Log.d("Error", "Error Respuesta en JSON: " +t.getMessage());
            }
        });
    }

    public void formatearJugadores(Cursor c){

        for(int i=0; i<c.getCount(); i++){
            c.moveToPosition(i);

            JugadorBody jugador = new JugadorBody(
                    c.getString(c.getColumnIndex(TennistatsContract.JugadorEntry.NOMBRE)),
                    c.getString(c.getColumnIndex(TennistatsContract.JugadorEntry.APELLIDO)),
                    c.getInt(c.getColumnIndex(TennistatsContract.JugadorEntry.EDAD)),
                    c.getString(c.getColumnIndex(TennistatsContract.JugadorEntry.MANO_DIESTRA)),
                    c.getString(c.getColumnIndex(TennistatsContract.JugadorEntry.GENERO))
            );

            crearJugadorAPI(jugador);
        }

    }

    private class JugadoresLoadTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return db.getAllJugadores(idUsuario);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.getCount() > 0) {
                formatearJugadores(cursor);
            } else {
                Log.d("APICONSULTAS", "Error SQLITE JUGADORES");
            }
        }
    }

    private class JugadasLoadTask extends AsyncTask<String, Void, Cursor> {

        private String idSet;

        public JugadasLoadTask(String idSet){
            this.idSet = idSet;
        }

        @Override
        protected Cursor doInBackground(String... params) {
            return db.getJugadasSet(Integer.parseInt(params[0]));
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.getCount() > 0) {
                for(int i=0; i<cursor.getCount(); i++){
                    cursor.moveToPosition(i);

                    JugadaBody jugadaBody = new JugadaBody(
                            Integer.parseInt(idSet),
                            i,
                            cursor.getInt(cursor.getColumnIndex(TennistatsContract.JugadaEntry.ZONA)),
                            cursor.getString(cursor.getColumnIndex(TennistatsContract.JugadaEntry.TIPO_GOLPE)),
                            cursor.getString(cursor.getColumnIndex(TennistatsContract.JugadaEntry.TIPO_JUGADA))
                    );

                    crearJugadaAPI(jugadaBody);
                }
            } else {
                Log.d("APICONSULTAS", "Error SQLITE JUGADA");
            }
        }
    }

    private class SaquesLoadTask extends AsyncTask<String, Void, Cursor> {

        private String idSet;

        public SaquesLoadTask(String idSet){
            this.idSet = idSet;
        }

        @Override
        protected Cursor doInBackground(String... params) {
            return db.getSaquesSet(Integer.parseInt(params[0]));
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.getCount() > 0) {
                for(int i=0; i<cursor.getCount(); i++){
                    cursor.moveToPosition(i);

                    SaqueBody saqueBody = new SaqueBody(
                            Integer.parseInt(idSet),
                            i,
                            cursor.getInt(cursor.getColumnIndex(TennistatsContract.SaqueEntry.ZONA)),
                            cursor.getString(cursor.getColumnIndex(TennistatsContract.SaqueEntry.TIPO_GOLPE)),
                            cursor.getString(cursor.getColumnIndex(TennistatsContract.SaqueEntry.TIPO_SAQUE))
                    );

                    crearSaqueAPI(saqueBody);
                }

            } else {
                Log.d("APICONSULTAS", "Error SQLITE SAQUE");
            }
        }
    }

    public void crearSaqueAPI(SaqueBody saqueBody){
        Call<StatusResponse> call = TennisApiAdapter.getApiService().createSaque("Bearer "+ token, saqueBody);

        call.enqueue(new Callback<StatusResponse>() {
            @Override
            public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                if(response.isSuccessful()){
                    Log.d("API", "Saque guardado");
                }
            }

            @Override
            public void onFailure(Call<StatusResponse> call, Throwable t) {
                //Toast.makeText(CrearJugadorActivity.this, t.getMessage(),Toast.LENGTH_SHORT).show();
                Log.d("Error", "Error Respuesta en JSON: " +t.getMessage());
            }
        });
    }

    public void crearJugadaAPI(JugadaBody jugadaBody){
        Call<StatusResponse> call = TennisApiAdapter.getApiService().createJugada("Bearer "+ token, jugadaBody);

        call.enqueue(new Callback<StatusResponse>() {
            @Override
            public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                if(response.isSuccessful()){
                    Log.d("API", "Jugada guardado");
                }
            }

            @Override
            public void onFailure(Call<StatusResponse> call, Throwable t) {
                //Toast.makeText(CrearJugadorActivity.this, t.getMessage(),Toast.LENGTH_SHORT).show();
                Log.d("Error", "Error Respuesta en JSON: " +t.getMessage());
            }
        });
    }
}
