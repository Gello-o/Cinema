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
        mText = MoviesPersistentData.getInstance().getProssimeUscite();
    }

    public LiveData<HashSet<Movie>> getProssimeUscite() {
        return mText;
    }
}