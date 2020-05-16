package com.example.cinemhub.model;

import android.text.method.MovementMethod;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PagedList;

import com.example.cinemhub.api.Client;
import com.example.cinemhub.api.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


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

    public void getMovies(String categoria, int pagina, MutableLiveData<PagedList<Movie>> moviesData) {
        Service apiService = Client.getClient().create(Service.class);
        Call<MoviesResponse> call;


            Log.d(TAG, "CHIAMATA " + pagina);
            call = apiService.getTMDB(categoria, API_KEY, LANGUAGE, pagina);

            call.enqueue(new Callback<MoviesResponse>() {

                @Override
                public void onResponse(@NonNull Call<MoviesResponse> call, @NonNull Response<MoviesResponse> response) {
                    MoviesResponse moviesResponse = response.body();
                    PagedList<Movie> movies = (PagedList<Movie>) moviesResponse.getResults();

                    if(moviesResponse == null){
                        Log.d(TAG, "response null");
                        //gestione risposta nulla
                    }
                    else if(movies == null){
                        Log.d(TAG, "movies null");
                        //gestione movies null
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