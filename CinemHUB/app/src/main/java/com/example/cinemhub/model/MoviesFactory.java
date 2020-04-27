package com.example.cinemhub.model;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.cinemhub.api.Client;
import com.example.cinemhub.api.Service;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesFactory {

    private static int PAGE = 1;
    private static final String API_KEY = "740ef79d64b588653371072cdee99a0f";
    private static String LANGUAGE = "en-US";

    public static void getMovies(String categoria) {

        Service apiService = Client.getClient().create(Service.class);
        Call<MoviesResponse> call;
        call = apiService.getTMDB(categoria, API_KEY, LANGUAGE, PAGE);

        call.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(@NonNull Call<MoviesResponse> call, @NonNull Response<MoviesResponse> response) {
                List<Movie> movies = response.body().getResults();
                switch (categoria){
                    case "popular":
                        MoviesPersistentData.getInstance().setPopolari(movies);
                    case "upcoming":
                        MoviesPersistentData.getInstance().setProssimeUscite(movies);
                    case "top_rated":
                        MoviesPersistentData.getInstance().setTopRated(movies);
                    default:
                        MoviesPersistentData.getInstance().setAlCinema(movies);
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

