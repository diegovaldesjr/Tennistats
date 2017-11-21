package com.diegovaldesjr.tennistats;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    SharedPreferences user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        user = getSharedPreferences("user", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = user.edit();
        editor.putString("ip_address", "192.168.xx.xx:8000");
        editor.commit();
    }

    public void avanzarHome(){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    public void iniciarSesion(View view){

        JSONObject json = new JSONObject();

        try {
            json.put("grant_type", "password");
            json.put("client_id", "2");
            json.put("client_secret", "wzeiLKWbx4Goyy2gSiwICgTxa589mXrYa4PxQ0Hq");
            json.put("username", "luis");
            json.put("password", "luis123");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, "http://"+user.getString("ip_address","")+"/oauth/token", json,
                new Response.Listener<JSONObject>(){
                    //Listener para exito
                    @Override
                    public void onResponse(JSONObject response) {
                        //Aqui se actualiza
                        SharedPreferences.Editor editor = user.edit();

                        try {
                            editor.putString("access_token", response.getString("access_token"));
                            editor.commit();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        avanzarHome();
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
        queue.add(jsonObjectRequest);
    }
}
