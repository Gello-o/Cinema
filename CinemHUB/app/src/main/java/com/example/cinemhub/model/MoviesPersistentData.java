package com.example.cinemhub.model;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class MoviesPersistentData {
    private static final String TAG = "MoviesPersistentData";
    private static HashSet<Movie> popolari = new HashSet<>();
    private static HashSet<Movie> al_cinema = new HashSet<>();
    private static HashSet<Movie> prossime_uscite = new HashSet<>();
    private static HashSet<Movie> top_rated = new HashSet<>();
    private static HashSet<Trailer> trailers = new HashSet<>();
    private static Set<Trailer> trailers2;

    private static MoviesPersistentData singleton = null;

    private MoviesPersistentData(){}

    public synchronized static MoviesPersistentData getInstance(){
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

    public static void setPopolari(HashSet<Movie> popolari) {
        MoviesPersistentData.popolari.addAll(popolari);
    }

    public static void setAlCinema(HashSet<Movie> al_cinema) {
        MoviesPersistentData.al_cinema.addAll(al_cinema);
    }

    public static void setProssimeUscite(HashSet<Movie> prossime_uscite) {
        MoviesPersistentData.prossime_uscite.addAll(prossime_uscite);
    }

    public static void setTopRated(HashSet<Movie> top_rated) {
        MoviesPersistentData.top_rated.addAll(top_rated);
    }

    public MutableLiveData<HashSet<Trailer>> getTrailer(int id) {
        MutableLiveData <HashSet<Trailer>> trailerLD = new MutableLiveData<>();
        if(trailers.isEmpty()) {
            Log.d(TAG, "GET TRAILERS");
            MoviesFactory.getTrailers(id);
        }
        if(trailers.isEmpty())
            Log.d(TAG, "trailersEmpty, tiro madonne");
        trailerLD.setValue(trailers);
        return trailerLD;
    }

    public static void setTrailer(HashSet<Trailer> trailers) {
        MoviesPersistentData.trailers.addAll(trailers);
    }






}
