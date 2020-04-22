package com.example.cinemhub;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceHolderApi {

    @GET("/3/movie/popular?api_key=740ef79d64b588653371072cdee99a0f")
    Call<List<tmdb>> getTMDB();
}
