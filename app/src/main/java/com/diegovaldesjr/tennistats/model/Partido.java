package com.diegovaldesjr.tennistats.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by diego on 29/10/2017.
 */

public class Partido implements Serializable {

    private int idPartido,idJugador;
    private String jugador, categoria;
    private Date fecha;

    public Partido(int idPartido, int idJugador, String jugador, Date fecha, String categoria) {
        this.idPartido = idPartido;
        this.idJugador = idJugador;
        this.jugador = jugador;
        this.fecha = fecha;
        this.categoria = categoria;
    }

    public Partido(int idJugador, Date fecha, String categoria) {
        this.idJugador = idJugador;
        this.fecha = fecha;
        this.categoria = categoria;
    }

    public Partido(){

    }

    public int getIdPartido() {
        return idPartido;
    }

    public void setIdPartido(int idPartido) {
        this.idPartido = idPartido;
    }

    public int getIdJugador() {
        return idJugador;
    }

    public void setIdJugador(int idJugador) {
        this.idJugador = idJugador;
    }

    public String getJugador() {
        return jugador;
    }

    public void setJugador(String jugador) {
        this.jugador = jugador;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
