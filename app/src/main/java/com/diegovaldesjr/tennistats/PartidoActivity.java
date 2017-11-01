package com.diegovaldesjr.tennistats;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.diegovaldesjr.tennistats.model.Partido;

public class PartidoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partido);

        showToolbar(getResources().getString(R.string.app_name), true);

        setDatosPartido();
    }

    public void setDatosPartido(){
        TextView jugador = (TextView) findViewById(R.id.jugadorPartido);
        TextView fecha = (TextView) findViewById(R.id.fechaPartido);
        TextView categoria = (TextView) findViewById(R.id.categoriaPartido);

        Partido partido = (Partido) getIntent().getSerializableExtra("partido");

        jugador.setText(partido.getJugador());
        fecha.setText(partido.getFecha().toString());
        categoria.setText(partido.getCategoria());
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
}
