package com.diegovaldesjr.tennistats.model;

import android.content.ContentValues;

import com.diegovaldesjr.tennistats.data.TennistatsContract;

/**
 * Created by diego on 30/12/2017.
 */

public class Set {

    int idSet, idPartido, numero, puntajej, puntajeo;
    String  ganador;

    public Set(int idSet, int idPartido, int numero, int puntajej, int puntajeo, String ganador) {
        this.idSet = idSet;
        this.idPartido = idPartido;
        this.numero = numero;
        this.puntajej = puntajej;
        this.puntajeo = puntajeo;
        this.ganador = ganador;
    }

    public int getIdSet() {
        return idSet;
    }

    public void setIdSet(int idSet) {
        this.idSet = idSet;
    }

    public int getIdPartido() {
        return idPartido;
    }

    public void setIdPartido(int idPartido) {
        this.idPartido = idPartido;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getPuntajej() {
        return puntajej;
    }

    public void setPuntajej(int puntajej) {
        this.puntajej = puntajej;
    }

    public int getPuntajeo() {
        return puntajeo;
    }

    public void setPuntajeo(int puntajeo) {
        this.puntajeo = puntajeo;
    }

    public String getGanador() {
        return ganador;
    }

    public void setGanador(String ganador) {
        this.ganador = ganador;
    }

    public ContentValues setToContentValues() {
        ContentValues values = new ContentValues();
        values.put(TennistatsContract.SetEntry.ID_PARTIDO, idPartido);
        values.put(TennistatsContract.SetEntry.NUMERO, numero);
        values.put(TennistatsContract.SetEntry.PUNTAJEJ, puntajej);
        values.put(TennistatsContract.SetEntry.PUNTAJEO, puntajeo);
        values.put(TennistatsContract.SetEntry.GANADOR, ganador);
        return values;
    }
}
