package com.diegovaldesjr.tennistats.io.body;

/**
 * Created by diego on 05/01/2018.
 */

public class JugadorBody {

    String nombre, apellido, mano, genero;
    int edad;

    public JugadorBody(String nombre, String apellido, int edad, String manoDiestra, String genero) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.mano = manoDiestra;
        this.genero = genero;
    }
}
