package com.diegovaldesjr.tennistats.model;

import android.content.ContentValues;

import com.diegovaldesjr.tennistats.data.TennistatsContract;

import java.io.Serializable;

/**
 * Created by diego on 30/10/2017.
 */

public class Jugador implements Serializable {
    private int idJugador, edad;
    private String nombre, apellido, manoDiestra, genero, idUsuario;

    public Jugador(int idJugador, String nombre, String apellido, int edad, String manoDiestra, String genero, String idUsuario) {
        this.idJugador = idJugador;
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.manoDiestra = manoDiestra;
        this.genero = genero;
        this.idUsuario = idUsuario;
    }

    public Jugador(String nombre, String apellido, int edad, String manoDiestra, String genero, String idUsuario) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.manoDiestra = manoDiestra;
        this.genero = genero;
        this.idUsuario = idUsuario;
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

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getManoDiestra() {
        return manoDiestra;
    }

    public void setManoDiestra(String manoDiestra) {
        this.manoDiestra = manoDiestra;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public ContentValues jugadorToContentValues() {
        ContentValues values = new ContentValues();
        values.put(TennistatsContract.JugadorEntry.ID_USUARIO, idJugador);
        values.put(TennistatsContract.JugadorEntry.NOMBRE, nombre);
        values.put(TennistatsContract.JugadorEntry.APELLIDO, apellido);
        values.put(TennistatsContract.JugadorEntry.EDAD, edad);
        values.put(TennistatsContract.JugadorEntry.MANO_DIESTRA, manoDiestra);
        values.put(TennistatsContract.JugadorEntry.GENERO, genero);
        return values;
    }
}
