package com.example.cinemhub.api;

import com.example.cinemhub.model.MoviesResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import com.example.cinemhub.model.TrailerResponse;

/*
L'oggetto Service serve per effettuare la chiamate REST all'API di theMovieDB, da cui
si ottengono i file JSON che l'oggetto Client convertirà in codice Java
*/

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

    @GET("/3/discover/movie")
    Call<MoviesResponse> getGenres(
            @Query("api_key") String apiKey,
            @Query("sort_by") String sortBy,
            @Query("language") String language,
            @Query("with_genres") int genre,
            @Query("page") int page
    );

    @GET("/3/search/movie")
    Call<MoviesResponse> search(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int page,
            @Query("query") String query,
            @Query("include_adult") boolean adult
    );

}
