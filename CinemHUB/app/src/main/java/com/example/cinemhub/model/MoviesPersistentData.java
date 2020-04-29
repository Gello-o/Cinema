package com.example.cinemhub.model;

import android.util.Log;

import java.util.HashSet;
import java.util.List;

public class MoviesPersistentData {
    private static final String TAG = "MoviesPersistentData";
    private static HashSet<Movie> popolari = new HashSet<>();
    private static HashSet<Movie> al_cinema = new HashSet<>();
    private static HashSet<Movie> prossime_uscite = new HashSet<>();
    private static HashSet<Movie> top_rated = new HashSet<>();

    private static MoviesPersistentData singleton = null;

    private MoviesPersistentData(){}

    public static synchronized MoviesPersistentData getInstance(){
        if(singleton == null)
            singleton = new MoviesPersistentData();
        return singleton;
    }

    public HashSet<Movie> getPopolari() {
        return popolari;
    }

    public HashSet<Movie> getAlCinema() {
        return al_cinema;
    }

    public HashSet<Movie> getProssimeUscite() {
        return prossime_uscite;
    }

    public HashSet<Movie> getTopRated() {
        return top_rated;
    }

}
