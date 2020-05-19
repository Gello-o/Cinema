package com.example.cinemhub;

import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.cinemhub.api.Client;
import com.example.cinemhub.api.Service;
import com.example.cinemhub.model.Favorite;
import com.example.cinemhub.model.Movie;
import com.example.cinemhub.model.Trailer;
import com.example.cinemhub.model.TrailerResponse;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.snackbar.Snackbar;
import com.like.LikeButton;
import com.like.OnLikeListener;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityDetail extends AppCompatActivity {
    private static final String TAG = "ActivityDetail";
    private TextView nameOfMovie, plotSynopsis, userRating, releaseDate;
    private ImageView imageView;
    private WebView webView;
    String thumbnail, movieName, synopsis, rating, release, movie_id, originalMovieName;
    public Favorite favorite;
    List<Favorite> line;
    private static final String API_KEY = "740ef79d64b588653371072cdee99a0f";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "creating ActivityDetail");
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = findViewById(R.id.toolbar_activity_detail);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        initCollapsingToolbar();

        imageView = findViewById(R.id.image_activity_detail);
        nameOfMovie = findViewById(R.id.title);
        plotSynopsis = findViewById(R.id.plotsynopsis);
        userRating = findViewById(R.id.usersRating);
        releaseDate = findViewById(R.id.releaseDate);
        webView = findViewById(R.id.videoWebView);

        Log.d(TAG, "Receiving intent");
        Intent intent = getIntent();

        if(intent.hasExtra("original_title")){

            thumbnail = intent.getExtras().getString("poster_path");
            originalMovieName = intent.getExtras().getString("original_title");
            synopsis = intent.getExtras().getString("overview");
            rating = intent.getExtras().getString("vote_average");
            release = intent.getExtras().getString("release_date");
            movie_id = intent.getExtras().getString("id");
            movieName = intent.getExtras().getString("title");

            if(thumbnail == null){
                Log.d(TAG, "immagine nulla");
                Glide.with(this)
                        .load(R.drawable.placeholder)
                        .into(imageView);
            }
            else{
                Glide.with(this)
                        .load(thumbnail)
                        .dontAnimate()
                        .into(imageView);
            }

            nameOfMovie.setText(movieName);
            plotSynopsis.setText(synopsis);
            userRating.setText(rating);
            releaseDate.setText(release);

            //favorite button
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

            LikeButton likeButtonFavorite =
                    (LikeButton) findViewById(R.id.favorite_button);


            if(sharedPreferences.getBoolean("Favorite", false))
                likeButtonFavorite.setLiked(true);;

            likeButtonFavorite.setOnLikeListener(new OnLikeListener() {
                @Override
                public void liked(LikeButton likeButtonFavorite) {
                    Log.d(TAG, "cliccato favorite");

                    SharedPreferences.Editor editor = getSharedPreferences("com.example.cinemhub.ActivityDetail", MODE_PRIVATE).edit();
                    editor.putBoolean("Favorite", true);
                    editor.apply();
                    saveFavorite();
                    likeButtonFavorite.setLiked(true);
                    Snackbar.make(likeButtonFavorite, "Added to Favorite", Snackbar.LENGTH_SHORT).show();
                    mostraDb();
                }

                @Override
                public void unLiked(LikeButton likeButtonFavorite) {
                    favorite = new Favorite();
                    Log.d(TAG, "cliccato unfavorite");
                    SharedPreferences.Editor editor = getSharedPreferences("com.example.cinemhub.ActivityDetail", MODE_PRIVATE).edit();
                    editor.putBoolean("Favorite", false);
                    likeButtonFavorite.setLiked(false);
                    favorite.setMovieId(Integer.parseInt(movie_id));
                    MainActivity.favoriteDB.dbInterface().deleteFavorite(favorite);
                    mostraDb();
                }
            });



            Service apiService = Client.getClient().create(Service.class);
            Call<TrailerResponse> call;
            call = apiService.getMovieTrailer(Integer.parseInt(movie_id), API_KEY);

            call.enqueue(new Callback<TrailerResponse>() {
                @Override
                public void onResponse(@NonNull Call<TrailerResponse> call, @NonNull Response<TrailerResponse> response) {
                    List<Trailer> trailers = response.body().getTrailers();
                    //Gli diamo il primo trailer.
                    String key = "";

                    //Temporaneo
                    if(trailers== null || trailers.size() == 0) {
                        key = "BdJKm16Co6M";
                    }

                    else key = trailers.get(0).getKey();


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
        else
            Toast.makeText(this, "no api data", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "end of the intent");
    }

    public void initCollapsingToolbar(){
        Log.d(TAG, "initializing CollapsingToolbar");
        final CollapsingToolbarLayout collapsingToolbarLayout;
        collapsingToolbarLayout = findViewById(R.id.ctoolbar_activity_detail);
        collapsingToolbarLayout.setTitle(" ");
        AppBarLayout appBarLayout;
        appBarLayout = findViewById(R.id.app_bar_activity_detail);
        appBarLayout.setExpanded(true);

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShown = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if(scrollRange==-1){
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if(scrollRange + verticalOffset == 0){
                    collapsingToolbarLayout.setTitle(getString(R.string.movie_details));
                    isShown = true;
                }else if(isShown){
                    collapsingToolbarLayout.setTitle(" ");
                    isShown = false;
                }
            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }


    private void saveFavorite() {
        favorite = new Favorite();

        Log.d(TAG,"entrato nel save");
        Log.d(TAG,"contenuto movie_id:" + movie_id);

        favorite.setMovieId(Integer.parseInt(movie_id));
        favorite.setTitle(movieName);
        favorite.setPosterPath(thumbnail);
        favorite.setUserRating(rating);
        favorite.setPlotSynopsys(synopsis);

        MainActivity.favoriteDB.dbInterface().addFavorite(favorite);
        Log.d(TAG,"entarto nel Db");
    }

    private void mostraDb() {
        line = MainActivity.favoriteDB.dbInterface().getFavorite();
        for(Favorite favorite : line){
            Log.d(TAG,"Database: " +
                    "ID: " + favorite.getMovieId() +
                    "Title: " + favorite.getTitle());
        }
    }
    //DA CANCELLARE UNA VOLTA FIXATO
    private void cancellaDb() {
        line = MainActivity.favoriteDB.dbInterface().getFavorite();
        MainActivity.favoriteDB.clearAllTables();
        Log.d(TAG, "db size: " + line.size());
        mostraDb();
    }

}
