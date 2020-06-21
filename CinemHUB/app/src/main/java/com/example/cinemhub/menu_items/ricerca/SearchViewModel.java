package com.example.cinemhub.menu_items.ricerca;

import android.util.Log;
import android.view.Menu;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cinemhub.menu_items.filtri.FilterHandler;
import com.example.cinemhub.model.Movie;
import com.example.cinemhub.model.MoviesRepository;
import com.example.cinemhub.model.Resource;

import java.util.List;

/*
ViewModel con cui interagisce l'oggetto SearchFragment: preserva i dati del fragment dai suoi
cambiamenti di stato, sfrutta l'oggetto MoviesRepository per inizializzare il dato che punta alla
lista di film e gestisce il lazy loading del fragment attraverso la variabile canLoad
*/

public class SearchViewModel extends ViewModel {
    private MutableLiveData<Resource<List<Movie>>> film;
    private static final String TAG = "SearchViewModel";
    private int page = 1;
    private String query;
    private int currentResults;
    private boolean isLoading;
    private boolean canLoad = true;

    MutableLiveData<Resource<List<Movie>>> doSearch(String query) {
        this.query = query;
        Log.d(TAG, "query VM " + query);
        if(film == null) {
            Log.d(TAG, "film = null");
            film = new MutableLiveData<>();
            MoviesRepository.getInstance().searchMovie(query, page, film);
        }
        return film;
    }

    MutableLiveData<Resource<List<Movie>>> searchMore() {
        MoviesRepository.getInstance().searchMovie(query, page, film);
        return film;
    }

    public MutableLiveData<Resource<List<Movie>>> getMoviesLiveData() {
        return film;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getCurrentResults() {
        return currentResults;
    }

    public void setCurrentResults(int currentResults) {
        this.currentResults = currentResults;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    public void initFilters(@NonNull Menu menu, Fragment fragment){
        FilterHandler filterOperation = new FilterHandler(menu, fragment);
        filterOperation.implementFilter(1);
    }

    public boolean canLoad() {
        return canLoad;
    }

    public void setCanLoad(boolean canLoad) {
        this.canLoad = canLoad;
    }

}