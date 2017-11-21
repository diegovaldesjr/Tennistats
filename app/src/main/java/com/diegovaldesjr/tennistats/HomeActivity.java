package com.diegovaldesjr.tennistats;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {

    SharedPreferences user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        user = getSharedPreferences("user", Context.MODE_PRIVATE);
        showToolbar(getResources().getString(R.string.app_name), false);

        RecyclerView partidosRecycler = (RecyclerView) findViewById(R.id.partidoRecycler);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        partidosRecycler.setLayoutManager(linearLayoutManager);

        PartidoAdapterRecyclerView partidoAdapterRecyclerView = new PartidoAdapterRecyclerView(buidPartidos());

        partidosRecycler.setAdapter(partidoAdapterRecyclerView);
    }

    public ArrayList<Partido> buidPartidos() {

        RequestQueue queue = Volley.newRequestQueue(this);
        final ArrayList<Partido> partidos = new ArrayList<>();

        JsonObjectRequest partidosRequest = new JsonObjectRequest(Request.Method.GET, "http://"+user.getString("ip_address","")+"/api/partidos", null,
                new Response.Listener<JSONObject>() {
                    //Listener para exito
                    @Override
                    public void onResponse(JSONObject response) {
                        //Aqui se actualiza
                        JSONArray partidosArray = null;
                        try {
                            partidosArray = response.getJSONArray("partidos");
                            for (int i = 0; i < partidosArray.length(); i++) {
                                JSONObject row = partidosArray.getJSONObject(i);
                                partidos.add(new Partido(
                                        row.getInt("idJugador"),
                                        row.getJSONObject("jugador").getInt("idJugador"),
                                        row.getJSONObject("jugador").getString("nombre")+" "+row.getJSONObject("jugador").getString("apellido"),
                                        row.getJSONObject("jugador").getString("fecha"),
                                        row.getString("categoria")
                                ));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Log.d("JSON", "Respuesta" + response.toString());
                    }
                },
                new Response.ErrorListener() {
                    //Listener para error
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error", "Error Respuesta en JSON: " + error.getMessage());
                    }
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("Authorization", "Bearer "+user.getString("access_token",""));
                return params;
            }

        };

            //Agregar json al request
        queue.add(partidosRequest);

        return partidos;
    }

    public void crearPartidoIntent(View view){
        Intent intent = new Intent(this, GameConfigActivity.class);
        startActivity(intent);
    }

    public void crearJugadorIntent(View view){
        Intent intent = new Intent(this, RegistrarJugadorActivity.class);
        startActivity(intent);
    }

    public void showToolbar(String tittle, boolean upButton){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar); //Cambiar
        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);

        //Soporte para versiones anteriores
        setSupportActionBar(toolbar);

        //Actualiza texto del toolbar
        getSupportActionBar().setTitle(tittle);

        //En caso de que tenga boton lo mostrara
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);

        //tabs.addTab(tabs.newTab().setText("Partidos"));
        //tabs.addTab(tabs.newTab().setText("Jugadores"));

    }
}
