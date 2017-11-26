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
import com.diegovaldesjr.tennistats.adapter.PartidoAdapterRecyclerView;
import com.diegovaldesjr.tennistats.io.TennisApiAdapter;
import com.diegovaldesjr.tennistats.io.response.PartidoResponse;
import com.diegovaldesjr.tennistats.model.Partido;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by diego on 25/11/2017.
 */

public class TabPartidos extends Fragment {

    SharedPreferences user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_partidos, container, false);

        user = this.getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);

        cargarPartidos(view);

        return view;
    }

    public void cargarPartidos(View v){
        final ArrayList<Partido> partidos = new ArrayList<>();
        final View view = v;

        Call<ArrayList<PartidoResponse>> call = TennisApiAdapter.getApiService().getPartidos("Bearer "+user.getString("access_token", ""));

        call.enqueue(new Callback<ArrayList<PartidoResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<PartidoResponse>> call, Response<ArrayList<PartidoResponse>> response) {
                if(response.isSuccessful()){
                    ArrayList<PartidoResponse> data = response.body();

                    for(int i=0; i<data.size(); i++){
                        PartidoResponse row = data.get(i);

                        try {
                            SimpleDateFormat parseador = new SimpleDateFormat("yyyy-MM-dd");
                            Date fecha = parseador.parse(row.getFecha());

                            partidos.add(new Partido(
                                    Integer.parseInt(row.getIdPartido()),
                                    Integer.parseInt(row.getJugador().getIdJugador()),
                                    row.getJugador().getNombre()+" "+row.getJugador().getApellido(),
                                    fecha,
                                    row.getCategoria()
                            ));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    actualizarRecyclerView(view, partidos);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<PartidoResponse>> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage().toString(),
                        Toast.LENGTH_SHORT).show();
                Log.d("Error", "Error Respuesta en JSON: " +t.getMessage());
            }
        });
    }

    public void actualizarRecyclerView(View view, ArrayList<Partido> partidos){
        RecyclerView partidosRecycler = (RecyclerView) view.findViewById(R.id.partidoRecycler);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        partidosRecycler.setLayoutManager(linearLayoutManager);

        PartidoAdapterRecyclerView partidoAdapterRecyclerView = new PartidoAdapterRecyclerView(partidos, R.layout.partido_list, getActivity());
        partidosRecycler.setAdapter(partidoAdapterRecyclerView);
    }
}
