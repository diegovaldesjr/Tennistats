package com.diegovaldesjr.tennistats.view;

import android.os.AsyncTask;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.diegovaldesjr.tennistats.R;
import com.diegovaldesjr.tennistats.data.SessionPrefs;
import com.diegovaldesjr.tennistats.data.TennistatsDbHelper;
import com.diegovaldesjr.tennistats.model.Jugador;

public class CrearJugadorActivity extends AppCompatActivity {

    private EditText nombre;
    private EditText apellido;
    private EditText edad;
    private Spinner genero;
    private Spinner mano;

    private TextInputLayout mFloatLabelNombre;
    private TextInputLayout mFloatLabelApellido;
    private TextInputLayout mFloatLabelEdad;

    private TennistatsDbHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_jugador);

        showToolbar(getResources().getString(R.string.app_name), true);

        nombre = (EditText) findViewById(R.id.nombreRegistrarJugador);
        apellido = (EditText) findViewById(R.id.apellidoRegistrarJugador);
        edad = (EditText) findViewById(R.id.edadRegistrarJugador);

        genero = (Spinner) findViewById(R.id.spinnerGeneroJugador);
        mano = (Spinner) findViewById(R.id.spinnerManoJugador);

        mFloatLabelNombre = (TextInputLayout) findViewById(R.id.float_label_nombre_jugador);
        mFloatLabelApellido = (TextInputLayout) findViewById(R.id.float_label_apellido_jugador);
        mFloatLabelEdad = (TextInputLayout) findViewById(R.id.float_label_edad_jugador);

        db = new TennistatsDbHelper(CrearJugadorActivity.this);

    }

    public void registrarJugador(View view){

        mFloatLabelNombre.setError(null);
        mFloatLabelApellido.setError(null);
        mFloatLabelEdad.setError(null);

        String nombre = this.nombre.getText().toString();
        String apellido = this.apellido.getText().toString();
        String edad = this.edad.getText().toString();
        String mano = this.mano.getSelectedItem().toString();
        String genero = this.genero.getSelectedItem().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(nombre)) {
            showError(getString(R.string.error_field_required));
            focusView = mFloatLabelNombre;
            cancel = true;
        }else if(!isValid(nombre)){
            showError(getString(R.string.error_invalid_nombre));
            focusView = mFloatLabelNombre;
            cancel = true;
        }

        if (TextUtils.isEmpty(apellido)) {
            showError(getString(R.string.error_field_required));
            focusView = mFloatLabelApellido;
            cancel = true;
        }else if(!isValid(apellido)){
            showError(getString(R.string.error_invalid_apellido));
            focusView = mFloatLabelApellido;
            cancel = true;
        }

        if (TextUtils.isEmpty(edad)) {
            showError(getString(R.string.error_field_required));
            focusView = mFloatLabelEdad;
            cancel = true;
        }else if(!isValid(edad)){
            showError(getString(R.string.error_invalid_edad));
            focusView = mFloatLabelEdad;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            Jugador jugador = new Jugador(
                    nombre,
                    apellido,
                    Integer.parseInt(edad),
                    mano,
                    genero,
                    SessionPrefs.get(CrearJugadorActivity.this).getUsername()
            );

            //Guardar jugador
            addJugador(jugador);
        }

    }

    private boolean isValid(String string) {
        return string.length() > 0;
    }

    private void showError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
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

    public void addJugador(Jugador jugador){
        new AddJugadorTask().execute(jugador);
    }

    private class AddJugadorTask extends AsyncTask<Jugador, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Jugador... params) {
            return db.saveJugador(params[0]) > 0;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                showError("Jugador agregado.");
                finish();
            } else {
                showError("Error al agregar jugador.");
            }
        }

    }
}
