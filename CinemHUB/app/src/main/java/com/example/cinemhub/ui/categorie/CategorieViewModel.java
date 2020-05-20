package com.example.cinemhub.ui.categorie;

import android.text.method.MovementMethod;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cinemhub.model.Movie;
import com.example.cinemhub.model.MoviesRepository;
import com.example.cinemhub.model.Trailer;

import java.util.HashSet;
import java.util.List;

public class CategorieViewModel extends ViewModel {

    private static final String TAG = "CategorieViewModel";
    private MutableLiveData<List<Movie>> mText;
    MoviesRepository repo;

    public MutableLiveData<List<Movie>> getPopolari() {
        if(mText == null){
            mText = new MutableLiveData<>();
            repo = MoviesRepository.getInstance();
            repo.getGenres(28, 1, mText);
        }
        return mText;
    }
}