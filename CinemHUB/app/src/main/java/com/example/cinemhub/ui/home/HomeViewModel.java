package com.example.cinemhub.ui.home;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cinemhub.model.Movie;
import com.example.cinemhub.model.MoviesRepository;

import java.util.List;

public class HomeViewModel extends ViewModel {

    private static final String TAG = "HomeViewModel";
    private MutableLiveData<List<Movie>> popolari;
    private MutableLiveData<List<Movie>> alCinema;
    private MutableLiveData<List<Movie>> topRated;
    private MutableLiveData<List<Movie>> prossimeUscite;
    MoviesRepository db;

    public MutableLiveData<List<Movie>> getPopolari() {
        db = MoviesRepository.getInstance();
        if(popolari == null) {
            popolari = new MutableLiveData<>();
            db.getMovies("popular", 1, popolari);
        }

        if(popolari.getValue() == null)
            Log.d(TAG, "popolari NULL");
        else if(popolari.getValue().isEmpty())
            Log.d(TAG, "popolari vuoto");

        return popolari;

    }

    public MutableLiveData<List<Movie>> getAlCinema() {
        db = MoviesRepository.getInstance();
        if(alCinema == null) {
            alCinema = new MutableLiveData<>();
            db.getMovies("now_playing", 1, alCinema);
        }

        if(alCinema.getValue() == null)
            Log.d(TAG, "cinema NULL");
        else if(alCinema.getValue().isEmpty())
            Log.d(TAG, "cinema vuoto");

        return alCinema;
    }

    public MutableLiveData<List<Movie>> getProssimeUscite() {
        db = MoviesRepository.getInstance();
        if(prossimeUscite == null) {
            prossimeUscite = new MutableLiveData<>();
            db.getMovies("upcoming", 1, prossimeUscite);
        }

        if(prossimeUscite.getValue() == null)
            Log.d(TAG, "prox NULL");
        else if(prossimeUscite.getValue().isEmpty())
            Log.d(TAG, "prox vuoto");


        return prossimeUscite;
    }

    public MutableLiveData<List<Movie>> getTopRated() {
        db = MoviesRepository.getInstance();
        if(topRated == null) {
            topRated = new MutableLiveData<>();
            db.getMovies("top_rated", 1, topRated);
        }

        if(topRated.getValue() == null)
            Log.d(TAG, "top NULL");
        else if(topRated.getValue().isEmpty())
            Log.d(TAG, "top vuoto");

        return topRated;
    }

}