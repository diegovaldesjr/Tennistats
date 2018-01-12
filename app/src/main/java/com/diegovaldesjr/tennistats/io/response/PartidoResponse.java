package com.diegovaldesjr.tennistats.io.response;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by diego on 25/11/2017.
 */

public class PartidoResponse implements Serializable{
    String idPartido, fecha, categoria, idUsuario, idJugador, descripcion;
    JugadorResponse jugador;
    ArrayList<SetResponse> sets;

    public PartidoResponse(String idPartido, String fecha, String descripcion, String categoria, String idUsuario, String idJugador, JugadorResponse jugador, ArrayList<SetResponse> sets) {
        this.idPartido = idPartido;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.idUsuario = idUsuario;
        this.idJugador = idJugador;
        this.jugador = jugador;
        this.sets = sets;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getIdJugador() {
        return idJugador;
    }

    public void setIdJugador(String idJugador) {
        this.idJugador = idJugador;
    }

    public ArrayList<SetResponse> getSets() {
        return sets;
    }

    public void setSets(ArrayList<SetResponse> sets) {
        this.sets = sets;
    }

    public String getIdPartido() {
        return idPartido;
    }

    public void setIdPartido(String idPartido) {
        this.idPartido = idPartido;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getUsuario_idUsuario() {
        return idUsuario;
    }

    public void setUsuario_idUsuario(String usuario_idUsuario) {
        idUsuario = usuario_idUsuario;
    }

    public String getJugador_idJugador() {
        return idJugador;
    }

    public void setJugador_idJugador(String jugador_idJugador) {
        idJugador = jugador_idJugador;
    }

    public JugadorResponse getJugador() {
        return jugador;
    }

    public void setJugador(JugadorResponse jugador) {
        this.jugador = jugador;
    }
}
