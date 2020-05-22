package com.example.cinemhub;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
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
import com.example.cinemhub.model.FavoriteDB;
import com.example.cinemhub.model.Favorite;
import com.example.cinemhub.model.MoviesRepository;
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
    String thumbnail, movieName, synopsis, rating, release, id, originalMovieName, voteCount, genre;
    public Favorite favorite;
    List<Favorite> line;
    private static final String API_KEY = "740ef79d64b588653371072cdee99a0f";
    private final String base_image_Url = "https://image.tmdb.org/t/p/w500";

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
            id = intent.getExtras().getString("id");
            movieName = intent.getExtras().getString("title");
            genre = intent.getExtras().getString("genre_id");
            voteCount = intent.getExtras().getString("vote_count");

            if(thumbnail == null){
                Log.d(TAG, "immagine nulla");
                Glide.with(this)
                        .load(R.drawable.ic_launcher_background)
                        .into(imageView);
            }
            else{
                Glide.with(this)
                        .load(base_image_Url+thumbnail)
                        .dontAnimate()
                        .into(imageView);
            }

            if(movieName == null && originalMovieName == null){
                nameOfMovie.setText("NON HA TITOLO");
            }
            else{
                if(movieName != null)
                    nameOfMovie.setText(movieName);
                else
                    nameOfMovie.setText(originalMovieName);
            }

            plotSynopsis.setText(synopsis);
            userRating.setText(rating);
            releaseDate.setText(release);
            //vedere se mostrare anche vote count

            LikeButton likeButtonFavorite =
                    (LikeButton) findViewById(R.id.favorite_button);

            if(checkFilm())
                likeButtonFavorite.setLiked(true);

            likeButtonFavorite.setOnLikeListener(new OnLikeListener() {
                @Override
                public void liked(LikeButton likeButtonFavorite) {
                    Log.d(TAG, "cliccato favorite");
                    if(FavoriteDB.getInstance().dbInterface().getFavorite().size() > 5){
                        cancellaDb();
                    }
                    saveFavorite();
                    Snackbar.make(likeButtonFavorite, "Added to Favorite",
                            Snackbar.LENGTH_SHORT).show();
                    mostraDb();

                }

                @Override
                public void unLiked(LikeButton likeButtonFavorite) {
                    favorite = new Favorite();
                    Log.d(TAG, "cliccato unfavorite");
                    favorite.setMovieId(Integer.parseInt(id));
                    FavoriteDB.getInstance().dbInterface().deleteFavorite(favorite);
                    mostraDb();
                }
            });

            MoviesRepository.getInstance().getTrailers(id, webView);

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
        Log.d(TAG,"contenuto id:" + id);

        favorite.setMovieId(Integer.parseInt(id));
        favorite.setTitle(movieName);
        favorite.setPosterPath(thumbnail);
        favorite.setUserRating(rating);
        favorite.setPlotSynopsys(synopsis);
        favorite.setReleaseDate(release);
        favorite.setGenreId(genre);
        favorite.setOriginalTitle(originalMovieName);
        favorite.setVoteCount(voteCount);


        FavoriteDB.getInstance().dbInterface().addFavorite(favorite);
        Log.d(TAG,"entarto nel Db");
    }

    private void mostraDb() {
        line = FavoriteDB.getInstance().dbInterface().getFavorite();
        for(Favorite favorite : line){
            Log.d(TAG,"Database: " +
                    "ID: " + favorite.getMovieId() +
                    "Title: " + favorite.getTitle());
        }
    }
    //DA CANCELLARE UNA VOLTA FIXATO
    private void cancellaDb() {
        line = FavoriteDB.getInstance().dbInterface().getFavorite();
        FavoriteDB.getInstance().clearAllTables();
        Log.d(TAG, "db size: " + line.size());
        mostraDb();
    }

    private boolean checkFilm(){
        Log.d(TAG,"entrato nel check");
        line = FavoriteDB.getInstance().dbInterface().getFavorite();
        for(Favorite favorite : line){
            if(favorite.getMovieId() == Integer. parseInt(id))
                return true;
        }
        return false;
    }

}