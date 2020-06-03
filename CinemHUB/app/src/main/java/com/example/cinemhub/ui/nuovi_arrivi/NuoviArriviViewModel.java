package com.example.cinemhub.ui.nuovi_arrivi;

import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bumptech.glide.load.engine.Resource;
import com.example.cinemhub.model.Movie;
import com.example.cinemhub.model.MoviesRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class NuoviArriviViewModel extends ViewModel {
    private static final String TAG = "NuoviArriviViewModel";
    private MutableLiveData<List<Movie>> film;
    int page = 1;
    private int currentResults;
    private boolean isLoading;

    public MutableLiveData<List<Movie>> getProssimeUscite() {
        if(film == null) {
            film = new MutableLiveData<>();
            MoviesRepository.getInstance().getMovies("upcoming", page, film);
        }
        return film;
    }

    public LiveData<List<Movie>> getMoreProssimeUscite() {
        MoviesRepository.getInstance().getMovies("upcoming", page, film);
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