package com.diegovaldesjr.tennistats.view.fragments;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


/**
 * Created by diego on 25/11/2017.
 */

public class TabPartidos extends Fragment {

    private TennistatsDbHelper db;
    private Cursor c;

    private RecyclerView partidosRecycler;
    private String idUsuario;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_partidos, container, false);

        partidosRecycler = (RecyclerView) view.findViewById(R.id.partidoRecycler);
        db = new TennistatsDbHelper(getContext());
        idUsuario = SessionPrefs.get(getActivity()).getUsername();

        loadPartidos();

        return view;
    }

    public void loadPartidos(){
        new PartidosLoadTask().execute();
    }

    public void formatearData(){
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
            actualizarRecyclerView(partidos);
        }
    }

    public void actualizarRecyclerView(ArrayList<Partido> partidos){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        partidosRecycler.setLayoutManager(linearLayoutManager);

        PartidoAdapterRecyclerView partidoAdapterRecyclerView = new PartidoAdapterRecyclerView(partidos, R.layout.partido_list, getActivity(), db);
        partidosRecycler.setAdapter(partidoAdapterRecyclerView);
    }

    private void showError(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_LONG).show();
    }

    private class PartidosLoadTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return db.getAllPartidos(idUsuario);
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
