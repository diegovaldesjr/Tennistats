package com.diegovaldesjr.tennistats.io.response;

import java.util.ArrayList;

/**
 * Created by diego on 26/11/2017.
 */

public class SetResponse {
    String idSet, numero, puntajej, puntajeo, ganador, idPartido;
    ArrayList<JugadaResponse> jugadas;
    ArrayList<SaqueResponse> saques;

    public SetResponse(String idSet, String numero, String puntajej, String puntajeo, String ganador, String idPartido, ArrayList<JugadaResponse> jugadas, ArrayList<SaqueResponse> saques) {
        this.idSet = idSet;
        this.numero = numero;
        this.puntajej = puntajej;
        this.puntajeo = puntajeo;
        this.ganador = ganador;
        this.idPartido = idPartido;
        this.jugadas = jugadas;
        this.saques = saques;
    }

    public String getIdSet() {
        return idSet;
    }

    public void setIdSet(String idSet) {
        this.idSet = idSet;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getPuntajej() {
        return puntajej;
    }

    public void setPuntajej(String puntajej) {
        this.puntajej = puntajej;
    }

    public String getPuntajeo() {
        return puntajeo;
    }

    public void setPuntajeo(String puntajeo) {
        this.puntajeo = puntajeo;
    }

    public String getGanador() {
        return ganador;
    }

    public void setGanador(String ganador) {
        this.ganador = ganador;
    }

    public String getPartido_idPartido() {
        return idPartido;
    }

    public void setPartido_idPartido(String partido_idPartido) {
        idPartido = partido_idPartido;
    }

    public String getIdPartido() {
        return idPartido;
    }

    public void setIdPartido(String idPartido) {
        this.idPartido = idPartido;
    }

    public ArrayList<JugadaResponse> getJugadas() {
        return jugadas;
    }

    public void setJugadas(ArrayList<JugadaResponse> jugadas) {
        this.jugadas = jugadas;
    }

    public ArrayList<SaqueResponse> getSaques() {
        return saques;
    }

    public void setSaques(ArrayList<SaqueResponse> saques) {
        this.saques = saques;
    }
}
