package com.example.cinemhub.model;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class MoviesPersistentData {
    private static final String TAG = "MoviesPersistentData";
    private HashSet<Movie> popolari = new HashSet<>();
    private HashSet<Movie> al_cinema = new HashSet<>();
    private HashSet<Movie> prossime_uscite = new HashSet<>();
    private HashSet<Movie> top_rated = new HashSet<>();
    private HashSet<Trailer> trailers = new HashSet<>();

    private static MoviesPersistentData singleton = null;

    private MoviesPersistentData(){}

    public static synchronized  MoviesPersistentData getInstance(){
        if(singleton == null)
            singleton = new MoviesPersistentData();
        return singleton;
    }

    public MutableLiveData<HashSet<Movie>> getPopolari() {
        MutableLiveData <HashSet<Movie>> popolariLD = new MutableLiveData<>();
        if(popolari.isEmpty()){
            MoviesFactory.getMovies("popular");
        }
        popolariLD.setValue(popolari);
        return popolariLD;
    }

    public MutableLiveData<HashSet<Movie>> getAlCinema() {
        MutableLiveData <HashSet<Movie>> alCinemaLD = new MutableLiveData<>();
        if(al_cinema.isEmpty()){
            MoviesFactory.getMovies("now_playing");
        }
        alCinemaLD.setValue(al_cinema);
        return alCinemaLD;
    }

    public MutableLiveData<HashSet<Movie>> getProssimeUscite() {
        MutableLiveData <HashSet<Movie>> prossimeLD = new MutableLiveData<>();
        if(prossime_uscite.isEmpty()){
            MoviesFactory.getMovies("upcoming");
        }
        prossimeLD.setValue(prossime_uscite);
        return prossimeLD;
    }

    public MutableLiveData<HashSet<Movie>> getTopRated() {
        MutableLiveData <HashSet<Movie>> topRatedLD = new MutableLiveData<>();
        if(top_rated.isEmpty()){
            MoviesFactory.getMovies("top_rated");
        }
        topRatedLD.setValue(top_rated);
        return topRatedLD;
    }

    public  void setPopolari(HashSet<Movie> popolari) {
        this.popolari.addAll(popolari);
    }

    public  void setAlCinema(HashSet<Movie> al_cinema) {
        this.al_cinema.addAll(al_cinema);
    }

    public  void setProssimeUscite(HashSet<Movie> prossime_uscite) {
        this.prossime_uscite.addAll(prossime_uscite);
    }

    public  void setTopRated(HashSet<Movie> top_rated) {
        this.top_rated.addAll(top_rated);
    }

}
