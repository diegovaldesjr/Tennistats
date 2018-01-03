package com.diegovaldesjr.tennistats.model;

import android.content.ContentValues;

import com.diegovaldesjr.tennistats.data.TennistatsContract;

/**
 * Created by diego on 27/11/2017.
 */

public class Jugada {
    int indice, idSet, zona;
    String tipoGolpe, tipoJugada;

    public Jugada(int indice, int idSet, int zona, String tipoGolpe, String tipoJugada) {
        this.indice = indice;
        this.idSet = idSet;
        this.zona = zona;
        this.tipoGolpe = tipoGolpe;
        this.tipoJugada = tipoJugada;
    }

    public Jugada(){

    }

    public int getIndice() {
        return indice;
    }

    public void setIndice(int indice) {
        this.indice = indice;
    }

    public int getIdSet() {
        return idSet;
    }

    public void setIdSet(int idSet) {
        this.idSet = idSet;
    }

    public String getTipoGolpe() {
        return tipoGolpe;
    }

    public void setTipoGolpe(String tipoGolpe) {
        this.tipoGolpe = tipoGolpe;
    }

    public String getTipoJugada() {
        return tipoJugada;
    }

    public void setTipoJugada(String tipoJugada) {
        this.tipoJugada = tipoJugada;
    }

    public int getZona() {
        return zona;
    }

    public void setZona(int zona) {
        this.zona = zona;
    }

    public ContentValues jugadaToContentValues() {
        ContentValues values = new ContentValues();
        values.put(TennistatsContract.JugadaEntry.INDICE, indice);
        values.put(TennistatsContract.JugadaEntry.ID_SET, idSet);
        values.put(TennistatsContract.JugadaEntry.TIPO_GOLPE, tipoGolpe);
        values.put(TennistatsContract.JugadaEntry.TIPO_JUGADA, tipoJugada);
        values.put(TennistatsContract.JugadaEntry.ZONA, zona);
        return values;
    }
}
