package com.example.cinemhub;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import com.bumptech.glide.Glide;
import com.example.cinemhub.model.Favorite;
import com.example.cinemhub.model.FavoriteDB;
import com.example.cinemhub.model.MoviesRepository;
import com.example.cinemhub.utils.Constants;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.snackbar.Snackbar;
import com.like.LikeButton;
import com.like.OnLikeListener;
import java.util.List;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class ActivityDetail extends YouTubeBaseActivity {
    private static final String TAG = "ActivityDetail";
    private TextView nameOfMovie, plotSynopsis, userRating, releaseDate;
    private ImageView imageView;
    private WebView webView;
    private VideoView videoView;
    String thumbnail, movieName, synopsis, rating, release, id, originalMovieName, voteCount, genre;
    public Favorite favorite;
    List<Favorite> line;
    private YouTubePlayerView playerView;
    private YouTubePlayer.OnInitializedListener initializedListener;
    private Context mContext;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "creating ActivityDetail");
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = findViewById(R.id.toolbar_activity_detail);
        //setSupportActionBar(toolbar);
        //Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        initCollapsingToolbar();
        mContext = getBaseContext();

        imageView = findViewById(R.id.image_activity_detail);
        nameOfMovie = findViewById(R.id.title);
        plotSynopsis = findViewById(R.id.plotsynopsis);
        userRating = findViewById(R.id.usersRating);
        releaseDate = findViewById(R.id.releaseDate);
        playerView = findViewById(R.id.player);


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

            MutableLiveData<String> keyDatum = new MutableLiveData<>();
            MoviesRepository.getInstance().getTrailer(id, keyDatum);

            initializedListener = new YouTubePlayer.OnInitializedListener() {
                @Override
                public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                    youTubePlayer.cueVideo(keyDatum.getValue());
                    Log.d(TAG,"success");
                }

                @Override
                public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                    Log.d(TAG,"fail");
                }
            };

            playerView.initialize(Constants.YT_API_KEY, initializedListener);



            if(thumbnail == null){
                Log.d(TAG, "immagine nulla");
                Glide.with(this)
                        .load(R.drawable.film_not_found)
                        .into(imageView);
            }
            else{
                Glide.with(this)
                        .load(Constants.BASE_IMAGE_URL+thumbnail)
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

            //MoviesRepository.getInstance().getTrailers(id, webView);

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