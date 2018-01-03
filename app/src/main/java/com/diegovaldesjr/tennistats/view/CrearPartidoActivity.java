package com.diegovaldesjr.tennistats.view;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.diegovaldesjr.tennistats.R;
import com.diegovaldesjr.tennistats.data.SessionPrefs;
import com.diegovaldesjr.tennistats.data.TennistatsContract;
import com.diegovaldesjr.tennistats.data.TennistatsDbHelper;
import com.diegovaldesjr.tennistats.model.Partido;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CrearPartidoActivity extends AppCompatActivity {

    private Spinner spinnerJugador;

    private TennistatsDbHelper db;
    private Cursor c;
    private String fechaS;
    private Partido partido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_partido);

        showToolbar(getResources().getString(R.string.app_name), true);
        db = new TennistatsDbHelper(CrearPartidoActivity.this);

        spinnerJugador = (Spinner) findViewById(R.id.spinnerJugadorGameconfig);

        TextView fecha = (TextView) findViewById(R.id.fechaGameConfig);

        SimpleDateFormat parseador = new SimpleDateFormat("yyyy-MM-dd");
        fechaS = parseador.format(new Date());
        fecha.setText(fechaS);

        Button button = (Button) findViewById(R.id.empezarPartido);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                empezarPartido();
            }
        });

        loadJugadores();
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

    public void addItemsOnSpinnerJugadores(){

        List<String> dynamicList = new ArrayList<String>();

        while(c.moveToNext()){
            dynamicList.add(c.getString(c.getColumnIndex(TennistatsContract.JugadorEntry.NOMBRE))+" "+c.getString(c.getColumnIndex(TennistatsContract.JugadorEntry.APELLIDO)));
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, dynamicList);
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerJugador.setAdapter(dataAdapter);
    }

    public void loadJugadores(){
        new JugadoresLoadTask().execute();
    }

    private void showError(String error) {
        Toast.makeText(CrearPartidoActivity.this, error, Toast.LENGTH_LONG).show();
    }

    private class JugadoresLoadTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return db.getAllJugadores();
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.getCount() > 0) {
                c = cursor;
                addItemsOnSpinnerJugadores();
            } else {
                String string = "No hay jugadores cargados";
                showError(string);
                c = cursor;
            }
        }
    }

    public void empezarPartido(){
        Spinner categoria = (Spinner) findViewById(R.id.spinnerCategoriaGameconfig);

        c.moveToPosition(spinnerJugador.getSelectedItemPosition());

        partido = new Partido(
                c.getInt(c.getColumnIndex(TennistatsContract.JugadorEntry._ID)),
                fechaS,
                categoria.getSelectedItem().toString(),
                SessionPrefs.get(CrearPartidoActivity.this).getUsername()
        );

        new AddPartidoTask().execute(partido);
    }

    public void showCancha(Partido partido){
        Intent intent = new Intent(this, CanchaActivity.class);

        intent.putExtra("partido", partido);
        startActivity(intent);
    }

    private class AddPartidoTask extends AsyncTask<Partido, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Partido... params) {
            return db.savePartido(params[0]) > 0;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                showError("Partido creado.");
                showCancha(partido);
            } else {
                showError("Error al agregar partido.");
            }
        }

    }
}
