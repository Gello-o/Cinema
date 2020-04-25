package com.example.cinemhub.api;

import android.util.Log;
import android.widget.Toast;

import com.example.cinemhub.MainActivity;
import com.example.cinemhub.R;
import com.example.cinemhub.model.Movie;
import com.example.cinemhub.model.MoviesResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieRepository{

    public static final String BASE_URL = "https://api.themoviedb.org";
    public int PAGE = 1;
    public static final String API_KEY = "dbe0e6a78f2de2ee943dd6f313182ea0";
    public static String LANGUAGE = "en-US";
    public String CATEGORY = "popular";
    List<Movie> listofMovies;

    public List<Movie> loadJSON (){

        try {
            Service jsonPlaceHolder = Client.getClient().create(Service.class);

            Call<MoviesResponse> call = jsonPlaceHolder.getTMDB(CATEGORY, API_KEY, LANGUAGE, PAGE);

            call.enqueue(new Callback<MoviesResponse>() {
                @Override
                public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                    MoviesResponse results = response.body();
                    listofMovies = results.getResults();
                    //Movie first = listofMovies.get(0);
                }

                @Override
                public void onFailure(Call<MoviesResponse> call, Throwable t) {
                    Log.d("Eccezione fetch", t.getMessage());
                }

            });
        }catch(Exception e){
            Log.d("Eccezione connection", e.getMessage());
        }
        return listofMovies;
    }
}
