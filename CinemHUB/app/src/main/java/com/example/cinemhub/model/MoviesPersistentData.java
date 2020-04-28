package com.example.cinemhub.model;

import android.util.Log;

import java.util.List;

public class MoviesPersistentData {
    private static final String TAG = "MoviesPersistentData";
    private static List<Movie> popolari;
    private static List<Movie> al_cinema;
    private static List<Movie> prossime_uscite;
    private static List<Movie> top_rated;

    private static MoviesPersistentData singleton = null;

    private MoviesPersistentData(){}

    public static synchronized MoviesPersistentData getInstance(){
        if(singleton == null)
            singleton = new MoviesPersistentData();
        return singleton;
    }

    public List<Movie> getPopolari() {
        if(popolari == null) {
            Log.d(TAG, "TIRO MADONNE1");
            MoviesFactory.getMovies("popular");
            return popolari;
        }
        else
            return popolari;
    }

    public void setPopolari(List<Movie> popolari) {
        MoviesPersistentData.popolari = popolari;
    }

    public List<Movie> getAlCinema() {
        if(al_cinema == null) {
            Log.d(TAG, "TIRO MADONNE2");
            MoviesFactory.getMovies("now_playing");
            return al_cinema;
        }
        else
            return al_cinema;
    }

    public void setAlCinema(List<Movie> al_cinema) {
        MoviesPersistentData.al_cinema = al_cinema;
    }

    public List<Movie> getProssimeUscite() {
        if(prossime_uscite == null) {
            Log.d(TAG, "TIRO MADONNE3");
            MoviesFactory.getMovies("upcoming");
            return prossime_uscite;
        }
        else
            return prossime_uscite;
    }

    public void setProssimeUscite(List<Movie> prossime_uscite) {
        MoviesPersistentData.prossime_uscite = prossime_uscite;
    }

    public List<Movie> getTopRated() {
        if(top_rated == null) {
            Log.d(TAG, "TIRO MADONNE4");
            MoviesFactory.getMovies("top_rated");
            return top_rated;
        }
        else
            return top_rated;
    }

    public void setTopRated(List<Movie> top_rated) {
        MoviesPersistentData.top_rated = top_rated;
    }

}
