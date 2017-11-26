package com.diegovaldesjr.tennistats.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.diegovaldesjr.tennistats.R;
import com.diegovaldesjr.tennistats.model.Jugador;
import com.diegovaldesjr.tennistats.view.JugadorActivity;

import java.util.ArrayList;

/**
 * Created by diego on 25/11/2017.
 */

public class JugadorAdapterRecyclerView extends RecyclerView.Adapter<JugadorAdapterRecyclerView.JugadorViewHolder>{

    private ArrayList<Jugador> jugadores;
    private int resource;
    private Activity activity;

    public JugadorAdapterRecyclerView(ArrayList<Jugador> jugadores, int resource, Activity activity) {
        this.jugadores = jugadores;
        this.resource = resource;
        this.activity = activity;
    }

    @Override
    public JugadorAdapterRecyclerView.JugadorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);

        return new JugadorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(JugadorAdapterRecyclerView.JugadorViewHolder holder, int position) {

        final Jugador jugador = jugadores.get(position);

        holder.jugador.setText(jugador.getNombre()+" "+jugador.getApellido());

        holder.layout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, JugadorActivity.class);
                intent.putExtra("jugador", jugador);

                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return jugadores.size();
    }

    public class JugadorViewHolder extends RecyclerView.ViewHolder{

        private LinearLayout layout;
        private TextView jugador;

        public JugadorViewHolder(View itemView){
            super (itemView);

            layout = (LinearLayout) itemView.findViewById(R.id.layoutJugadorlist);
            jugador = (TextView) itemView.findViewById(R.id.jugadorList);
        }
    }
}
