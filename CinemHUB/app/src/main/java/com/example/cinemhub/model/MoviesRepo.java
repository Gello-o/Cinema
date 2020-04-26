package com.example.cinemhub.model;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.cinemhub.MainActivity;
import com.example.cinemhub.R;
import com.example.cinemhub.adapter.MoviesAdapter;
import com.example.cinemhub.api.Client;
import com.example.cinemhub.api.Service;
import com.example.cinemhub.ui.categorie.CategorieViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesRepo {

    private static String BASE_URL = "https://api.themoviedb.org";
    public static int PAGE = 1;
    public static String API_KEY = "740ef79d64b588653371072cdee99a0f";
    public static String LANGUAGE = "en-US";
    public static String CATEGORY = "popular";
    public static List<Movie> movies;

    public static List<Movie> loadJSON() {
        Service apiService = Client.getClient().create(Service.class);
        Call<MoviesResponse> call;
        call = apiService.getTMDB(CATEGORY, API_KEY, LANGUAGE, PAGE);
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

