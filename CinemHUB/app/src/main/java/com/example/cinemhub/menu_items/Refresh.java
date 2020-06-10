package com.example.cinemhub.menu_items;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.fragment.app.Fragment;

import com.example.cinemhub.R;
import com.example.cinemhub.menu_items.filtri.FilterHandler;
import com.example.cinemhub.model.Movie;
import com.example.cinemhub.ui.add_list.AddListFragment;
import com.example.cinemhub.ui.categorie.MostraCategoriaFragment;
import com.example.cinemhub.ui.nuovi_arrivi.NuoviArriviFragment;
import com.example.cinemhub.ui.piu_visti.PiuVistiFragment;
import com.example.cinemhub.ui.prossime_uscite.ProssimeUsciteFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Refresh {
    private static final String TAG = "Refresh";
    private Fragment fragment;
    private Menu menu;
    int c;

    public Refresh(Menu menu, Fragment fragment){
        this.fragment = fragment;
        this.menu = menu;
        c = 0;
        Log.d(TAG, "RefreshConstructor");
    }

    public void implementRefresh(int tipo) {
        Log.d(TAG, "ImplementRefresh");

        MenuItem refreshMenuItem;
        if(tipo==1)
            refreshMenuItem = menu.findItem(R.id.refresh);
        else
            refreshMenuItem = menu.findItem(R.id.refresh1); //1

        refreshMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Log.d(TAG, "ClickedRefresh");

                if(fragment instanceof MostraCategoriaFragment) {
                    ((MostraCategoriaFragment) fragment).setCanLoad(true);
                    ((MostraCategoriaFragment) fragment).initMovieRV(((MostraCategoriaFragment) fragment).getCurrentMovies());
                }else if(fragment instanceof AddListFragment) {
                    //((AddListFragment) fragment).setCanLoad(true);
                    ( (AddListFragment) fragment ).initMovieRV(( (AddListFragment) fragment ).getCurrentMovies());
                }else if(fragment instanceof PiuVistiFragment){
                    ((PiuVistiFragment) fragment).setCanLoad(true);
                    ((PiuVistiFragment) fragment).initMovieRV(((PiuVistiFragment) fragment).getCurrentMovies());
                }else if(fragment instanceof ProssimeUsciteFragment) {
                    ((ProssimeUsciteFragment) fragment).setCanLoad(true);
                    ((ProssimeUsciteFragment) fragment ).initMovieRV(( (ProssimeUsciteFragment) fragment ).getCurrentMovies());
                }else if(fragment instanceof NuoviArriviFragment){
                    ((NuoviArriviFragment) fragment).setCanLoad(true);
                    ((NuoviArriviFragment) fragment).initMovieRV(((NuoviArriviFragment) fragment).getCurrentMovies());
                }

                return true;
            }
        });
    }

    public void count() {
        c++;
        Log.d(TAG, "count() -> "+c);
    }

    public int getCount() {
        Log.d(TAG, "getCount() -> "+c);
        return c;
    }

}