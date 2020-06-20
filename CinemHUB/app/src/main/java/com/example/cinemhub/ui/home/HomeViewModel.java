package com.example.cinemhub.ui.home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.cinemhub.model.Movie;
import com.example.cinemhub.model.MoviesRepository;
import java.util.List;

public class HomeViewModel extends ViewModel {

    //private static final String TAG = "HomeViewModel";
    private MutableLiveData<List<Movie>> popolari;
    private MutableLiveData<List<Movie>> alCinema;
    private MutableLiveData<List<Movie>> topRated;
    private MutableLiveData<List<Movie>> prossimeUscite;

    public MutableLiveData<List<Movie>> getPopolari() {

        if(popolari == null) {
            popolari = new MutableLiveData<>();
            MoviesRepository.getInstance().getMovies("popular", 1, popolari);
        }

        return popolari;
    }

    public MutableLiveData<List<Movie>> getAlCinema() {

        if(alCinema == null) {
            alCinema = new MutableLiveData<>();
            MoviesRepository.getInstance().getMovies("now_playing", 1, alCinema);
        }

        return alCinema;
    }

    public MutableLiveData<List<Movie>> getProssimeUscite() {
        if(prossimeUscite == null) {
            prossimeUscite = new MutableLiveData<>();
            MoviesRepository.getInstance().getMovies("upcoming", 1, prossimeUscite);
        }

        return prossimeUscite;
    }

    public MutableLiveData<List<Movie>> getTopRated() {
        if(topRated == null) {
            topRated = new MutableLiveData<>();
            MoviesRepository.getInstance().getMovies("top_rated", 1, topRated);
        }

        return topRated;
    }

}