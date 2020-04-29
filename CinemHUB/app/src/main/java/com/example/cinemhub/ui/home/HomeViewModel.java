package com.example.cinemhub.ui.home;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.example.cinemhub.model.Movie;
import com.example.cinemhub.model.MoviesPersistentData;

import java.util.HashSet;
import java.util.List;

public class HomeViewModel extends ViewModel {

    private static final String TAG = "HomeViewModel";
    private MutableLiveData<HashSet<Movie>> popolari;

    public HomeViewModel(SavedStateHandle savedStateHandle) {
        popolari = new MutableLiveData<>();
        HashSet<Movie> movies = MoviesPersistentData.getInstance().getPopolari();
        HashSet<Movie> banane = MoviesPersistentData.getInstance().getAlCinema();
        HashSet<Movie> banane1= MoviesPersistentData.getInstance().getProssimeUscite();
        HashSet<Movie> banane2 = MoviesPersistentData.getInstance().getTopRated();

        if(!movies.isEmpty())
            Log.d(TAG, "popolari initialized");
        if(!banane.isEmpty())
            Log.d(TAG, "al cinema initialized");
        if(!banane1.isEmpty())
            Log.d(TAG, "prossime initialized");
        if(!banane2.isEmpty())
            Log.d(TAG, "top rated initialized");
        popolari.setValue(movies);
    }

    public LiveData<HashSet<Movie>> getPopular() {
        return popolari;
    }
}