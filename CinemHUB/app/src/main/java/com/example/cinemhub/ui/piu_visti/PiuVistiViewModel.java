package com.example.cinemhub.ui.piu_visti;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cinemhub.model.Movie;
import com.example.cinemhub.model.MoviesRepository;

import java.util.List;

public class PiuVistiViewModel extends ViewModel {
    private MutableLiveData<List<Movie>> film;
    int page = 4;
    private MoviesRepository repo;
    private int currentResults;
    private boolean isLoading;

    public MutableLiveData<List<Movie>> getPiuVisti() {
        if(film == null) {
            film = new MutableLiveData<>();
            repo = MoviesRepository.getInstance();
            for(int i=1; i<page; i++)
                repo.getMovies("top_rated", i, film);
        }
        return film;
    }


    public LiveData<List<Movie>> getMorePiuVisti() {
        repo = MoviesRepository.getInstance();
        for(int i=1; i<page; i++)
            repo.getMovies("top_rated", i, film);
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