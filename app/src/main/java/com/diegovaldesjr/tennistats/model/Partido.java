package com.diegovaldesjr.tennistats.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by diego on 29/10/2017.
 */

public class Partido implements Serializable {

    private String jugador, categoria;
    private Date fecha;

    public Partido(String jugador, Date fecha, String categoria) {
        this.jugador = jugador;
        this.fecha = fecha;
        this.categoria = categoria;
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
