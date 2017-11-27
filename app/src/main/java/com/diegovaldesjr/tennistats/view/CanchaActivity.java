package com.diegovaldesjr.tennistats.view;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.diegovaldesjr.tennistats.R;
import com.diegovaldesjr.tennistats.io.response.PartidoResponse;

public class CanchaActivity extends AppCompatActivity {

    private int indice;
    String selection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancha);

        indice = 1;
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
                apuntarJugada();
            }
        });

        zona2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apuntarJugada();
            }
        });

        zona3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apuntarJugada();
            }
        });

        zona4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apuntarJugada();
            }
        });

        zona5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apuntarJugada();
            }
        });

        zona6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apuntarJugada();
            }
        });

        zona7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apuntarJugada();
            }
        });

        zona8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apuntarJugada();
            }
        });
    }

    public void apuntarJugada(){
        AlertDialog.Builder builder = new AlertDialog.Builder(CanchaActivity.this);
        final CharSequence[] items = new CharSequence[2];

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

        builder.setTitle("Tipo de jugada")
                .setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(CanchaActivity.this, items[which], Toast.LENGTH_SHORT).show();
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

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

        final CharSequence[] items = new CharSequence[5];

        items[0] = "Ace";
        items[1] = "Falta";

        builder.setTitle("Tipo de golpe")
                .setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(CanchaActivity.this, items[which], Toast.LENGTH_SHORT).show();
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

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
