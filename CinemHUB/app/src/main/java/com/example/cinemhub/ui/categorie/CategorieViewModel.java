package com.example.cinemhub.ui.categorie;

import android.graphics.Movie;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.model.MovieDb;

public class CategorieViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CategorieViewModel() {

        try{
            mText = new MutableLiveData<>();
            mText.setValue("Questa è la pagina delle categorie");
        }catch(Exception exception){
            System.out.println("sono andato in eccezione");
        }

    }

    public LiveData<String> getText() {
        return mText;
    }
}