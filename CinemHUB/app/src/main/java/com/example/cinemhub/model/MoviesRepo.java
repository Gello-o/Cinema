package com.example.cinemhub.model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.cinemhub.api.Client;
import com.example.cinemhub.api.Service;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesRepo {

    private static int PAGE = 1;
    private static final String API_KEY = "740ef79d64b588653371072cdee99a0f";
    private static String LANGUAGE = "en-US";
    private static List<Movie> movies;

    public static List<Movie> loadJSON(String categoria) throws NullPointerException{
        Service apiService = Client.getClient().create(Service.class);
        Call<MoviesResponse> call;
        call = apiService.getTMDB(categoria, API_KEY, LANGUAGE, PAGE);
        if(call == null){
            Log.d("Error", "chiamata == null");
            return null;
        }
        call.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(@NonNull Call<MoviesResponse> call, @NonNull Response<MoviesResponse> response) {
                movies = response.body().getResults();
            }

            @Override
            public void onFailure(@NonNull Call<MoviesResponse> call, @NonNull Throwable t) {
                if (t.getMessage() != null)
                    Log.d("Error", t.getMessage());
                else
                    Log.d("Error", "qualcosa è andato storto");
            }
        });
        return movies;
    }
}

