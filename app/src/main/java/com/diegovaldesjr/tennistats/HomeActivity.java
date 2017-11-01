package com.diegovaldesjr.tennistats;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.diegovaldesjr.tennistats.adapter.PartidoAdapterRecyclerView;
import com.diegovaldesjr.tennistats.model.Partido;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class HomeActivity extends AppCompatActivity {

    ArrayList<Partido> partidos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        showToolbar(getResources().getString(R.string.app_name), false);

        RecyclerView partidosRecycler = (RecyclerView) findViewById(R.id.partidoRecycler);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        partidosRecycler.setLayoutManager(linearLayoutManager);

        PartidoAdapterRecyclerView partidoAdapterRecyclerView = new PartidoAdapterRecyclerView(buidPartidos());

        partidosRecycler.setAdapter(partidoAdapterRecyclerView);
    }

    public ArrayList<Partido> buidPartidos(){
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, "", null,
                new Response.Listener<JSONArray>(){
                    //Listener para exito
                    @Override
                    public void onResponse(JSONArray response) {
                        //Aqui se actualiza
                        for(int i=0; i<response.length(); i++){
                            try {
                                JSONObject row = response.getJSONObject(i);
                                //partidos.add(new Partido());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        Log.d("JSON", "Respuesta" + response.toString());
                    }
                },
                new Response.ErrorListener(){
                    //Listener para error
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error", "Error Respuesta en JSON: " +error.getMessage());
                    }
                });

        //Agregar json al request
        queue.add(jsonArrayRequest);

        return partidos;
    }

    public void crearPartido(View view){
        Intent intent = new Intent(this, GameConfigActivity.class);
        startActivity(intent);
    }

    public void crearJugador(View view){
        Intent intent = new Intent(this, RegistrarJugadorActivity.class);
        startActivity(intent);
    }

    public void showToolbar(String tittle, boolean upButton){
        Toolbar toolbar = (Toolbar) findViewById(R.id.tabToolbar);
        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);

        //Soporte para versiones anteriores
        setSupportActionBar(toolbar);

        //Actualiza texto del toolbar
        getSupportActionBar().setTitle(tittle);

        //En caso de que tenga boton lo mostrara
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);

        tabs.addTab(tabs.newTab().setText("Partidos"));
        tabs.addTab(tabs.newTab().setText("Jugadores"));

    }
}
