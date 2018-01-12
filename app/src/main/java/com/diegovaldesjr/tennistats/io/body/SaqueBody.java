package com.diegovaldesjr.tennistats.io.body;

/**
 * Created by diego on 12/01/2018.
 */

public class SaqueBody {
    int idSet, indice, zona;
    String tipoGolpe, tipoSaque;

    public SaqueBody(int idSet, int indice, int zona, String tipoGolpe, String tipoSaque) {
        this.idSet = idSet;
        this.indice = indice;
        this.zona = zona;
        this.tipoGolpe = tipoGolpe;
        this.tipoSaque = tipoSaque;
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

    public String getTipoSaque() {
        return tipoSaque;
    }

    public void setTipoSaque(String tipoSaque) {
        this.tipoSaque = tipoSaque;
    }
}
