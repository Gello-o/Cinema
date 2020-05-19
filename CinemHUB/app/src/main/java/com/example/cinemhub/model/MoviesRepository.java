package com.example.cinemhub.model;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.cinemhub.api.Client;
import com.example.cinemhub.api.Service;


import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesRepository {

    private static MoviesRepository instance;
    private static final String TAG = "MoviesFactory";
    private static final String API_KEY = "740ef79d64b588653371072cdee99a0f";
    private static String LANGUAGE = "en-US";

    private MoviesRepository(){}

    public synchronized static MoviesRepository getInstance(){
        if(instance == null)
            instance = new MoviesRepository();
        return instance;
    }

    public void getMovies(String categoria, int pagina, MutableLiveData<List<Movie>> moviesData) {
        Service apiService = Client.getClient().create(Service.class);
        Call<MoviesResponse> call;


        Log.d(TAG, "CHIAMATA " + pagina);
        call = apiService.getTMDB(categoria, API_KEY, LANGUAGE, pagina);

        call.enqueue(new Callback<MoviesResponse>() {

            @Override
            public void onResponse(@NonNull Call<MoviesResponse> call, @NonNull Response<MoviesResponse> response) {
                MoviesResponse moviesResponse = response.body();
                List<Movie> movies = moviesResponse.getResults();

                if(moviesResponse == null){
                    Log.d(TAG, "response null");
                    //gestione risposta nulla
                }
                else if(movies == null){
                    Log.d(TAG, "movies null");
                }

                if(moviesData.getValue() == null) {
                    moviesData.setValue(movies);
                    Log.d(TAG, "null zio pera");
                    Log.d(TAG, "Pagina: " + pagina);
                }

                else {
                    List<Movie> a = moviesData.getValue();
                    a.addAll(movies);
                    moviesData.setValue(a); //setValue

                    Log.d(TAG, "not null zio pera");
                }
            }

            @Override
            public void onFailure(@NonNull Call<MoviesResponse> call, @NonNull Throwable t) {
                if (t.getMessage() != null)
                    Log.d("Error", t.getMessage());
                else
                    Log.d("Error", "qualcosa è andato storto");
            }
        });

    }

    public void getGenres(int genere, int pagina, MutableLiveData<List<Movie>> moviesData){
        Service apiService = Client.getClient().create(Service.class);
        Call<MoviesResponse> call;


        Log.d(TAG, "CHIAMATA " + pagina);
        call = apiService.getGenres(API_KEY, "popularity.desc", LANGUAGE, genere, pagina);

        call.enqueue(new Callback<MoviesResponse>() {

            @Override
            public void onResponse(@NonNull Call<MoviesResponse> call, @NonNull Response<MoviesResponse> response) {
                MoviesResponse moviesResponse = response.body();
                List<Movie> movies = moviesResponse.getResults();

                if(moviesResponse == null){
                    Log.d(TAG, "response null");
                    //gestione risposta nulla
                }
                else if(movies == null){
                    Log.d(TAG, "movies null");
                }

                if(moviesData.getValue() == null) {
                    moviesData.setValue(movies);
                    Log.d(TAG, "null zio pera");
                    Log.d(TAG, "Pagina: " + pagina);
                }

                else {
                    List<Movie> a = moviesData.getValue();
                    a.addAll(movies);
                    moviesData.setValue(a); //setValue

                    Log.d(TAG, "not null zio pera");
                }
            }


            @Override
            public void onFailure(@NonNull Call<MoviesResponse> call, @NonNull Throwable t) {
                if (t.getMessage() != null)
                    Log.d("Error", t.getMessage());
                else
                    Log.d("Error", "qualcosa è andato storto");
            }
        });

    }

    public void searchMovie(int pagina, String query, MutableLiveData<List<Movie>> moviesData){
        Service apiService = Client.getClient().create(Service.class);
        Call<MoviesResponse> call;


        Log.d(TAG, "CHIAMATA " + pagina);
        call = apiService.search(API_KEY, LANGUAGE, pagina, query, true);

        call.enqueue(new Callback<MoviesResponse>() {

            @Override
            public void onResponse(@NonNull Call<MoviesResponse> call, @NonNull Response<MoviesResponse> response) {
                MoviesResponse moviesResponse = response.body();
                List<Movie> movies = moviesResponse.getResults();

                if(moviesResponse == null){
                    Log.d(TAG, "response null");
                    //gestione risposta nulla
                }
                else if(movies == null){
                    Log.d(TAG, "movies null");
                }

                if(moviesData.getValue() == null) {
                    moviesData.setValue(movies);
                    Log.d(TAG, "null zio pera");
                    Log.d(TAG, "Pagina: " + pagina);
                }

                else {
                    List<Movie> a = moviesData.getValue();
                    a.addAll(movies);
                    moviesData.setValue(a); //setValue

                    Log.d(TAG, "not null zio pera");
                }
            }


            @Override
            public void onFailure(@NonNull Call<MoviesResponse> call, @NonNull Throwable t) {
                if (t.getMessage() != null)
                    Log.d("Error", t.getMessage());
                else
                    Log.d("Error", "qualcosa è andato storto");
            }
        });
    }
}