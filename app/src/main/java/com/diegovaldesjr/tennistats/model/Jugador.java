package com.diegovaldesjr.tennistats.model;

import java.io.Serializable;

/**
 * Created by diego on 30/10/2017.
 */

public class Jugador implements Serializable {
    private int idJugador, edad;
    private String nombre, apellido, categoria, mano;

    public Jugador(int idJugador, int edad, String nombre, String apellido, String categoria, String mano) {
        this.idJugador = idJugador;
        this.edad = edad;
        this.nombre = nombre;
        this.apellido = apellido;
        this.categoria = categoria;
        this.mano = mano;
    }

    public Jugador(int idJugador, String nombre, String apellido) {
        this.idJugador = idJugador;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public int getIdJugador() {
        return idJugador;
    }

    public void setIdJugador(int idJugador) {
        this.idJugador = idJugador;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getMano() {
        return mano;
    }

    public void setMano(String mano) {
        this.mano = mano;
    }
}
