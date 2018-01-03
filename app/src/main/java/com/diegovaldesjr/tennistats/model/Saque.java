package com.diegovaldesjr.tennistats.model;

import android.content.ContentValues;

import com.diegovaldesjr.tennistats.data.TennistatsContract;

/**
 * Created by diego on 27/11/2017.
 */

public class Saque {
    int indice, idSet;
    String tipoSaque, tipoGolpe;

    public Saque(int indice, int idSet, String tipoSaque, String tipoGolpe) {
        this.indice = indice;
        this.idSet = idSet;
        this.tipoSaque = tipoSaque;
        this.tipoGolpe = tipoGolpe;
    }

    public String getTipoSaque() {
        return tipoSaque;
    }

    public void setTipoSaque(String tipoSaque) {
        this.tipoSaque = tipoSaque;
    }

    public String getTipoGolpe() {
        return tipoGolpe;
    }

    public void setTipoGolpe(String tipoGolpe) {
        this.tipoGolpe = tipoGolpe;
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

    public ContentValues saqueToContentValues() {
        ContentValues values = new ContentValues();
        values.put(TennistatsContract.SaqueEntry.INDICE, indice);
        values.put(TennistatsContract.SaqueEntry.ID_SET, idSet);
        values.put(TennistatsContract.SaqueEntry.TIPO_GOLPE, tipoGolpe);
        values.put(TennistatsContract.SaqueEntry.TIPO_SAQUE, tipoSaque);
        return values;
    }

}
