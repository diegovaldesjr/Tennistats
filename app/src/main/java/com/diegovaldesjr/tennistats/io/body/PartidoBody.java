package com.diegovaldesjr.tennistats.io.body;

/**
 * Created by diego on 31/12/2017.
 */

public class PartidoBody {
    private int idJugador;
    private String categoria, fecha;
    //private Date fecha;

    public PartidoBody(int idJugador, String categoria, String fecha){
        this.idJugador = idJugador;
        this.categoria = categoria;
        this.fecha = fecha;
    }

    public int getIdJugador() {
        return idJugador;
    }

    public void setIdJugador(int idJugador) {
        this.idJugador = idJugador;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

}
