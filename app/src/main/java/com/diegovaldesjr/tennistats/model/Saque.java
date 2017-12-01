package com.diegovaldesjr.tennistats.model;

/**
 * Created by diego on 27/11/2017.
 */

public class Saque {
    String tipoSaque, tipoGolpe;

    public Saque(String tipoSaque, String tipoGolpe) {
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
}
