package com.example.cinemhub;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import com.bumptech.glide.Glide;
import com.example.cinemhub.api.Client;
import com.example.cinemhub.api.Service;
import com.example.cinemhub.model.Favorite;
import com.example.cinemhub.model.FavoriteDB;
import com.example.cinemhub.model.Favorite;
import com.example.cinemhub.model.MoviesRepository;
import com.example.cinemhub.model.Trailer;
import com.example.cinemhub.model.TrailerResponse;

import com.example.cinemhub.model.UserRatingDB;
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
    private TextView nameOfMovie, plotSynopsis, userRating, releaseDate, showVote;
    private EditText editText;
    private ImageView imageView;
    private WebView webView;
    private VideoView videoView;
    String thumbnail, movieName, synopsis, rating, release, id, originalMovieName, voteCount, genre;
    float submittedRating;
    public Favorite favorite;
    public UserRatingDB userRatingDB;
    List<Favorite> line;
    List<UserRatingDB> lineUser;
    private YouTubePlayerView playerView;
    private YouTubePlayer.OnInitializedListener initializedListener;
    private static final String API_KEY = "740ef79d64b588653371072cdee99a0f";
    private final String YT_API_KEY = "AIzaSyC95r_3BNU_BxvSUE7ZyXKrar3dc127rVk";
    private final String base_image_Url = "https://image.tmdb.org/t/p/w500";
    private Context mContext;
    RatingBar ratingBar;
    Button button;
    boolean recensito;
    String commento;

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
        //webView = findViewById(R.id.videoWebView);
        playerView = findViewById(R.id.player);



        /*WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());*/
        //webView.setWebChromeClient(new myChrome());


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

            /*
            Intent intent2 = new Intent(mContext, WebViewActivity.class);
            webView.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    System.out.println("Touched: " + webView.getUrl());
                    intent2.putExtra("key", webView.getUrl());
                    System.out.println(intent2.putExtra("key", webView.getUrl()));
                    intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent2);
                    return false;
                }
            });

            */



            MutableLiveData<String> keyDatum = new MutableLiveData<>();
            MoviesRepository.getInstance().getTrailer(id, keyDatum);

            initializedListener = new YouTubePlayer.OnInitializedListener() {
                @Override
                public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                    youTubePlayer.loadVideo(keyDatum.getValue());
                    Log.d(TAG,"success");
                }

                @Override
                public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                    Log.d(TAG,"fail");
                }
            };

            playerView.initialize(YT_API_KEY, initializedListener);



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

            //MoviesRepository.getInstance().getTrailers(id, webView);

        }
        else
            Toast.makeText(this, "no api data", Toast.LENGTH_SHORT).show();

        // uteenteeeeeeee
        button = findViewById(R.id.submit_rating);
        ratingBar = findViewById(R.id.ratingBar);
        editText = findViewById(R.id.user_overview);
        showVote = findViewById(R.id.show_vote);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                submittedRating = ratingBar.getRating();
                showVote.setText("your Rating: " + submittedRating);
                Log.d(TAG,"ottenuto il voto: " + ratingBar.getRating());
            }
        });


        recensito = checkUser();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userRatingDB = new UserRatingDB();
                String name = editText.getText().toString();
                Log.d(TAG, "ottenuta la string " + name);
                if(name.equals("")){
                    if(commento != null && !commento.equals("")){
                        name = commento;
                        //textView = commento
                    }
                }else{
                    //textView = name
                }

                userRatingDB.setMovie_id(Integer.parseInt(id));
                userRatingDB.setRating(submittedRating);
                userRatingDB.setOverview(name);
                if(submittedRating!=0) {
                    if (recensito) {
                        FavoriteDB.getInstance().dbInterface().updateUserRating(userRatingDB);
                        Log.d(TAG, "update dbuser ok " + movieName);
                        mostraDbUser();
                    } else {
                        FavoriteDB.getInstance().dbInterface().addUserRating(userRatingDB);
                        Log.d(TAG, "memorizzato in dbUser 1 volta" + movieName);
                        mostraDbUser();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "inserire un punteggio che non sia 0", Toast.LENGTH_SHORT).show();
                }
            }
        });

////////////////
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
        Log.d(TAG,"entarto nella tab Favorite");
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


////////////////
    private void mostraDbUser() {
        lineUser = FavoriteDB.getInstance().dbInterface().getUserOverview();
        for(UserRatingDB userRatingDB : lineUser){
            Log.d(TAG,"Database: " +
                    " ID: " + userRatingDB.getMovie_id() +
                    " Rating: " + userRatingDB.getRating() +
                    " Overview: " + userRatingDB.getOverview() +
                    " titolo: " + movieName);
        }
    }

    private boolean checkUser(){
        UserRatingDB user;
        Log.d(TAG,"entrato nel check");
        user = FavoriteDB.getInstance().dbInterface().getUserInfo(Integer.parseInt(id));
        if(user != null){
            commento= user.getOverview();
            submittedRating = user.getRating();
            ratingBar.setRating(submittedRating);
            return true;
        }else {
            return false;
        }
    }

////////////////////

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