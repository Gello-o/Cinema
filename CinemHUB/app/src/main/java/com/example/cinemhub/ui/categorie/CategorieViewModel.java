package com.example.cinemhub.ui.categorie;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cinemhub.model.Movie;
import com.example.cinemhub.model.MoviesPersistentData;

import java.util.List;

public class CategorieViewModel extends ViewModel {

    private static final String TAG = "CategorieViewModel";
    private MutableLiveData<List<Movie>> mText;

    public CategorieViewModel() {
        mText = new MutableLiveData<>();
        List<Movie> movies = MoviesPersistentData.getInstance().getPopolari();
        if(movies != null)
            Log.d(TAG, "movies initialized");
        mText.setValue(movies);
    }

    public LiveData<List<Movie>> getPopolari() {
        return mText;
    }
}