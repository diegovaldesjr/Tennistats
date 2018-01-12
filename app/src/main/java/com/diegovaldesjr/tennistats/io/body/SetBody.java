package com.diegovaldesjr.tennistats.io.body;

/**
 * Created by diego on 12/01/2018.
 */

public class SetBody {
    int puntajej, puntajeo;

    public SetBody(int puntajej, int puntajeo) {
        this.puntajej = puntajej;
        this.puntajeo = puntajeo;
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
}
