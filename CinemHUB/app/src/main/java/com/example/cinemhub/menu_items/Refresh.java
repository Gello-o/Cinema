package com.example.cinemhub.menu_items;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.fragment.app.Fragment;

import com.example.cinemhub.R;
import com.example.cinemhub.menu_items.ricerca.SearchFragment;
import com.example.cinemhub.ui.categorie.MostraCategoriaFragment;
import com.example.cinemhub.ui.nuovi_arrivi.NuoviArriviFragment;
import com.example.cinemhub.ui.piu_visti.PiuVistiFragment;
import com.example.cinemhub.ui.prossime_uscite.ProssimeUsciteFragment;

public class Refresh {
    private static final String TAG = "Refresh";
    private Fragment fragment;
    private Menu menu;

    public Refresh(Menu menu, Fragment fragment){
        this.fragment = fragment;
        this.menu = menu;
        Log.d(TAG, "RefreshConstructor");
    }

    public void implementRefresh(int tipo) {
        Log.d(TAG, "ImplementRefresh");

        MenuItem refreshMenuItem;
        if(tipo==1)
            refreshMenuItem = menu.findItem(R.id.refresh);
        else
            refreshMenuItem = menu.findItem(R.id.refresh1);

        refreshMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Log.d(TAG, "ClickedRefresh");

                if(fragment instanceof MostraCategoriaFragment) {
                    ((MostraCategoriaFragment) fragment).setCanLoad(true);
                    ((MostraCategoriaFragment) fragment).initMovieRV(((MostraCategoriaFragment) fragment).getCurrentMovies());
                }else if(fragment instanceof PiuVistiFragment){
                    ((PiuVistiFragment) fragment).setCanLoad(true);
                    ((PiuVistiFragment) fragment).initMovieRV(((PiuVistiFragment) fragment).getCurrentMovies());
                }else if(fragment instanceof ProssimeUsciteFragment) {
                    ((ProssimeUsciteFragment) fragment).setCanLoad(true);
                    ((ProssimeUsciteFragment) fragment ).initMovieRV(( (ProssimeUsciteFragment) fragment ).getCurrentMovies());
                }else if(fragment instanceof NuoviArriviFragment){
                    ((NuoviArriviFragment) fragment).setCanLoad(true);
                    ((NuoviArriviFragment) fragment).initMovieRV(((NuoviArriviFragment) fragment).getCurrentMovies());
                }else if(fragment instanceof SearchFragment){
                    ((SearchFragment) fragment).setCanLoad(true);
                    ((SearchFragment) fragment).initMovieRV(((SearchFragment) fragment).getCurrentMovies());
                }

                return true;
            }
        });
    }

}