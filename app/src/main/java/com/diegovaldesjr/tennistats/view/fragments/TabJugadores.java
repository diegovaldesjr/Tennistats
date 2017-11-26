package com.diegovaldesjr.tennistats.view.fragments;

import android.content.Context;
import android.content.SharedPreferences;
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
import com.diegovaldesjr.tennistats.adapter.JugadorAdapterRecyclerView;
import com.diegovaldesjr.tennistats.io.TennisApiAdapter;
import com.diegovaldesjr.tennistats.io.response.JugadorResponse;
import com.diegovaldesjr.tennistats.model.Jugador;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by diego on 25/11/2017.
 */

public class TabJugadores extends Fragment {

    SharedPreferences user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_jugadores, container, false);

        user = this.getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        cargarJugadores(view);

        return view;
    }

    public void cargarJugadores(View v){
        final ArrayList<Jugador> jugadores = new ArrayList<>();
        final View view = v;

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
                    actualizarRecyclerView(view, jugadores);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<JugadorResponse>> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage().toString(),
                        Toast.LENGTH_SHORT).show();
                Log.d("Error", "Error Respuesta en JSON: " +t.getMessage());
            }
        });
    }

    public void actualizarRecyclerView(View view, ArrayList<Jugador> jugadores){
        RecyclerView jugadoresRecycler = (RecyclerView) view.findViewById(R.id.jugadoresRecycler);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        jugadoresRecycler.setLayoutManager(linearLayoutManager);

        JugadorAdapterRecyclerView jugadorAdapterRecyclerView = new JugadorAdapterRecyclerView(jugadores, R.layout.jugador_list, getActivity());
        jugadoresRecycler.setAdapter(jugadorAdapterRecyclerView);
    }
}
