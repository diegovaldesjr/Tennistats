package com.diegovaldesjr.tennistats.io.response;

import java.util.ArrayList;

/**
 * Created by diego on 25/11/2017.
 */

public class JugadorResponse {
    String idJugador, nombre, apellido, edad, manoDiestra, genero, idUsuario;
    ArrayList<PartidoResponse> partidos;

    public JugadorResponse(String idJugador, String nombre, String apellido, String edad, String manoDiestra, String genero, String idUsuario) {
        this.idJugador = idJugador;
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.manoDiestra = manoDiestra;
        this.genero = genero;
        this.idUsuario = idUsuario;
    }

    public JugadorResponse(String idJugador, String nombre, String apellido, String edad, String manoDiestra, String genero, String idUsuario, ArrayList<PartidoResponse> partidos) {
        this.idJugador = idJugador;
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.manoDiestra = manoDiestra;
        this.genero = genero;
        this.idUsuario = idUsuario;
        this.partidos = partidos;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public ArrayList<PartidoResponse> getPartidos() {
        return partidos;
    }

    public void setPartidos(ArrayList<PartidoResponse> partidos) {
        this.partidos = partidos;
    }

    public String getIdJugador() {
        return idJugador;
    }

    public void setIdJugador(String idJugador) {
        this.idJugador = idJugador;
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

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getManoDiestra() {
        return manoDiestra;
    }

    public void setManoDiestra(String manoDiestra) {
        this.manoDiestra = manoDiestra;
    }

    public String getUsuario_idUsuario() {
        return idUsuario;
    }

    public void setUsuario_idUsuario(String usuario_idUsuario) {
        idUsuario = usuario_idUsuario;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }
}
