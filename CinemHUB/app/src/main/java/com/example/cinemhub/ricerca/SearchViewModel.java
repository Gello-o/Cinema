package com.example.cinemhub.ricerca;

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

public class SearchViewModel extends ViewModel {

    private static final String TAG = "SearchViewModel";
    private MutableLiveData<List<Movie>> mText;
    MoviesRepository repo;

    public MutableLiveData<List<Movie>> doSearch(String query) {
        if(mText == null){
            mText = new MutableLiveData<>();
            repo = MoviesRepository.getInstance();
            repo.searchMovie(1, query, mText);
        }
        return mText;
    }
}