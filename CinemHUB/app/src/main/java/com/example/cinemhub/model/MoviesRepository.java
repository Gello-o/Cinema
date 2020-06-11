package com.example.cinemhub.model;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import com.example.cinemhub.api.Client;
import com.example.cinemhub.api.Service;
import com.example.cinemhub.utils.Constants;

import java.io.IOException;
import java.util.ArrayList;
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

    public void getMoviesLL(String categoria, int pagina, MutableLiveData<Resource<List<Movie>>> moviesData) {
        Service apiService = Client.getClient().create(Service.class);
        Call<MoviesResponse> call;


        Log.d(TAG, "CHIAMATA " + pagina);
        call = apiService.getTMDB(categoria, Constants.API_KEY, Constants.LINGUA, pagina);

        call.enqueue(new Callback<MoviesResponse>() {

            @Override
            public void onResponse(@NonNull Call<MoviesResponse> call, @NonNull Response<MoviesResponse> response) {
                if (response.isSuccessful() && response.body() != null) {

                    Resource<List<Movie>> resource = new Resource<>();

                    if (moviesData.getValue() != null && moviesData.getValue().getData() != null) {
                        List<Movie> currentArticleList = moviesData.getValue().getData();

                        currentArticleList.remove(currentArticleList.size() - 1);
                        currentArticleList.addAll(response.body().getResults());
                        resource.setData(currentArticleList);
                    } else {
                        resource.setData(response.body().getResults());
                    }

                    resource.setTotalResults(response.body().getTotalResults());
                    resource.setStatusCode(response.code());
                    resource.setStatusMessage(response.message());
                    resource.setLoading(false);
                    Log.d(TAG, "LOADING FALSE");
                    moviesData.postValue(resource);
                } else if (response.errorBody() != null) {
                    Resource<List<Movie>> resource = new Resource<>();
                    resource.setStatusCode(response.code());
                    try {
                        resource.setStatusMessage(response.errorBody().string() + "- " + response.message());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    moviesData.postValue(resource);
                }
            }

            @Override
            public void onFailure(@NonNull Call<MoviesResponse> call, @NonNull Throwable t) {
                Resource<List<Movie>> resource = new Resource<>();
                resource.setStatusMessage(t.getMessage());
                moviesData.postValue(resource);
            }
        });
    }


    public void getMovies(String categoria, int pagina, MutableLiveData<List<Movie>> moviesData) {
        Service apiService = Client.getClient().create(Service.class);
        Call<MoviesResponse> call;

        Log.d(TAG, "CHIAMATA " + pagina);
        call = apiService.getTMDB(categoria, Constants.API_KEY, Constants.LINGUA, pagina);

        call.enqueue(new Callback<MoviesResponse>() {

            @Override
            public void onResponse(@NonNull Call<MoviesResponse> call, @NonNull Response<MoviesResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    List<Movie> movies = response.body().getResults();
                    if(movies != null)
                        moviesData.setValue(movies);
                    else
                        moviesData.setValue(new ArrayList<>());
                }
            }

            @Override
            public void onFailure(@NonNull Call<MoviesResponse> call, @NonNull Throwable t) {
                moviesData.setValue(new ArrayList<>());
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
                if(response.isSuccessful() && response.body() != null){
                    List<Movie> movies = response.body().getResults();
                    if(movies != null)
                        moviesData.setValue(movies);
                    else
                        moviesData.setValue(new ArrayList<>());
                }
            }


            @Override
            public void onFailure(@NonNull Call<MoviesResponse> call, @NonNull Throwable t) {
                moviesData.setValue(new ArrayList<>());
            }
        });

    }

    public void searchMovie(String query, int pagina, MutableLiveData<Resource<List<Movie>>> moviesData) {
        Service apiService = Client.getClient().create(Service.class);
        Call<MoviesResponse> call;


        Log.d(TAG, "CHIAMATA " + pagina);
        call = apiService.search(Constants.API_KEY, Constants.LINGUA, pagina, query, true);

        call.enqueue(new Callback<MoviesResponse>() {

            @Override
            public void onResponse(@NonNull Call<MoviesResponse> call, @NonNull Response<MoviesResponse> response) {
                if (response.isSuccessful() && response.body() != null) {

                    Resource<List<Movie>> resource = new Resource<>();
                    Log.d(TAG, "RESPONSE SUCCESFUL");
                    if (moviesData.getValue() != null && moviesData.getValue().getData() != null) {

                        Log.d(TAG, "dato già popolato");
                        List<Movie> currentArticleList = moviesData.getValue().getData();

                        currentArticleList.remove(currentArticleList.size() - 1);
                        currentArticleList.addAll(response.body().getResults());
                        resource.setData(currentArticleList);
                    } else {
                        Log.d(TAG, "dato non ancora popolato");
                        resource.setData(response.body().getResults());
                    }

                    resource.setTotalResults(response.body().getTotalResults());
                    resource.setStatusCode(response.code());
                    resource.setStatusMessage(response.message());
                    resource.setLoading(false);
                    Log.d(TAG, "LOADING FALSE");
                    moviesData.postValue(resource);
                } else if (response.errorBody() != null) {
                    Log.d(TAG, "RESPONSE NOT SUCCESFUL");
                    Resource<List<Movie>> resource = new Resource<>();
                    resource.setStatusCode(response.code());
                    try {
                        resource.setStatusMessage(response.errorBody().string() + "- " + response.message());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    moviesData.postValue(resource);
                }

            }

            @Override
            public void onFailure(@NonNull Call<MoviesResponse> call, @NonNull Throwable t) {
                Resource<List<Movie>> resource = new Resource<>();
                resource.setStatusMessage(t.getMessage());
                moviesData.postValue(resource);
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

                if(response.isSuccessful() && response.body() != null){
                    List<Trailer> trailers = response.body().getTrailers();
                    if(trailers != null && !trailers.isEmpty() && trailers.get(0)!=null && trailers.get(0).getKey() != null) {
                        keyDatum.setValue(trailers.get(0).getKey());
                    }
                    else
                        keyDatum.setValue("668nUCeBHyY");
                }

            }

            @Override
            public void onFailure(@NonNull Call<TrailerResponse> call, @NonNull Throwable t) {
                keyDatum.setValue("668nUCeBHyY");
            }
        });

    }


    public void getGenresLL(int genere, int pagina, MutableLiveData<Resource<List<Movie>>> moviesData) {
        Service apiService = Client.getClient().create(Service.class);
        Call<MoviesResponse> call;


        Log.d(TAG, "CHIAMATA " + pagina);
        call = apiService.getGenres(Constants.API_KEY, "popularity.desc", Constants.LINGUA, genere, pagina);

        call.enqueue(new Callback<MoviesResponse>() {

            @Override
            public void onResponse(@NonNull Call<MoviesResponse> call, @NonNull Response<MoviesResponse> response) {
                if (response.isSuccessful() && response.body() != null) {

                    Resource<List<Movie>> resource = new Resource<>();

                    if (moviesData.getValue() != null && moviesData.getValue().getData() != null) {
                        List<Movie> currentArticleList = moviesData.getValue().getData();

                        currentArticleList.remove(currentArticleList.size() - 1);
                        currentArticleList.addAll(response.body().getResults());
                        resource.setData(currentArticleList);
                    } else {
                        resource.setData(response.body().getResults());
                    }

                    resource.setTotalResults(response.body().getTotalResults());
                    resource.setStatusCode(response.code());
                    resource.setStatusMessage(response.message());
                    resource.setLoading(false);
                    Log.d(TAG, "LOADING FALSE");
                    moviesData.postValue(resource);
                } else if (response.errorBody() != null) {
                    Resource<List<Movie>> resource = new Resource<>();
                    resource.setStatusCode(response.code());
                    try {
                        resource.setStatusMessage(response.errorBody().string() + "- " + response.message());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    moviesData.postValue(resource);
                }
            }

            @Override
            public void onFailure(@NonNull Call<MoviesResponse> call, @NonNull Throwable t) {
                Resource<List<Movie>> resource = new Resource<>();
                resource.setStatusMessage(t.getMessage());
                moviesData.postValue(resource);
            }
        });
    }

}