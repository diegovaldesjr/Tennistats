package com.diegovaldesjr.tennistats.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.diegovaldesjr.tennistats.PartidoActivity;
import com.diegovaldesjr.tennistats.R;
import com.diegovaldesjr.tennistats.model.Partido;

import java.util.ArrayList;

/**
 * Created by diego on 29/10/2017.
 */

public class PartidoAdapterRecyclerView extends RecyclerView.Adapter<PartidoAdapterRecyclerView.PartidoViewHolder> {

    private ArrayList<Partido> partidos;
    private Context context;

    public PartidoAdapterRecyclerView(ArrayList<Partido> partidos) {
        this.partidos = partidos;
    }


    @Override
    public PartidoAdapterRecyclerView.PartidoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.partido_list, parent, false);

        return new PartidoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PartidoAdapterRecyclerView.PartidoViewHolder holder, int position) {

        final Partido partido = partidos.get(position);

        holder.jugador.setText(partido.getJugador());
        holder.categoria.setText(partido.getCategoria());
        holder.fecha.setText(partido.getFecha().toString());

        holder.set1.setText(R.string.set_result_partidolist);
        holder.set2.setText(R.string.set_result_partidolist);
        holder.set3.setText(R.string.set_result_partidolist);

        holder.layout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PartidoActivity.class);
                intent.putExtra("partido", partido);

                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return partidos.size();
    }

    public class PartidoViewHolder extends RecyclerView.ViewHolder{

        private RelativeLayout layout;
        private TextView jugador, fecha, set1, set2, set3, categoria;

        public PartidoViewHolder(View itemView){
            super (itemView);
            context = itemView.getContext();

            layout = (RelativeLayout) itemView.findViewById(R.id.layoutPartidolist);
            jugador = (TextView) itemView.findViewById(R.id.jugadorPartidolist);
            fecha = (TextView) itemView.findViewById(R.id.fechaPartidolist);
            set1 = (TextView) itemView.findViewById(R.id.set1Partidolist);
            set2 = (TextView) itemView.findViewById(R.id.set2Partidolist);
            set3 = (TextView) itemView.findViewById(R.id.set3Partidolist);
            categoria = (TextView) itemView.findViewById(R.id.categoriaPartidolist);
        }
    }
}
