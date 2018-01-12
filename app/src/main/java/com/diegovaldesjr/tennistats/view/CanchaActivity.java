package com.diegovaldesjr.tennistats.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.diegovaldesjr.tennistats.R;
import com.diegovaldesjr.tennistats.data.TennistatsDbHelper;
import com.diegovaldesjr.tennistats.model.Jugada;
import com.diegovaldesjr.tennistats.model.Partido;
import com.diegovaldesjr.tennistats.model.Saque;
import com.diegovaldesjr.tennistats.model.Set;

import java.util.ArrayList;

public class CanchaActivity extends AppCompatActivity {

    private TextView numeroSet;
    private int indice, nSet;
    private Set set;
    private Saque saque;
    private Jugada jugada;
    private TennistatsDbHelper db;
    private Partido partido;

    private String selection;
    private Boolean finalizar=false, avanzar=false;

    private ArrayList<Saque> saques;
    private ArrayList<Jugada> jugadas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancha);

        showToolbar(getResources().getString(R.string.app_name), false);
        partido = (Partido) getIntent().getSerializableExtra("partido");
        db = new TennistatsDbHelper(CanchaActivity.this);

        numeroSet = (TextView) findViewById(R.id.numeroSet);

        indice = nSet = 1;
        numeroSet.setText(String.valueOf(nSet));

        saque = new Saque();
        jugada = new Jugada();
        saques = new ArrayList<>();
        jugadas = new ArrayList<>();

        set = new Set(partido.getIdPartido(), nSet, 0, 0, "");

        new AddSetTask().execute(set);

        ImageView zona1 = (ImageView) findViewById(R.id.Zona1);
        ImageView zona2 = (ImageView) findViewById(R.id.Zona2);
        ImageView zona3 = (ImageView) findViewById(R.id.Zona3);
        ImageView zona4 = (ImageView) findViewById(R.id.Zona4);
        ImageView zona5 = (ImageView) findViewById(R.id.Zona5);
        ImageView zona6 = (ImageView) findViewById(R.id.Zona6);
        ImageView zona7 = (ImageView) findViewById(R.id.Zona7);
        ImageView zona8 = (ImageView) findViewById(R.id.Zona8);

        zona1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                seleccionarJugada(1);
            }
        });

        zona2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seleccionarJugada(2);
            }
        });

        zona3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seleccionarJugada(3);
            }
        });

        zona4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seleccionarJugada(4);
            }
        });

        zona5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seleccionarJugada(5);
            }
        });

        zona6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seleccionarJugada(6);
            }
        });

        zona7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seleccionarJugada(7);
            }
        });

        zona8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seleccionarJugada(8);
            }
        });
    }

    public void showToolbar(String tittle, boolean upButton){
        Toolbar toolbar = (Toolbar) findViewById(R.id.canchaToolbar);

        //Soporte para versiones anteriores
        setSupportActionBar(toolbar);

        //Actualiza texto del toolbar
        getSupportActionBar().setTitle(tittle);

        //En caso de que tenga boton lo mostrara
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);

        ImageView ver = (ImageView) findViewById(R.id.verDatos);
        ImageView terminar = (ImageView) findViewById(R.id.terminarPartido);

        ver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verDatos();
            }
        });
        terminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                terminarPartido();
            }
        });
    }

    public void verDatos(){
        AlertDialog.Builder builder = new AlertDialog.Builder(CanchaActivity.this);
        LayoutInflater inflater = this.getLayoutInflater();
        String mensaje = "";

        View v = inflater.inflate(R.layout.dialog_ver_jugadas, null);
        TextView textView = (TextView) v.findViewById(R.id.mensajeVerJugada);

        for(int i=0, j=0, k=0; k<(indice-1); k++){
            if(jugadas.size() > 0 && jugadas.get(i).getIndice() == (k+1)){
                mensaje += "Jugada:\nGolpe:"+jugadas.get(i).getTipoGolpe()+"\tTipo:"+jugadas.get(i).getTipoJugada()+"\tZona:"+jugadas.get(i).getZona() +"\n\n";
                i++;
            }else if(saques.size() > 0 && saques.get(j).getIndice() == (k+1)){
                mensaje += "Saque:\nGolpe:"+saques.get(j).getTipoGolpe()+"\tTipo:"+saques.get(j).getTipoSaque()+"\tZona:"+saques.get(j).getZona() +"\n\n";
                j++;
            }
        }

        textView.setText(mensaje);
        builder.setView(v)
                .setTitle("Datos")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        builder.create().show();
    }

    public void terminarPartido(){
        AlertDialog.Builder builder = new AlertDialog.Builder(CanchaActivity.this);
        builder.setTitle("Terminar")
                .setMessage("Desea terminar...")
                .setPositiveButton("PARTIDO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finalizar = true;
                        resultado();
                    }
                })
                .setNeutralButton("CANCELAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        if(nSet < 3){
            builder.setNegativeButton("SET", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    avanzar=true;
                    resultado();
                }
            });
        }

        builder.create().show();
    }

    public void resultado(){
        AlertDialog.Builder builder = new AlertDialog.Builder(CanchaActivity.this);
        LayoutInflater inflater = this.getLayoutInflater();

        final View v = inflater.inflate(R.layout.dialog_terminar_set, null);

        builder.setView(v)
                .setTitle("Resultado")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText puntajejEditText = (EditText) v.findViewById(R.id.puntajej);
                        EditText puntajeoEditText = (EditText) v.findViewById(R.id.puntajeo);
                        Spinner ganadorSpinner = (Spinner) v.findViewById(R.id.spinnerGanador);

                        if(validarPuntaje(puntajejEditText.getText().toString()) && validarPuntaje(puntajeoEditText.getText().toString())){
                            set.setPuntajej(Integer.parseInt(puntajejEditText.getText().toString()));
                            set.setPuntajeo(Integer.parseInt(puntajeoEditText.getText().toString()));
                            set.setGanador(ganadorSpinner.getSelectedItem().toString());

                            //Actualizar set en BD
                            new AddSetTask().execute(set);
                            //Creo set siguiente
                        }else {
                            showMessage(getString(R.string.error_invalid_puntaje));
                            dialog.cancel();
                        }
                    }
                })
                .setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        builder.create().show();
    }

    public Boolean validarPuntaje(String puntaje){

        if (TextUtils.isEmpty(puntaje)) {
            showMessage(getString(R.string.error_invalid_puntaje));
            return false;
        }
        switch (puntaje){
            case "0": return true;
            case "15": return true;
            case "30": return true;
            case "40": return true;
            case "45": return true;
        }

        return false;
    }

    public void seleccionarJugada(final int zona){
        AlertDialog.Builder builder = new AlertDialog.Builder(CanchaActivity.this);
        final CharSequence[] items = new CharSequence[2];

        items[0] = "Saque";
        items[1] = "Jugada";

        builder.setTitle("Saque o Jugada")
                .setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selection = items[which].toString();
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(selection.equals("Saque")){
                            saque.setZona(zona);
                            saqueTipoSaque();
                        }else if(selection.equals("Jugada")){
                            jugada.setZona(zona);
                            jugadaTipoGolpe();
                        }
                    }
                })
                .setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        builder.create().show();
    }

    public void jugadaTipoGolpe(){
        AlertDialog.Builder builder = new AlertDialog.Builder(CanchaActivity.this);
        final CharSequence[] items = new CharSequence[5];

        items[0] = "Normal";
        items[1] = "Volea";
        items[2] = "Drop";
        items[3] = "Paralelo";
        items[4] = "Angulado";

        builder.setTitle("Jugada, seleccion tipo de golpe")
                .setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        jugada.setTipoGolpe(items[which].toString());
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        jugadaTipoJugada();
                    }
                })
                .setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        builder.create().show();
    }

    public void jugadaTipoJugada(){
        AlertDialog.Builder builder = new AlertDialog.Builder(CanchaActivity.this);
        final CharSequence[] items = new CharSequence[4];

        items[0] = "Out";
        items[1] = "Punto";
        items[2] = "Error forzado";
        items[3] = "Error no forzado";

        builder.setTitle("Golpe:"+jugada.getTipoGolpe()+", seleccion tipo de jugada")
                .setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        jugada.setTipoJugada(items[which].toString());
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        jugada.setIdSet(set.getIdSet());
                        jugada.setIndice(indice++);
                        new AddJugadaTask().execute(jugada);
                        //showMessage("golpe:"+jugada.getTipoGolpe()+", saque"+jugada.getTipoJugada()+"\nJugada numero:"+saque.getIndice()+"\n Zona:"+saque.getZona());
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
    }

    public void saqueTipoSaque(){
        AlertDialog.Builder builder = new AlertDialog.Builder(CanchaActivity.this);
        final CharSequence[] items = new CharSequence[2];

        items[0] = "Primero";
        items[1] = "Segundo";

        builder.setTitle("Saque, seleccione tipo de saque")
                .setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        saque.setTipoSaque(items[which].toString());
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        saqueTipoGolpe();
                    }
                })
                .setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        builder.create().show();
    }

    public void saqueTipoGolpe(){
        AlertDialog.Builder builder = new AlertDialog.Builder(CanchaActivity.this);
        final CharSequence[] items = new CharSequence[4];

        items[0] = "Ace";
        items[1] = "Falta";
        items[2] = "Gano";
        items[3] = "Perdio";

        builder.setTitle(saque.getTipoSaque()+", seleccione tipo de golpe")
                .setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        saque.setTipoGolpe(items[which].toString());
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        saque.setIdSet(set.getIdSet());
                        saque.setIndice(indice++);
                        new AddSaqueTask().execute(saque);
                        //showMessage("golpe:"+saque.getTipoGolpe()+", saque"+saque.getTipoSaque()+"\nJugada numero:"+saque.getIndice()+"\n Zona:"+saque.getZona());
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
    }

    private void showMessage(String error) {
        Toast.makeText(CanchaActivity.this, error, Toast.LENGTH_LONG).show();
    }

    private void showHome() {
        startActivity(new Intent(this, ContainerActivity.class));
        finish();
    }

    private class AddSetTask extends AsyncTask<Set, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Set... params) {
            if(set.getIdSet() != 0){
                return db.updateSet(params[0], String.valueOf(set.getIdSet())) > 0;
            }else{
                set.setIdSet(db.saveSet(params[0]));
                return set.getIdSet() > 0;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                showMessage("Set agregado.");
                if(finalizar){
                    showHome();
                }else if(avanzar){
                    nSet++;
                    set = new Set(partido.getIdPartido(), nSet, 0, 0, "");
                    avanzar=false;
                    new AddSetTask().execute(set);
                    numeroSet.setText(String.valueOf(nSet));
                }

            } else {
                showMessage("Error al crear set.");
            }
        }

    }

    private class AddSaqueTask extends AsyncTask<Saque, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Saque... params) {
            return db.saveSaque(params[0]) > 0;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                showMessage("Saque agregado.");
                saques.add(new Saque(saque.getIndice(), saque.getIdSet(), saque.getZona(), saque.getTipoSaque(), saque.getTipoGolpe()));
            } else {
                showMessage("Error al registrar saque.");
            }
        }

    }

    private class AddJugadaTask extends AsyncTask<Jugada, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Jugada... params) {
            return db.saveJugada(params[0]) > 0;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                showMessage("Jugada agregada.");
                jugadas.add(new Jugada(jugada.getIndice(), jugada.getIdSet(), jugada.getZona(), jugada.getTipoGolpe(), jugada.getTipoJugada()));
            } else {
                showMessage("Error al registrar jugada.");
            }
        }

    }
}
