package com.example.cinemhub.model;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import com.example.cinemhub.api.Client;
import com.example.cinemhub.api.Service;
import com.example.cinemhub.utils.Constants;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesRepository {

    private static MoviesRepository instance;
    private static final String TAG = "MoviesFactory";


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
        call = apiService.getTMDB(categoria, Constants.API_KEY, Constants.LINGUA, pagina);

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
                    Log.d(TAG, "null ");
                    Log.d(TAG, "Pagina: " + pagina);
                }

                else {
                    List<Movie> a = moviesData.getValue();
                    a.addAll(movies);
                    moviesData.setValue(a); //setValue

                    Log.d(TAG, "not null ");
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
        call = apiService.getGenres(Constants.API_KEY, "popularity.desc", Constants.LINGUA, genere, pagina);

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
                    Log.d(TAG, "null ");
                    Log.d(TAG, "Pagina: " + pagina);
                }

                else {
                    List<Movie> a = moviesData.getValue();
                    a.addAll(movies);
                    moviesData.setValue(a); //setValue

                    Log.d(TAG, "not null ");
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
        call = apiService.search(Constants.API_KEY, Constants.LINGUA, pagina, query, true);

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
                    Log.d(TAG, "null ");
                    Log.d(TAG, "Pagina: " + pagina);
                }

                else {
                    List<Movie> a = moviesData.getValue();
                    a.addAll(movies);
                    moviesData.setValue(a); //setValue

                    Log.d(TAG, "not null ");
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


    public void getTrailer(String id, MutableLiveData<String> keyDatum) {
        Service apiService = Client.getClient().create(Service.class);
        Call<TrailerResponse> call;
        call = apiService.getMovieTrailer(Integer.parseInt(id), Constants.API_KEY);

        call.enqueue(new Callback<TrailerResponse>() {
            @Override
            public void onResponse(@NonNull Call<TrailerResponse> call, @NonNull Response<TrailerResponse> response) {
                List<Trailer> trailers = null;
                if(response.body() != null)
                    trailers = response.body().getTrailers();
                //Gli diamo il primo trailer.
                String key = "";

                //Temporaneo
                if (trailers == null || trailers.size() == 0) {
                    key = "BdJKm16Co6M";
                } else
                    key = trailers.get(0).getKey();

                keyDatum.postValue(key);
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



}