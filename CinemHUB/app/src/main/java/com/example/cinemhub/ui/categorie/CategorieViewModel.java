package com.example.cinemhub.ui.categorie;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cinemhub.model.Movie;
import com.example.cinemhub.model.MoviesRepository;

import java.util.List;

public class CategorieViewModel extends ViewModel {

    private static final String TAG = "CategorieViewModel";
    private MutableLiveData<List<Movie>> mText;
    private MutableLiveData<List<Movie>> mText1;
    private MutableLiveData<List<Movie>> mText2;
    private MutableLiveData<List<Movie>> mText3;
    private MutableLiveData<List<Movie>> mText4;
    MoviesRepository repo;

    public MutableLiveData<List<Movie>> getAzione() {
        if(mText == null){
            mText = new MutableLiveData<>();
            repo = MoviesRepository.getInstance();
            repo.getGenres(28, 1, mText);
        }
        return mText;
    }

    public MutableLiveData<List<Movie>> getFantasy() {
        if(mText1 == null){
            mText1 = new MutableLiveData<>();
            repo = MoviesRepository.getInstance();
            repo.getGenres(14, 1, mText1);
        }
        return mText1;
    }

    public MutableLiveData<List<Movie>> getAnimation() {
        if(mText2 == null){
            mText2 = new MutableLiveData<>();
            repo = MoviesRepository.getInstance();
            repo.getGenres(16, 1, mText2);
        }
        return mText2;
    }

    public MutableLiveData<List<Movie>> getCommedie() {
        if(mText3 == null){
            mText3 = new MutableLiveData<>();
            repo = MoviesRepository.getInstance();
            repo.getGenres(35, 1, mText3);
        }
        return mText3;
    }

    public MutableLiveData<List<Movie>> getRomance() {
        if(mText4 == null){
            mText4 = new MutableLiveData<>();
            repo = MoviesRepository.getInstance();
            repo.getGenres(10749, 1, mText4);
        }
        return mText4;
    }

}