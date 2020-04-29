package com.example.cinemhub.ui.categorie;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cinemhub.model.Movie;
import com.example.cinemhub.model.MoviesPersistentData;

import java.util.HashSet;
import java.util.List;

public class CategorieViewModel extends ViewModel {

    private static final String TAG = "CategorieViewModel";
    private MutableLiveData<HashSet<Movie>> mText;

    public CategorieViewModel() {
        mText = new MutableLiveData<>();
        while(MoviesPersistentData.getInstance().getPopolari().isEmpty()){}

        HashSet<Movie> movies = MoviesPersistentData.getInstance().getPopolari();

        if(!movies.isEmpty())
            Log.d(TAG, "movies initialized");
        mText.setValue(movies);
    }

    public LiveData<HashSet<Movie>> getPopolari() {
        return mText;
    }
}