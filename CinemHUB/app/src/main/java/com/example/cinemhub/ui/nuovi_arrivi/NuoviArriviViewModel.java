package com.example.cinemhub.ui.nuovi_arrivi;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cinemhub.model.Movie;
import com.example.cinemhub.model.MoviesRepository;


import java.util.List;

public class NuoviArriviViewModel extends ViewModel {
    private static final String TAG = "NuoviArriviViewModel";
    private MutableLiveData<List<Movie>> mText = new MutableLiveData<>();
    MoviesRepository db;

    public MutableLiveData<List<Movie>> getProssimeUscite() {
        if(mText == null) {
            mText = new MutableLiveData<>();
            db = MoviesRepository.getInstance();
            db.getMovies("upcoming", 1, mText);
        }
        return mText;
    }
}