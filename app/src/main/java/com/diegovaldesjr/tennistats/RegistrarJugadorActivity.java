package com.diegovaldesjr.tennistats;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RegistrarJugadorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_jugador);

        showToolbar(getResources().getString(R.string.app_name), true);
    }

    public void registrarUsuario(){

        JSONObject json = new JSONObject();

        TextInputEditText nombre = (TextInputEditText) findViewById(R.id.nombreJugador);
        TextInputEditText apellido = (TextInputEditText) findViewById(R.id.apellidoJugador);
        TextInputEditText edad = (TextInputEditText) findViewById(R.id.edadJugador);

        Spinner genero = (Spinner) findViewById(R.id.spinnerGeneroJugador);
        Spinner categoria = (Spinner) findViewById(R.id.spinnerCategoriaJugador);
        Spinner mano = (Spinner) findViewById(R.id.spinnerManoJugador);

        try {
            json.put("nombre", nombre.getText());
            json.put("apellido", apellido.getText());
            json.put("edad", edad.getText());
            json.put("categoria", categoria.getSelectedItem().toString());
            json.put("genero", genero.getSelectedItem().toString());
            json.put("mano", mano.getSelectedItem().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, "", json,
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
                });

        //Agregar json al request
        queue.add(jsonObjectRequest);

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
}
