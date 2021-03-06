package com.example.cinemhub.ui.nuovi_arrivi;


import android.view.Menu;

import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cinemhub.menu_items.filtri.FilterHandler;
import com.example.cinemhub.menu_items.ricerca.SearchHandler;
import com.example.cinemhub.model.Movie;
import com.example.cinemhub.model.MoviesRepository;
import com.example.cinemhub.model.Resource;

import java.util.List;

public class NuoviArriviViewModel extends ViewModel {
    //private static final String TAG = "NuoviArriviViewModel";
    private MutableLiveData<Resource<List<Movie>>> film;
    private int page = 1;
    private int currentResults;
    private boolean isLoading;
    private boolean canLoad = true;
    private List<Movie> filtered;

    public List<Movie> getFiltered() {
        return filtered;
    }

    public void setFiltered(List<Movie> filtered) {
        this.filtered = filtered;
    }

    MutableLiveData<Resource<List<Movie>>> getProssimeUscite() {
        if(film == null) {
            film = new MutableLiveData<>();
            MoviesRepository.getInstance().getMoviesLL("now_playing", page, film);
        }
        return film;
    }

    MutableLiveData<Resource<List<Movie>>> getMoreProssimeUscite() {
        MoviesRepository.getInstance().getMoviesLL("now_playing", page, film);
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
        filterOperation.implementFilter(2);
    }

    void initSearch(@NonNull Menu menu, Fragment fragment){
        SearchHandler searchOperation = new SearchHandler(menu, fragment);
        searchOperation.implementSearch(2);
    }

    public boolean canLoad() {
        return canLoad;
    }

    public void setCanLoad(boolean canLoad) {
        this.canLoad = canLoad;
    }
}