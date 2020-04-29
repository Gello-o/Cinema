package com.example.cinemhub.ui.nuovi_arrivi;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cinemhub.model.Movie;
import com.example.cinemhub.model.MoviesPersistentData;

import java.util.HashSet;

public class NuoviArriviViewModel extends ViewModel {
    private static final String TAG = "NuoviArriviViewModel";
    private MutableLiveData<HashSet<Movie>> mText;

    public NuoviArriviViewModel() {
        mText = new MutableLiveData<>();
        HashSet <Movie> movies = MoviesPersistentData.getInstance().getAlCinema();
        if(movies.isEmpty()){
            Log.d(TAG, "Movies Empty");
        }
        mText.setValue(movies);
    }

    public LiveData<HashSet<Movie>> getText() {
        return mText;
    }
}