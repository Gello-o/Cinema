package com.example.cinemhub;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.bumptech.glide.Glide;
import com.example.cinemhub.model.MoviesRepository;

import com.example.cinemhub.utils.Constants;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;


import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class ActivityDetail extends YouTubeBaseActivity {
    private static final String TAG = "ActivityDetail";

    String thumbnail, movieName, synopsis, rating, release, id, originalMovieName, voteCount, genre;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "creating ActivityDetail");
        setContentView(R.layout.activity_detail);

        UserOperation userOperation = new UserOperation(this, this);
        FavoriteOperation favoriteOperation = new FavoriteOperation(this, this);

        initCollapsingToolbar();

        ImageView imageView = findViewById(R.id.image_activity_detail);
        TextView nameOfMovie = findViewById(R.id.title);
        TextView plotSynopsis = findViewById(R.id.plotsynopsis);
        TextView userRating = findViewById(R.id.usersRating);
        TextView releaseDate = findViewById(R.id.releaseDate);
        YouTubePlayerView playerView = findViewById(R.id.player);

        Intent intent = getIntent();
        Log.d(TAG, "Receiving intent");

        if (intent.hasExtra("original_title")) {

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

            YouTubePlayer.OnInitializedListener initializedListener = new YouTubePlayer.OnInitializedListener() {
                @Override
                public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                    if (keyDatum.getValue() != null)
                        youTubePlayer.cueVideo(keyDatum.getValue());
                    else {
                        youTubePlayer.cueVideo("7310215");
                    }

                    Log.d(TAG, "success");
                }

                @Override
                public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                    Log.d(TAG, "fail");
                }
            };

            playerView.initialize(Constants.YT_API_KEY, initializedListener);

            if (thumbnail == null) {
                imageView.setImageResource(R.drawable.image_not_found_detail);
            } else {
                Glide.with(this)
                        .load(Constants.BASE_IMAGE_URL + thumbnail)
                        .dontAnimate()
                        .into(imageView);
            }

            if (movieName == null && originalMovieName == null) {
                nameOfMovie.setText(R.string.no_title);
            } else {
                if (movieName != null)
                    nameOfMovie.setText(movieName);
                else
                    nameOfMovie.setText(originalMovieName);
            }

            plotSynopsis.setText(synopsis);
            userRating.setText(rating);
            releaseDate.setText(release);

            //chiamat User
            userOperation.eseguiUser(id);
            Log.d(TAG, "superato i userOperation");

            //chiamata favorite
            favoriteOperation.eseguiPreferiti(id,movieName,thumbnail,rating,synopsis,release,genre,originalMovieName,voteCount);

            Log.d(TAG, "end of the intent");
        }
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
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return true;
    }

}