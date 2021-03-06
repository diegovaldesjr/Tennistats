package com.diegovaldesjr.tennistats.adapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.diegovaldesjr.tennistats.data.TennistatsContract;
import com.diegovaldesjr.tennistats.data.TennistatsDbHelper;
import com.diegovaldesjr.tennistats.view.PartidoActivity;
import com.diegovaldesjr.tennistats.R;
import com.diegovaldesjr.tennistats.model.Partido;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by diego on 29/10/2017.
 */

public class PartidoAdapterRecyclerView extends RecyclerView.Adapter<PartidoAdapterRecyclerView.PartidoViewHolder> {

    private int resource;
    private Activity activity;
    private ArrayList<Partido> partidos;
    private TennistatsDbHelper db;

    public PartidoAdapterRecyclerView(ArrayList<Partido> partidos, int resource, Activity activity, TennistatsDbHelper db) {
        this.partidos = partidos;
        this.resource = resource;
        this.activity = activity;
        this.db = db;
    }

    @Override
    public PartidoAdapterRecyclerView.PartidoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);

        return new PartidoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PartidoAdapterRecyclerView.PartidoViewHolder holder, int position) {
        final Partido partido = partidos.get(position);

        holder.jugador.setText(partido.getJugador().getNombre()+" "+partido.getJugador().getApellido());
        holder.categoria.setText(partido.getCategoria());

        //SimpleDateFormat parseador = new SimpleDateFormat("yyyy-MM-dd");
        //holder.fecha.setText(parseador.format(partido.getFecha()));
        holder.fecha.setText(partido.getFecha());

        for(int i=0; i<partido.getSets().size(); i++){
            switch (i){
                case 0:
                    holder.set1.setText(String.valueOf(partido.getSets().get(i).getPuntajej())+"-"+String.valueOf(partido.getSets().get(i).getPuntajeo()));
                    break;
                case 1:
                    holder.set2.setText(String.valueOf(partido.getSets().get(i).getPuntajej())+"-"+String.valueOf(partido.getSets().get(i).getPuntajeo()));
                    break;
                case 2:
                    holder.set3.setText(String.valueOf(partido.getSets().get(i).getPuntajej())+"-"+String.valueOf(partido.getSets().get(i).getPuntajeo()));
                    break;
            }
        }

        holder.layout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, PartidoActivity.class);
                intent.putExtra("partido", partido);

                activity.startActivity(intent);
            }
        });

        holder.layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setTitle("Eliminar")
                        .setMessage("Seguro que desea eliminar jugador?")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                new DeletePartidoTask().execute(String.valueOf(partido.getIdPartido()));
                                dialog.cancel();
                            }
                        })
                        .setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                builder.create().show();
                return false;
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

            layout = (RelativeLayout) itemView.findViewById(R.id.layoutPartidolist);
            jugador = (TextView) itemView.findViewById(R.id.jugadorPartidolist);
            fecha = (TextView) itemView.findViewById(R.id.fechaPartidolist);
            set1 = (TextView) itemView.findViewById(R.id.set1Partidolist);
            set2 = (TextView) itemView.findViewById(R.id.set2Partidolist);
            set3 = (TextView) itemView.findViewById(R.id.set3Partidolist);
            categoria = (TextView) itemView.findViewById(R.id.categoriaPartidolist);
        }
    }

    private void showError(String error) {
        Toast.makeText(activity, error, Toast.LENGTH_LONG).show();
    }

    private class DeletePartidoTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            return db.deletePartido(params[0]) > 0;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if(result){
                showError("Partido borrado.");
            }else{
                showError("Error al borrar partido");
            }
        }
    }
}
