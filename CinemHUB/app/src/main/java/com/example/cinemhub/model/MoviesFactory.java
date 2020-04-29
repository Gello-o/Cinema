package com.example.cinemhub.model;

import android.util.Log;

import androidx.annotation.NonNull;
import com.example.cinemhub.api.Client;
import com.example.cinemhub.api.Service;


import java.util.ArrayList;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Handler;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesFactory {


    private static final String TAG = "MoviesFactory";
    private static final String API_KEY = "740ef79d64b588653371072cdee99a0f";
    private static String LANGUAGE = "en-US";
    private static final int PAGINE_TOT = 4;

    public synchronized static void getMovies(String categoria) {
        Service apiService = Client.getClient().create(Service.class);
        int index = 1;

        while(index <= PAGINE_TOT ) {
            Log.d(TAG, "pagina " + index);
            Call<MoviesResponse> call;
            call = apiService.getTMDB(categoria, API_KEY, LANGUAGE, index);
            call.enqueue(new Callback<MoviesResponse>() {
                @Override
                public void onResponse(@NonNull Call<MoviesResponse> call, @NonNull Response<MoviesResponse> response) {

                    List<Movie> movies = response.body().getResults();
                    HashSet<Movie> moviesSet = new HashSet<>();
                    for(Movie m : movies)
                        moviesSet.add(m);

                    fillDB(categoria, moviesSet);
                }

                @Override
                public void onFailure(@NonNull Call<MoviesResponse> call, @NonNull Throwable t) {
                    if (t.getMessage() != null)
                        Log.d("Error", t.getMessage());
                    else
                        Log.d("Error", "qualcosa è andato storto");
                }
            });
            index++;
        }
    }

    public static void getTrailers(Movie movie){
        int movieID = movie.getId();
        Service apiService = Client.getClient().create(Service.class);
        Call<TrailerResponse> call;
        call = apiService.getMovieTrailer(movieID, API_KEY);

        call.enqueue(new Callback<TrailerResponse>() {
            @Override
            public void onResponse(@NonNull Call<TrailerResponse> call, @NonNull Response<TrailerResponse> response){
                List <Trailer> trailerResponse = response.body().getTrailers();

            }

            @Override
            public void onFailure(@NonNull Call<TrailerResponse> call, @NonNull Throwable t) {
                if (t.getMessage() != null)
                    Log.d("Error", t.getMessage());
                else
                    Log.d("Error", "qualcosa è andato storto");
            }
        });
    }

    public static void fillDB(String categoria, HashSet<Movie> movieSet) {

        switch (categoria) {
            case "popular":
                    MoviesPersistentData.getInstance().getPopolari().addAll(movieSet);
            case "upcoming":
                    MoviesPersistentData.getInstance().getProssimeUscite().addAll(movieSet);
            case "top_rated":
                    MoviesPersistentData.getInstance().getTopRated().addAll(movieSet);
            default:
                    MoviesPersistentData.getInstance().getAlCinema().addAll(movieSet);
        }

    }
}

