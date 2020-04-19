package com.example.cinemhub.api;

import com.example.cinemhub.model.MoviesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Service {

    @GET("movie/popular")
    Call<MoviesResponse> getPopularMovies(@Query("dbe0e6a78f2de2ee943dd6f313182ea0") String apiKey);

    @GET("movie/top_rated")
    Call<MoviesResponse> getTopRatedMovies(@Query("dbe0e6a78f2de2ee943dd6f313182ea0") String apiKey);

}
