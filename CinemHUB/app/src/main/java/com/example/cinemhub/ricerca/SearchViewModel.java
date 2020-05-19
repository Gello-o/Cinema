package com.example.cinemhub.ricerca;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cinemhub.model.Movie;
import com.example.cinemhub.model.MoviesRepository;

import java.util.List;

/*
public class SearchViewModel extends ViewModel {
    private static final String TAG = "SearchViewModel";
    private MutableLiveData<List<Movie>> tutti;
    MoviesRepository db;

    public MutableLiveData<List<Movie>> getSearch() {
        if(tutti == null) {
            db = MoviesRepository.getInstance();
            tutti = new MutableLiveData<>();
            for(int i = 1; i < 3; i++){
                db.getMovies("upcoming", i, tutti);
            }
        }
        return tutti;
    }
}
*/
