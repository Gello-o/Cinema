package com.example.cinemhub.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cinemhub.adapter.MoviesAdapter;
import com.example.cinemhub.model.Movie;
import com.example.cinemhub.model.MoviesRepo;

import java.util.List;

public class HomeViewModel extends ViewModel {

    //private MutableLiveData<List<Movie>> firstList;
    private MutableLiveData<List<Movie>> popolari;

    public HomeViewModel() {
        popolari = new MutableLiveData<>();
        List<Movie> listaPopolari = MoviesRepo.loadJSON("popular");
        popolari.setValue(listaPopolari);

    }

    public LiveData<List<Movie>> getPopular() {
        return popolari;
    }
}