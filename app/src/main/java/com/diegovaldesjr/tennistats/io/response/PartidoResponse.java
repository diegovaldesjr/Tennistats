package com.diegovaldesjr.tennistats.io.response;

/**
 * Created by diego on 25/11/2017.
 */

public class PartidoResponse {
    String idPartido, fecha, descripcion, categoria, Usuario_idUsuario, Jugador_idJugador;
    JugadorResponse jugador;

    public PartidoResponse(String idPartido, String fecha, String descripcion, String categoria, String usuario_idUsuario, String jugador_idJugador, JugadorResponse jugador) {
        this.idPartido = idPartido;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.Usuario_idUsuario = usuario_idUsuario;
        this.Jugador_idJugador = jugador_idJugador;
        this.jugador = jugador;
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
        return Usuario_idUsuario;
    }

    public void setUsuario_idUsuario(String usuario_idUsuario) {
        Usuario_idUsuario = usuario_idUsuario;
    }

    public String getJugador_idJugador() {
        return Jugador_idJugador;
    }

    public void setJugador_idJugador(String jugador_idJugador) {
        Jugador_idJugador = jugador_idJugador;
    }

    public JugadorResponse getJugador() {
        return jugador;
    }

    public void setJugador(JugadorResponse jugador) {
        this.jugador = jugador;
    }
}
