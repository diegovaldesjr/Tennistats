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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.diegovaldesjr.tennistats.R;
import com.diegovaldesjr.tennistats.data.TennistatsContract;
import com.diegovaldesjr.tennistats.data.TennistatsDbHelper;
import com.diegovaldesjr.tennistats.model.Jugador;
import com.diegovaldesjr.tennistats.view.CanchaActivity;
import com.diegovaldesjr.tennistats.view.JugadorActivity;

import java.util.ArrayList;

/**
 * Created by diego on 25/11/2017.
 */

public class JugadorAdapterRecyclerView extends RecyclerView.Adapter<JugadorAdapterRecyclerView.JugadorViewHolder>{

    private int resource;
    private Activity activity;
    private Cursor items;
    private TennistatsDbHelper db;

    public JugadorAdapterRecyclerView(Cursor items, int resource, Activity activity, TennistatsDbHelper db) {
        this.items = items;
        this.resource = resource;
        this.activity = activity;
        this.db = db;
    }

    @Override
    public JugadorAdapterRecyclerView.JugadorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);

        return new JugadorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(JugadorAdapterRecyclerView.JugadorViewHolder holder, int position) {

        items.moveToPosition(position);
        final Jugador jugador = new Jugador(
                items.getInt(items.getColumnIndex(TennistatsContract.JugadorEntry._ID)),
                items.getString(items.getColumnIndex(TennistatsContract.JugadorEntry.NOMBRE)),
                items.getString(items.getColumnIndex(TennistatsContract.JugadorEntry.APELLIDO)),
                items.getInt(items.getColumnIndex(TennistatsContract.JugadorEntry.EDAD)),
                items.getString(items.getColumnIndex(TennistatsContract.JugadorEntry.MANO_DIESTRA)),
                items.getString(items.getColumnIndex(TennistatsContract.JugadorEntry.GENERO)),
                items.getString(items.getColumnIndex(TennistatsContract.JugadorEntry.ID_USUARIO))
        );

        holder.jugador.setText(jugador.getNombre()+" "+jugador.getApellido());

        holder.layout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, JugadorActivity.class);
                intent.putExtra("jugador", jugador);

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
                                new DeleteJugadorTask().execute(String.valueOf(items.getInt(items.getColumnIndex(TennistatsContract.JugadorEntry._ID))));
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
        if (items != null)
            return items.getCount();
        return 0;
    }

    private void showError(String error) {
        Toast.makeText(activity, error, Toast.LENGTH_LONG).show();
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

    private class DeleteJugadorTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            return db.deleteJugador(params[0]) > 0;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if(result){
                showError("Jugador borrado.");
            }else{
                showError("Error al borrar jugador");
            }
        }
    }
}
