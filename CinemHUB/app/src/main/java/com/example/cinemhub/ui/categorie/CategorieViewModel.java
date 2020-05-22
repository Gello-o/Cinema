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
    private MutableLiveData<List<Movie>> mText1;
    private MutableLiveData<List<Movie>> mText2;
    MoviesRepository repo;

    public MutableLiveData<List<Movie>> getAzione() {
        if(mText == null){
            mText = new MutableLiveData<>();
            repo = MoviesRepository.getInstance();
            repo.getGenres(28, 1, mText);
        }
        return mText;
    }

    public MutableLiveData<List<Movie>> getAvventura() {
        if(mText1 == null){
            mText1 = new MutableLiveData<>();
            repo = MoviesRepository.getInstance();
            repo.getGenres(12, 1, mText1);
        }
        return mText1;
    }

    public MutableLiveData<List<Movie>> getCrime() {
        if(mText2 == null){
            mText2 = new MutableLiveData<>();
            repo = MoviesRepository.getInstance();
            repo.getGenres(80, 1, mText2);
        }
        return mText2;
    }
}