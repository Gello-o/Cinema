package com.example.cinemhub.ui.categorie;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cinemhub.model.Movie;
import com.example.cinemhub.model.MoviesRepository;
import java.util.List;

public class CategorieViewModel extends ViewModel {

    //private static final String TAG = "CategorieViewModel";
    private MutableLiveData<List<Movie>> azione;
    private MutableLiveData<List<Movie>> avventura;
    private MutableLiveData<List<Movie>> crimine;

    public MutableLiveData<List<Movie>> getAzione() {
        if(azione == null){
            azione = new MutableLiveData<>();
            MoviesRepository.getInstance().getGenres(28, 1, azione);
        }
        return azione;
    }

    public MutableLiveData<List<Movie>> getAvventura() {
        if(avventura == null){
            avventura = new MutableLiveData<>();
            MoviesRepository.getInstance().getGenres(12, 1, avventura);
        }
        return avventura;
    }

    public MutableLiveData<List<Movie>> getCrime() {
        if(crimine == null){
            crimine = new MutableLiveData<>();
            MoviesRepository.getInstance().getGenres(80, 1, crimine);
        }
        return crimine;
    }

}