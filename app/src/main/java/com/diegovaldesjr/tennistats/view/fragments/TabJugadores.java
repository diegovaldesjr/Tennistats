package com.diegovaldesjr.tennistats.view.fragments;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.diegovaldesjr.tennistats.R;
import com.diegovaldesjr.tennistats.adapter.JugadorAdapterRecyclerView;
import com.diegovaldesjr.tennistats.data.SessionPrefs;
import com.diegovaldesjr.tennistats.data.TennistatsDbHelper;


/**
 * Created by diego on 25/11/2017.
 */

public class TabJugadores extends Fragment {

    private TennistatsDbHelper db;
    private Cursor c;

    private RecyclerView jugadoresRecycler;
    private String idUsuario;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_jugadores, container, false);

        jugadoresRecycler = (RecyclerView) view.findViewById(R.id.jugadoresRecycler);
        db = new TennistatsDbHelper(getContext());
        idUsuario = SessionPrefs.get(getActivity()).getUsername();

        loadJugadores();

        return view;
    }

    public void loadJugadores(){
        new JugadoresLoadTask().execute();
    }

    public void actualizarRecyclerView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        jugadoresRecycler.setLayoutManager(linearLayoutManager);

        JugadorAdapterRecyclerView jugadorAdapterRecyclerView = new JugadorAdapterRecyclerView(c, R.layout.jugador_list, getActivity());
        jugadoresRecycler.setAdapter(jugadorAdapterRecyclerView);
    }

    private void showError(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_LONG).show();
    }

    private class JugadoresLoadTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return db.getAllJugadores(idUsuario);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.getCount() > 0) {
                c = cursor;
                actualizarRecyclerView();
            } else {
                String string = "No hay jugadores cargados";
                showError(string);
                c = cursor;
            }
        }
    }
}
