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

/*
l'oggetto Refresh ha la responsabilità di ripristinare la lista globale di film
dopo che è avvenuto un filtraggio. Esso abilita nuovamente il lazy loading, che è stato
disabilitato se si ha filtrato
*/

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

        refreshMenuItem.setOnMenuItemClickListener(item -> {
            Log.d(TAG, "ClickedRefresh");

            if(fragment instanceof MostraCategoriaFragment) {
                if(((MostraCategoriaFragment) fragment).getCurrentMovies() != null){
                    ((MostraCategoriaFragment) fragment).setCanLoad(true);
                    ((MostraCategoriaFragment) fragment).initMovieRV(((MostraCategoriaFragment) fragment).getCurrentMovies());
                 }

            }else if(fragment instanceof PiuVistiFragment){
                if(((PiuVistiFragment) fragment).getCurrentMovies() != null){
                    ((PiuVistiFragment) fragment).setCanLoad(true);
                    ((PiuVistiFragment) fragment).initMovieRV(((PiuVistiFragment) fragment).getCurrentMovies());
                }
            }else if(fragment instanceof ProssimeUsciteFragment) {
                if(((ProssimeUsciteFragment) fragment).getCurrentMovies() != null){
                    ((ProssimeUsciteFragment) fragment).setCanLoad(true);
                    ((ProssimeUsciteFragment) fragment ).initMovieRV(( (ProssimeUsciteFragment) fragment ).getCurrentMovies());
                }
            }else if(fragment instanceof NuoviArriviFragment){
                if(((NuoviArriviFragment) fragment).getCurrentMovies() != null){
                    ((NuoviArriviFragment) fragment).setCanLoad(true);
                    ((NuoviArriviFragment) fragment).initMovieRV(((NuoviArriviFragment) fragment).getCurrentMovies());
                }
            }else if(fragment instanceof SearchFragment){
                if(((SearchFragment) fragment).getCurrentMovies() != null){
                    ((SearchFragment) fragment).setCanLoad(true);
                    ((SearchFragment) fragment).initMovieRV(((SearchFragment) fragment).getCurrentMovies());
                }
            }

            return true;
        });
    }

}