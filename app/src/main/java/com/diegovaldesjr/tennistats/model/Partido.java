package com.diegovaldesjr.tennistats.model;

import android.content.ContentValues;
import android.database.Cursor;

import com.diegovaldesjr.tennistats.data.TennistatsContract;
import com.diegovaldesjr.tennistats.io.response.SetResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by diego on 29/10/2017.
 */

public class Partido implements Serializable {

    private int idPartido,idJugador;
    private String categoria, fecha, idUsuario;
    //private Date fecha;
    private ArrayList<Set> sets;
    private Jugador jugador;

    public Partido(int idPartido, int idJugador, String fecha, String categoria, String idUsuario) {
        this.idPartido = idPartido;
        this.idJugador = idJugador;
        this.fecha = fecha;
        this.categoria = categoria;
        this.idUsuario = idUsuario;
    }

    public Partido(int idJugador, String fecha, String categoria, String idUsuario) {
        this.idJugador = idJugador;
        this.fecha = fecha;
        this.categoria = categoria;
        this.idUsuario = idUsuario;
    }

    public Partido(int idPartido, int idJugador, String categoria, String fecha, String idUsuario, ArrayList<Set> sets, Jugador jugador) {
        this.idPartido = idPartido;
        this.idJugador = idJugador;
        this.categoria = categoria;
        this.fecha = fecha;
        this.idUsuario = idUsuario;
        this.sets = sets;
        this.jugador = jugador;
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

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public ArrayList<Set> getSets() {
        return sets;
    }

    public void setSets(ArrayList<Set> sets) {
        this.sets = sets;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    public ContentValues partidoToContentValues() {
        ContentValues values = new ContentValues();
        values.put(TennistatsContract.PartidoEntry.ID_JUGADOR, idJugador);
        values.put(TennistatsContract.PartidoEntry.ID_USUARIO, idUsuario);
        values.put(TennistatsContract.PartidoEntry.CATEGORIA, categoria);
        values.put(TennistatsContract.PartidoEntry.FECHA, fecha);
        return values;
    }
}
