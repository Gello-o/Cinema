package com.example.cinemhub.ui.home;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.example.cinemhub.model.Movie;
import com.example.cinemhub.model.MoviesPersistentData;

import java.util.HashSet;
import java.util.HashSet;
import java.util.List;

public class HomeViewModel extends ViewModel {

    private static final String TAG = "HomeViewModel";
    private MutableLiveData<HashSet<Movie>> popolari;
    private MutableLiveData<HashSet<Movie>> alCinema;
    private MutableLiveData<HashSet<Movie>> topRated;
    private MutableLiveData<HashSet<Movie>> prossimeUscite;

    public HomeViewModel(SavedStateHandle savedStateHandle) {
        MoviesPersistentData db = MoviesPersistentData.getInstance();
        popolari = db.getPopolari();
        alCinema = db.getAlCinema();
        topRated = db.getTopRated();
        prossimeUscite = db.getProssimeUscite();
    }

    public LiveData<HashSet<Movie>> getPopolari() { return popolari; }

    public LiveData<HashSet<Movie>> getAlCinema() {
        return alCinema;
    }

    public LiveData<HashSet<Movie>> getProssimeUscite() {
        return prossimeUscite;
    }

    public LiveData<HashSet<Movie>> getTopRated() {
        return topRated;
    }
}