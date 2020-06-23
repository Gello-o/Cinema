package com.example.cinemhub.ui.piu_visti;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cinemhub.model.Movie;
import com.example.cinemhub.model.MoviesRepository;
import com.example.cinemhub.model.Resource;

import java.util.List;

public class PiuVistiViewModel extends ViewModel {
    //private static final String TAG = "NuoviArriviViewModel";
    private MutableLiveData<Resource<List<Movie>>> film;
    private int page = 1;
    private int currentResults;
    private boolean isLoading;

    public MutableLiveData<Resource<List<Movie>>> getPiuVisti() {
        if(film == null) {
            film = new MutableLiveData<>();
            MoviesRepository.getInstance().getMoviesLL("top_rated", page, film);
        }
        return film;
    }

    public MutableLiveData<Resource<List<Movie>>> getMorePiuVisti() {
        MoviesRepository.getInstance().getMoviesLL("top_rated", page, film);
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

}