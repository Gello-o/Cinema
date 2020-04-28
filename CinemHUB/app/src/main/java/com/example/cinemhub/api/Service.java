package com.example.cinemhub.api;

import com.example.cinemhub.model.MoviesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import com.example.cinemhub.model.TrailerResponse;

public interface Service {

    @GET("/3/movie/{category}")
        Call<MoviesResponse> getTMDB (
            @Path("category") String category,
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int page
    );

    @GET("/3/movie/{movie_id}/videos")
    Call<TrailerResponse> getMovieTrailer (
            @Path("movie_id") int id,
            @Query("api_key") String apiKey
    );
}
