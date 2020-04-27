package com.example.cinemhub.ui.home;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.example.cinemhub.model.Movie;
import com.example.cinemhub.model.MoviesPersistentData;

import java.util.List;

public class HomeViewModel extends ViewModel {

    private static final String TAG = "HomeViewModel";
    private MutableLiveData<List<Movie>> popolari;

    public HomeViewModel(SavedStateHandle savedStateHandle) {
        popolari = new MutableLiveData<>();
        List<Movie> movies = MoviesPersistentData.getInstance().getPopolari();
        if(movies != null)
            Log.d(TAG, "movies initialized");
        popolari.setValue(movies);
    }

    public LiveData<List<Movie>> getPopular() {
        return popolari;
    }
}