package com.diegovaldesjr.tennistats;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.diegovaldesjr.tennistats.model.Jugador;
import com.diegovaldesjr.tennistats.model.Partido;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameConfigActivity extends AppCompatActivity {

    private Spinner spinnerJugador, spinnerCategoria;
    SharedPreferences user;
    JSONObject partido;

    ArrayList<Jugador> jugadores = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_config);
        obtenerJugadores();

        partido = new JSONObject();

        spinnerCategoria = (Spinner) findViewById(R.id.spinnerCategoriaGameconfig);
        spinnerCategoria.setOnItemSelectedListener(categoriaSelectedListener);

        spinnerJugador = (Spinner) findViewById(R.id.spinnerJugadorGameconfig);
        spinnerJugador.setOnItemSelectedListener(jugadorSelectedListener);

        user = getSharedPreferences("user", Context.MODE_PRIVATE);

        showToolbar(getResources().getString(R.string.app_name), true);
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

    public void obtenerJugadores() {

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "http://"+user.getString("ip_address","")+"/api/jugadores", null,
                new Response.Listener<JSONObject>(){
                    //Listener para exito
                    @Override
                    public void onResponse(JSONObject response) {
                        //Aqui se actualiza
                        Log.d("JSON", "Respuesta" + response.toString());
                        Jugador jugador;

                        try {
                            for(int i=0; i<response.getJSONArray("jugadores").length();i++){
                                jugador = new Jugador(
                                        response.getJSONArray("jugadores").getJSONObject(i).getInt("idJugador"),
                                        response.getJSONArray("jugadores").getJSONObject(i).getString("nombre"),
                                        response.getJSONArray("jugadores").getJSONObject(i).getString("apellido")
                                );
                                jugadores.add(jugador);
                            }
                            addItemsOnSpinnerJugadores(response.getJSONArray("jugadores"), response.getJSONArray("jugadores").length());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener(){
                    //Listener para error
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error", "Error Respuesta en JSON: " +error.getMessage());
                    }
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError{
                Map<String, String> params = new HashMap<>();

                params.put("Authorization", "Bearer "+user.getString("access_token",""));
                return params;
            }
        };

        //Agregar json al request
        queue.add(jsonObjectRequest);

    }

    public void addItemsOnSpinnerJugadores(JSONArray array, int n){

        List<String> dynamicList = new ArrayList<String>();

        for (int i=0; i<array.length(); i++) {
            try {
                dynamicList.add(array.getJSONObject(i).getString("nombre")+" "+array.getJSONObject(i).getString("apellido"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, dynamicList);
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerJugador.setAdapter(dataAdapter);
    }

    private AdapterView.OnItemSelectedListener jugadorSelectedListener = new Spinner.OnItemSelectedListener(){

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            /*Toast.makeText(
                    parent.getContext(), "Item selected:" + parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT
            ).show();*/

            for(int i=0; i<jugadores.size(); i++){
                if(jugadores.get(i).getNombre().contains(parent.getItemAtPosition(position).toString()) && jugadores.get(i).getApellido().contains(parent.getItemAtPosition(position).toString()))
                    try {
                        partido.put("idJugador", jugadores.get(i).getIdJugador());
                        partido.put("fecha", Calendar.getInstance().getTime());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private AdapterView.OnItemSelectedListener categoriaSelectedListener = new Spinner.OnItemSelectedListener(){

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            try {
                partido.put("categoria", parent.getItemAtPosition(position).toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    public void crearPartido(){

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, "http://"+user.getString("ip_address","")+"/api/partidos", partido,
                new Response.Listener<JSONObject>(){
                    //Listener para exito
                    @Override
                    public void onResponse(JSONObject response) {
                        //Aqui se actualiza
                        Log.d("JSON", "Respuesta" + response.toString());
                        finish();
                    }
                },
                new Response.ErrorListener(){
                    //Listener para error
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error", "Error Respuesta en JSON: " +error.getMessage());
                    }
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError{
                Map<String, String> params = new HashMap<>();

                params.put("Authorization", "Bearer "+user.getString("access_token",""));
                return params;
            }
        };

        //Agregar json al request
        queue.add(jsonObjectRequest);
    }
}
