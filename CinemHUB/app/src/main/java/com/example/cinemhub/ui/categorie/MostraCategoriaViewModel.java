package com.example.cinemhub.ui.categorie;

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

public class MostraCategoriaViewModel extends ViewModel {
    private MutableLiveData<Resource<List<Movie>>> film;
    //private static final String TAG = "MostraCategorieFragment";
    private int page = 1;
    private int genere;
    private int currentResults;
    private boolean isLoading;
    private boolean canLoad = true;

    MutableLiveData<Resource<List<Movie>>> getGenere(int genere) {
        this.genere = genere;
        if(film == null) {
            film = new MutableLiveData<>();
            MoviesRepository.getInstance().getGenresLL(genere, page, film);
        }
        return film;
    }

    MutableLiveData<Resource<List<Movie>>> getMoreGenere() {
        MoviesRepository.getInstance().getGenresLL(genere, page, film);
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

    public void initSearch(@NonNull Menu menu, Fragment fragment){
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