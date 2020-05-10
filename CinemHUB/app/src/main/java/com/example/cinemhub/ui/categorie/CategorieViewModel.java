package com.example.cinemhub.ui.categorie;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cinemhub.model.Movie;
import com.example.cinemhub.model.MoviesPersistentData;
import com.example.cinemhub.model.Trailer;

import java.util.HashSet;
import java.util.List;

public class CategorieViewModel extends ViewModel {

    private static final String TAG = "CategorieViewModel";
    private MutableLiveData<HashSet<Trailer>> mText;
    MoviesPersistentData db;

    public CategorieViewModel() {
        mText = new MutableLiveData<>();
    }

    public LiveData<HashSet<Trailer>> getPopolari() {
        return mText;
    }
}