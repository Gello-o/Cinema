package com.example.cinemhub.api;

import android.graphics.Movie;

import com.example.cinemhub.model.MoviesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Service {

    @GET("/3/movie/{category}")
        Call<MoviesResponse> getTMDB (
            @Path("category") String category,
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int page
    );
}
