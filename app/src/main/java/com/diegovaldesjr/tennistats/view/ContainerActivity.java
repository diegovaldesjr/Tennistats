package com.diegovaldesjr.tennistats.view;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.diegovaldesjr.tennistats.R;
import com.diegovaldesjr.tennistats.adapter.PagerAdapter;

public class ContainerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);

        showToolbar(getResources().getString(R.string.app_name), false);
    }

    public void showToolbar(String tittle, boolean upButton){
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
        PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabs.getTabCount());

        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));
        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    public void crearPartidoIntent(View view){
        Intent intent = new Intent(this, CrearPartidoActivity.class);
        startActivity(intent);
    }

    public void crearJugadorIntent(View view){
        Intent intent = new Intent(this, RegistrarJugadorActivity.class);
        startActivity(intent);
    }
}
