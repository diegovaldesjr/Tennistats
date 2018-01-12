package com.diegovaldesjr.tennistats.io.response;

import java.util.ArrayList;

/**
 * Created by diego on 12/01/2018.
 */

public class StatusResponse {
    String status_code;
    PartidoResponse partido;
    JugadorResponse jugador;
    SetResponse set;
    JugadaResponse jugada;
    SaqueResponse saque;

    ArrayList<PartidoResponse> partidos;
    ArrayList<SetResponse> sets;

    public StatusResponse(String status_code, PartidoResponse partido, JugadorResponse jugador, SetResponse set, JugadaResponse jugada, SaqueResponse saque, ArrayList<PartidoResponse> partidos, ArrayList<SetResponse> sets) {
        this.status_code = status_code;
        this.partido = partido;
        this.jugador = jugador;
        this.set = set;
        this.jugada = jugada;
        this.saque = saque;
        this.partidos = partidos;
        this.sets = sets;
    }

    public String getStatus_code() {
        return status_code;
    }

    public void setStatus_code(String status_code) {
        this.status_code = status_code;
    }

    public PartidoResponse getPartido() {
        return partido;
    }

    public void setPartido(PartidoResponse partido) {
        this.partido = partido;
    }

    public JugadorResponse getJugador() {
        return jugador;
    }

    public void setJugador(JugadorResponse jugador) {
        this.jugador = jugador;
    }

    public SetResponse getSet() {
        return set;
    }

    public void setSet(SetResponse set) {
        this.set = set;
    }

    public JugadaResponse getJugada() {
        return jugada;
    }

    public void setJugada(JugadaResponse jugada) {
        this.jugada = jugada;
    }

    public SaqueResponse getSaque() {
        return saque;
    }

    public void setSaque(SaqueResponse saque) {
        this.saque = saque;
    }

    public ArrayList<PartidoResponse> getPartidos() {
        return partidos;
    }

    public void setPartidos(ArrayList<PartidoResponse> partidos) {
        this.partidos = partidos;
    }

    public ArrayList<SetResponse> getSets() {
        return sets;
    }

    public void setSets(ArrayList<SetResponse> sets) {
        this.sets = sets;
    }
}
