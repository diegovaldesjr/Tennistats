package com.diegovaldesjr.tennistats.view;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.diegovaldesjr.tennistats.R;
import com.diegovaldesjr.tennistats.io.response.PartidoResponse;
import com.diegovaldesjr.tennistats.model.Jugada;
import com.diegovaldesjr.tennistats.model.Saque;

import java.util.ArrayList;

public class CanchaActivity extends AppCompatActivity {

    private int indice;
    String selection;
    ArrayList<Saque> saques;
    ArrayList<Jugada> jugadas;
    String jugada, golpe, zona;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancha);

        showToolbar(getResources().getString(R.string.app_name), true);

        indice = 1;
        saques = new ArrayList<>();
        jugadas = new ArrayList<>();
        jugada = golpe = "";
        PartidoResponse partido = (PartidoResponse) getIntent().getSerializableExtra("partido");

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
                apuntarJugada("1");
            }
        });

        zona2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apuntarJugada("2");
            }
        });

        zona3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apuntarJugada("3");
            }
        });

        zona4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apuntarJugada("4");
            }
        });

        zona5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apuntarJugada("5");
            }
        });

        zona6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apuntarJugada("6");
            }
        });

        zona7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apuntarJugada("7");
            }
        });

        zona8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apuntarJugada("8");
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

        builder.setTitle("Datos")
                .setMessage("Aqui van los datos")
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

        builder.setTitle("Datos")
                .setMessage("Terminar")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
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

    public void apuntarJugada(String zona){
        AlertDialog.Builder builder = new AlertDialog.Builder(CanchaActivity.this);
        final CharSequence[] items = new CharSequence[2];

        this.zona = "zona "+zona;
        items[0] = "Saque";
        items[1] = "Jugada";

        builder.setTitle("Saque o Jugada")
                .setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(CanchaActivity.this, items[which], Toast.LENGTH_SHORT).show();
                        selection = items[which].toString();
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(selection.equals("Saque")){
                            tipoSaque();
                        }else if(selection.equals("Jugada")){
                            tipoGolpe();
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

    public void tipoGolpe(){
        AlertDialog.Builder builder = new AlertDialog.Builder(CanchaActivity.this);

        final CharSequence[] items = new CharSequence[5];

        items[0] = "Normal";
        items[1] = "Volea";
        items[2] = "Drop";
        items[3] = "Paralelo";
        items[4] = "Angulado";

        builder.setTitle("Tipo de golpe")
                .setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(CanchaActivity.this, items[which], Toast.LENGTH_SHORT).show();
                        golpe = items[which].toString();
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tipoJugada();
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

    public void tipoJugada(){
        AlertDialog.Builder builder = new AlertDialog.Builder(CanchaActivity.this);

        final CharSequence[] items = new CharSequence[4];

        items[0] = "Out";
        items[1] = "Punto";
        items[2] = "Error Forzado";
        items[3] = "Error no forzado";

        final Spinner set = (Spinner) findViewById(R.id.spinnerSet);

        builder.setTitle("Tipo de jugada")
                .setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(CanchaActivity.this, items[which], Toast.LENGTH_SHORT).show();
                        jugada = items[which].toString();
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //jugadas.add(new Jugada(golpe, jugada));
                        Toast.makeText(CanchaActivity.this, "set:"+set.getSelectedItem().toString()+"\n"+golpe+", "+jugada+"\nJugada numero:"+indice+"\n"+zona, Toast.LENGTH_SHORT).show();
                        indice++;
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

    public void tipoSaque(){
        AlertDialog.Builder builder = new AlertDialog.Builder(CanchaActivity.this);

        final CharSequence[] items = new CharSequence[2];

        items[0] = "Primer saque";
        items[1] = "Segundo saque";

        builder.setTitle("Tipo de saque")
                .setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(CanchaActivity.this, items[which], Toast.LENGTH_SHORT).show();
                        jugada = items[which].toString();
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tipoSaqueGolpe();
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

    public void tipoSaqueGolpe(){
        AlertDialog.Builder builder = new AlertDialog.Builder(CanchaActivity.this);

        final CharSequence[] items = new CharSequence[2];

        items[0] = "Ace";
        items[1] = "Falta";

        final Spinner set = (Spinner) findViewById(R.id.spinnerSet);

        builder.setTitle("Tipo de golpe")
                .setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(CanchaActivity.this, items[which], Toast.LENGTH_SHORT).show();
                        golpe = items[which].toString();
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //saques.add(new Saque(jugada, golpe));
                        Toast.makeText(CanchaActivity.this, "set:"+set.getSelectedItem().toString()+"\n"+golpe+", "+jugada+"\nJugada numero:"+indice+"\n"+zona, Toast.LENGTH_SHORT).show();
                        indice++;
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

    public int getIndice() {
        return indice;
    }

    public void setIndice(int indice) {
        this.indice = indice;
    }
}
