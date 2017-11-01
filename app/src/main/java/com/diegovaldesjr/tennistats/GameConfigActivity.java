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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameConfigActivity extends AppCompatActivity {

    private Spinner spinnerJugador, spinnerCategoria;
    JSONArray jsonJugadores;
    SharedPreferences user;

    ArrayList<Jugador> jugadores = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_config);

        spinnerCategoria = (Spinner) findViewById(R.id.spinnerCategoriaGameconfig);
        spinnerCategoria.setOnItemSelectedListener(new ItemSelectedListener(spinnerCategoria));

        spinnerJugador = (Spinner) findViewById(R.id.spinnerJugadorGameconfig);
        addItemsOnSpinnerDynamic();
        spinnerJugador.setOnItemSelectedListener(JugadorItemSelectedListener);

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

    public void addItemsOnSpinnerDynamic() {

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "http://192.168.151.239:8000/api/jugadores", null,
                new Response.Listener<JSONObject>(){
                    //Listener para exito
                    @Override
                    public void onResponse(JSONObject response) {
                        //Aqui se actualiza
                        Log.d("JSON", "Respuesta" + response.toString());
                        try {
                            jsonJugadores = response.getJSONArray("jugadores");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        for(int i = 0; i< jsonJugadores.length(); i++){
                            try {
                                JSONObject row = jsonJugadores.getJSONObject(i);
                                jugadores.add(new Jugador(row.getInt("idJugador"),row.getString("nombre"), row.getString("apellido")));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
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

                params.put("Authorization", "Bearer "+user.getString("token",""));
                return params;
            }
        };

        //Agregar json al request
        queue.add(jsonObjectRequest);

        ArrayList<String> dynamicList = new ArrayList<String>();
        for (Jugador jugador : jugadores) {
            dynamicList.add(jugador.getNombre()+" "+jugador.getApellido());
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, dynamicList);
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerJugador.setAdapter(dataAdapter);
    }

    private AdapterView.OnItemSelectedListener JugadorItemSelectedListener = new Spinner.OnItemSelectedListener() {

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            Toast.makeText(
                    parent.getContext(),
                    "Item selected : "
                            + parent.getItemAtPosition(pos).toString(),
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {

        }
    };

    //Spinner categoria listener
    public class ItemSelectedListener implements AdapterView.OnItemSelectedListener {

        Spinner spinner;

        public ItemSelectedListener(Spinner spinner) {
            this.spinner = spinner;
        }

        //get strings of first item
        String firstItem = String.valueOf(spinnerCategoria.getSelectedItem());

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            if (firstItem.equals(String.valueOf(spinner.getSelectedItem()))) {

            } else {
                Toast.makeText(parent.getContext(),
                        "You have selected : " + parent.getItemAtPosition(pos).toString(),
                        Toast.LENGTH_LONG).show();

            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg) {

        }

    }
}
