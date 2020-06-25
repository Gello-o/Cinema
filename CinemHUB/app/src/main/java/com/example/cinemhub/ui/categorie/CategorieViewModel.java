package com.example.cinemhub.ui.categorie;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cinemhub.model.Movie;
import com.example.cinemhub.model.MoviesRepository;
import com.example.cinemhub.utils.Constants;

import java.util.List;

public class CategorieViewModel extends ViewModel {

    //private static final String TAG = "CategorieViewModel";
    private MutableLiveData<List<Movie>> azioneMovies;
    private MutableLiveData<List<Movie>> fantasyMovies;
    private MutableLiveData<List<Movie>> animationMovies;
    private MutableLiveData<List<Movie>> comedyMovies;
    private MutableLiveData<List<Movie>> romanceMovies;

    MutableLiveData<List<Movie>> getAzione() {
        if(azioneMovies == null){
            azioneMovies = new MutableLiveData<>();
            MoviesRepository.getInstance().getGenres(Constants.ACTION, 1, azioneMovies);
        }
        return azioneMovies;
    }

    MutableLiveData<List<Movie>> getFantasy() {
        if(fantasyMovies == null){
            fantasyMovies = new MutableLiveData<>();
            MoviesRepository.getInstance().getGenres(Constants.FANTASY, 1, fantasyMovies);
        }
        return fantasyMovies;
    }

    public MutableLiveData<List<Movie>> getAnimation() {
        if(animationMovies == null){
            animationMovies = new MutableLiveData<>();
            MoviesRepository.getInstance().getGenres(Constants.ANIMATION, 1, animationMovies);
        }
        return animationMovies;
    }

    MutableLiveData<List<Movie>> getCommedie() {
        if(comedyMovies == null){
            comedyMovies = new MutableLiveData<>();
            MoviesRepository.getInstance().getGenres(Constants.COMEDY, 1, comedyMovies);
        }
        return comedyMovies;
    }

    MutableLiveData<List<Movie>> getRomance() {
        if(romanceMovies == null){
            romanceMovies = new MutableLiveData<>();
            MoviesRepository.getInstance().getGenres(Constants.ROMANCE, 1, romanceMovies);
        }
        return romanceMovies;
    }

}