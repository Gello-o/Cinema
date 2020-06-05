package com.example.cinemhub;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;

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
    List<Movie> moviesGlobal;
    int c;

    public Refresh(Menu menu, Fragment fragment){
        this.fragment = fragment;
        this.menu = menu;
        c = 0;
        moviesGlobal = new ArrayList<>();
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

                //Qua stampa la lista attuale invece che quella iniziale (da fixare)
                /*
                for(Movie m : moviesGlobal)
                    Log.d(TAG, "Film: "+m.getTitle());
                */
                Log.d(TAG, "#Film: "+moviesGlobal.size());

                if(fragment instanceof MostraCategoriaFragment)
                    ((MostraCategoriaFragment) fragment).initMovieRV(moviesGlobal);
                else if(fragment instanceof AddListFragment)
                    ((AddListFragment) fragment).initMovieRV(moviesGlobal);
                else if(fragment instanceof PiuVistiFragment)
                    ((PiuVistiFragment) fragment).initMovieRV(moviesGlobal);
                else if(fragment instanceof ProssimeUsciteFragment)
                    ((ProssimeUsciteFragment) fragment).initMovieRV(moviesGlobal);
                else if(fragment instanceof NuoviArriviFragment)
                    ((NuoviArriviFragment) fragment).initMovieRV(moviesGlobal);

                return true;
            }
        });
    }

    public void setMovie(Set<Movie> moviesGlobal) {
        Log.d(TAG, "setMovie ");
        if(moviesGlobal==null) {
            this.moviesGlobal = new ArrayList<>();
            Log.d(TAG, "setMovieNull");
        }
        else {
            this.moviesGlobal.addAll(moviesGlobal);
            Log.d(TAG, "setMovieNotNull");
        }
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