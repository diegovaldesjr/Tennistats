package com.diegovaldesjr.tennistats.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.diegovaldesjr.tennistats.R;
import com.diegovaldesjr.tennistats.data.SessionPrefs;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Redirección al Login
        if (!SessionPrefs.get(this).isLoggedIn()) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }else{
            startActivity(new Intent(this, ContainerActivity.class));
            finish();
            return;
        }
    }
}
