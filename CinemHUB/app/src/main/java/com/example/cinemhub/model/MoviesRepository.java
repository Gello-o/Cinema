package com.example.cinemhub.model;

import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.cinemhub.ActivityDetail;
import com.example.cinemhub.api.Client;
import com.example.cinemhub.api.Service;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


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

    public void getMovies(String categoria, int pagina, MutableLiveData<List<Movie>> moviesData) {
        Service apiService = Client.getClient().create(Service.class);
        Call<MoviesResponse> call;


        Log.d(TAG, "CHIAMATA " + pagina);
        call = apiService.getTMDB(categoria, API_KEY, LANGUAGE, pagina);

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
                    Log.d(TAG, "null zio pera");
                    Log.d(TAG, "Pagina: " + pagina);
                }

                else {
                    List<Movie> a = moviesData.getValue();
                    a.addAll(movies);
                    moviesData.setValue(a); //setValue

                    Log.d(TAG, "not null zio pera");
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
        call = apiService.getGenres(API_KEY, "popularity.desc", LANGUAGE, genere, pagina);

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
                    Log.d(TAG, "null zio pera");
                    Log.d(TAG, "Pagina: " + pagina);
                }

                else {
                    List<Movie> a = moviesData.getValue();
                    a.addAll(movies);
                    moviesData.setValue(a); //setValue

                    Log.d(TAG, "not null zio pera");
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
        call = apiService.search(API_KEY, LANGUAGE, pagina, query, true);

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
                    Log.d(TAG, "null zio pera");
                    Log.d(TAG, "Pagina: " + pagina);
                }

                else {
                    List<Movie> a = moviesData.getValue();
                    a.addAll(movies);
                    moviesData.setValue(a); //setValue

                    Log.d(TAG, "not null zio pera");
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

    public void getTrailers(String id, WebView webView) {
        Service apiService = Client.getClient().create(Service.class);
        Call<TrailerResponse> call;
        call = apiService.getMovieTrailer(Integer.parseInt(id), API_KEY);

        call.enqueue(new Callback<TrailerResponse>() {
            @Override
            public void onResponse(@NonNull Call<TrailerResponse> call, @NonNull Response<TrailerResponse> response) {
                List<Trailer> trailers = response.body().getTrailers();
                //Gli diamo il primo trailer.
                String key = "";

                //Temporaneo
                if (trailers == null || trailers.size() == 0) {
                    key = "BdJKm16Co6M";
                } else key = trailers.get(0).getKey();


                //La stringa che si andrà a formare da mettere nella webview di content detail
                String frameVideo = "<html><body><iframe src=\"https://www.youtube.com/embed/";
                String link2 = key + "\" frameborder=\"0\" allowfullscreen></iframe></body></html>";
                String link3 = frameVideo + link2;

                webView.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        return false;
                    }
                });
                //Mettiamo tutto nella webview
                WebSettings webSettings = webView.getSettings();
                webSettings.setJavaScriptEnabled(true);
                webView.loadData(link3, "text/html", "utf-8");
                //webView.loadUrl("https://www.youtube.com/embed/" + key);


                //Molte cose son da cancellare, ma le lascio così confonde di più le idee.
                HashSet<Trailer> trailersSet = new HashSet<>();
                trailersSet.addAll(trailers);

                if (trailersSet.isEmpty())
                    Log.d(TAG, "trailerSet NULL");
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





    public void getTrailer(String id, MutableLiveData<String> keyDatum) {
        Service apiService = Client.getClient().create(Service.class);
        Call<TrailerResponse> call;
        call = apiService.getMovieTrailer(Integer.parseInt(id), API_KEY);

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