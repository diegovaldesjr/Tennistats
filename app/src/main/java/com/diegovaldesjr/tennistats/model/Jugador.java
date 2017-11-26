package com.diegovaldesjr.tennistats.model;

import java.io.Serializable;

/**
 * Created by diego on 30/10/2017.
 */

public class Jugador implements Serializable {
    private int idJugador, edad;
    private String nombre, apellido, manoDiestra, genero;

    public Jugador(int idJugador, int edad, String nombre, String apellido, String manoDiestra, String genero) {
        this.idJugador = idJugador;
        this.edad = edad;
        this.nombre = nombre;
        this.apellido = apellido;
        this.manoDiestra = manoDiestra;
        this.genero = genero;
    }

    public Jugador(int idJugador, String nombre, String apellido) {
        this.idJugador = idJugador;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public Jugador(int edad, String nombre, String apellido, String manoDiestra, String genero) {
        this.edad = edad;
        this.nombre = nombre;
        this.apellido = apellido;
        this.manoDiestra = manoDiestra;
        this.genero = genero;
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

    public String getMano() {
        return manoDiestra;
    }

    public void setMano(String mano) {
        this.manoDiestra = mano;
    }
}
