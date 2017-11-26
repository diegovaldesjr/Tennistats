package com.diegovaldesjr.tennistats.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.diegovaldesjr.tennistats.view.fragments.TabJugadores;
import com.diegovaldesjr.tennistats.view.fragments.TabPartidos;

/**
 * Created by diego on 25/11/2017.
 */

public class PagerAdapter extends FragmentPagerAdapter {

    int nTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.nTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                TabPartidos tab1 = new TabPartidos();
                return tab1;
            case 1:
                TabJugadores tab2 = new TabJugadores();
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return nTabs;
    }
}
