package com.diegovaldesjr.tennistats.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.diegovaldesjr.tennistats.R;
import com.diegovaldesjr.tennistats.adapter.PagerAdapter;
import com.diegovaldesjr.tennistats.data.SessionPrefs;
import com.diegovaldesjr.tennistats.data.TennistatsDbHelper;
import com.diegovaldesjr.tennistats.io.ApiConsultas;
import com.getbase.floatingactionbutton.FloatingActionButton;

public class ContainerActivity extends AppCompatActivity {

    private FloatingActionButton crearJugador;
    private FloatingActionButton crearPartido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);

        showToolbar(getResources().getString(R.string.app_name), false);

        crearJugador = (FloatingActionButton) findViewById(R.id.accion_crearjugador);
        crearPartido = (FloatingActionButton) findViewById(R.id.accion_crearpartido);

        crearJugador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCrearJugador();
            }
        });

        crearPartido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCrearPartido();
            }
        });
    }

    public void showToolbar(String tittle, boolean upButton) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.tabToolbar);

        //Soporte para versiones anteriores
        setSupportActionBar(toolbar);

        //Actualiza texto del toolbar
        getSupportActionBar().setTitle(tittle);

        //En caso de que tenga boton lo mostrara
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);

        //Configuramos tabs
        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);

        tabs.addTab(tabs.newTab().setText("Partidos"));
        tabs.addTab(tabs.newTab().setText("Jugadores"));

        tabs.setTabGravity(tabs.GRAVITY_CENTER);

        //Viewpager
        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabs.getTabCount());

        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));
        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                adapter.actualizar();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                adapter.actualizar();
            }
        });

        ImageView sincronizar = (ImageView) findViewById(R.id.sincronizar);
        ImageView cerrarSesion = (ImageView) findViewById(R.id.cerrarSesion);

        sincronizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMessage("Sincronizando.");
                new ApiConsultas(
                        new TennistatsDbHelper(ContainerActivity.this),
                        SessionPrefs.get(ContainerActivity.this).getUsername(),
                        SessionPrefs.get(ContainerActivity.this).getToken(),
                        ContainerActivity.this
                );
            }
        });

        cerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cerrarSesion();
            }
        });

    }

    public void cerrarSesion(){
        AlertDialog.Builder builder = new AlertDialog.Builder(ContainerActivity.this);

        builder.setTitle("Cerrar sesion")
                .setMessage("Seguro de cerrar sesion?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SessionPrefs.get(ContainerActivity.this).logOut();
                        showLogin();
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

    public void showLogin(){
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    private void showMessage(String error) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }

    public void showCrearJugador(){
        Intent intent = new Intent(this, CrearJugadorActivity.class);
        startActivity(intent);
    }

    public void showCrearPartido(){
        Intent intent = new Intent(this, CrearPartidoActivity.class);
        startActivity(intent);
    }
}
