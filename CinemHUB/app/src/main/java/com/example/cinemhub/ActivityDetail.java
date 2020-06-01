package com.example.cinemhub;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.bumptech.glide.Glide;
import com.example.cinemhub.model.MoviesRepository;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;


import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class ActivityDetail extends YouTubeBaseActivity {
    private static final String TAG = "ActivityDetail";

    private static final String API_KEY = "740ef79d64b588653371072cdee99a0f";
    private final String YT_API_KEY = "AIzaSyC95r_3BNU_BxvSUE7ZyXKrar3dc127rVk";
    private final String base_image_Url = "https://image.tmdb.org/t/p/w500";

    private TextView nameOfMovie, plotSynopsis, userRating, releaseDate;
    private ImageView imageView;
    private YouTubePlayerView playerView;
    private YouTubePlayer.OnInitializedListener initializedListener;
    private Context mContext;
    private UserOperation userOperation ;
    private FavoriteOperation favoriteOperation;

    String thumbnail, movieName, synopsis, rating, release, id, originalMovieName, voteCount, genre;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "creating ActivityDetail");
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = findViewById(R.id.toolbar_activity_detail);
        userOperation = new UserOperation(this,this);
        favoriteOperation = new FavoriteOperation(this,this);

        initCollapsingToolbar();
        mContext = getBaseContext();

        imageView = findViewById(R.id.image_activity_detail);
        nameOfMovie = findViewById(R.id.title);
        plotSynopsis = findViewById(R.id.plotsynopsis);
        userRating = findViewById(R.id.usersRating);
        releaseDate = findViewById(R.id.releaseDate);
        playerView = findViewById(R.id.player);

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

            initializedListener = new YouTubePlayer.OnInitializedListener() {
                @Override
                public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                    youTubePlayer.loadVideo(keyDatum.getValue());
                    Log.d(TAG, "success");
                }

                @Override
                public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                    Log.d(TAG, "fail");
                }
            };

            playerView.initialize(YT_API_KEY, initializedListener);

            if (thumbnail == null) {
                imageView.setImageResource(R.drawable.image_not_found);
            } else {
                Glide.with(this)
                        .load(base_image_Url + thumbnail)
                        .dontAnimate()
                        .into(imageView);
            }

            if (movieName == null && originalMovieName == null) {
                nameOfMovie.setText("NON HA TITOLO");
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
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }


    public class myChrome extends WebChromeClient {
        private View mCustomView;
        private WebChromeClient.CustomViewCallback mCustomViewCallback;
        //protected FrameLayout mFullscreenContainer;
//private int mOriginalOrientation;
        private int mOriginalSystemUiVisibility;

        myChrome() {
        }

        public Bitmap getDefaultVideoPoster() {
            if (mCustomView == null) {
                return null;
            }
            return BitmapFactory.decodeResource(getApplicationContext().getResources(), 2130837573);
        }

        public void onHideCustomView() {
            ((FrameLayout) getWindow().getDecorView()).removeView(this.mCustomView);
            this.mCustomView = null;
            getWindow().getDecorView().setSystemUiVisibility(this.mOriginalSystemUiVisibility);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
            this.mCustomViewCallback.onCustomViewHidden();
            this.mCustomViewCallback = null;
        }

        public void onShowCustomView(View paramView, WebChromeClient.CustomViewCallback paramCustomViewCallback) {
            if (this.mCustomView != null) {
                onHideCustomView();
                return;
            }
            this.mCustomView = paramView;
            this.mOriginalSystemUiVisibility = getWindow().getDecorView().getSystemUiVisibility();
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
//this.mOriginalOrientation = getRequestedOrientation();
            this.mCustomViewCallback = paramCustomViewCallback;
            ((FrameLayout) getWindow().getDecorView()).addView(this.mCustomView, new FrameLayout.LayoutParams(-1, -1));
            getWindow().getDecorView().setSystemUiVisibility(3846);
        }
    }

}