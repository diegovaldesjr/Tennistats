package com.diegovaldesjr.tennistats.model;

/**
 * Created by diego on 27/11/2017.
 */

public class Jugada {
    String tipoGolpe, tipoJugada;

    public Jugada(String tipoGolpe, String tipoJugada) {
        this.tipoGolpe = tipoGolpe;
        this.tipoJugada = tipoJugada;
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
