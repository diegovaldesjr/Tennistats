package com.diegovaldesjr.tennistats.io.response;

/**
 * Created by diego on 26/11/2017.
 */

public class SetResponse {
    String idSet, numero, puntajej, puntajeo, ganador, Partido_idPartido;

    public SetResponse(String idSet, String numero, String puntajej, String puntajeo, String ganador, String partido_idPartido) {
        this.idSet = idSet;
        this.numero = numero;
        this.puntajej = puntajej;
        this.puntajeo = puntajeo;
        this.ganador = ganador;
        Partido_idPartido = partido_idPartido;
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
        return Partido_idPartido;
    }

    public void setPartido_idPartido(String partido_idPartido) {
        Partido_idPartido = partido_idPartido;
    }
}
