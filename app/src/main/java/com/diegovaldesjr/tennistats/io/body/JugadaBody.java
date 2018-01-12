package com.diegovaldesjr.tennistats.io.body;

/**
 * Created by diego on 12/01/2018.
 */

public class JugadaBody {
    int idSet, indice, zona;
    String tipoGolpe, tipoJugada;

    public JugadaBody(int idSet, int indice, int zona, String tipoGolpe, String tipoJugada) {
        this.idSet = idSet;
        this.indice = indice;
        this.zona = zona;
        this.tipoGolpe = tipoGolpe;
        this.tipoJugada = tipoJugada;
    }

    public int getIdSet() {
        return idSet;
    }

    public void setIdSet(int idSet) {
        this.idSet = idSet;
    }

    public int getIndice() {
        return indice;
    }

    public void setIndice(int indice) {
        this.indice = indice;
    }

    public int getZona() {
        return zona;
    }

    public void setZona(int zona) {
        this.zona = zona;
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
}
