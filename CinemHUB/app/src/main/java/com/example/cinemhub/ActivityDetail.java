package com.example.cinemhub;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.bumptech.glide.Glide;
import com.example.cinemhub.api.Client;
import com.example.cinemhub.api.Service;
import com.example.cinemhub.model.Movie;
import com.example.cinemhub.model.Trailer;
import com.example.cinemhub.model.TrailerResponse;
import com.example.cinemhub.room.Favorite;
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
    private static final String API_KEY = "740ef79d64b588653371072cdee99a0f";

    public Movie movie;
    String thumbnail, movieName, synopsis, rating, release, movie_id, movieOriginalName;
    public Favorite favorite;
    List<Favorite> line;

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

        if (intent.hasExtra("original_title")) {

            thumbnail = intent.getExtras().getString("poster_path");
            if (thumbnail == null) {
                Log.d(TAG, "immagine nulla");
            }
            movie_id = intent.getExtras().getString("id");
            movieName = intent.getExtras().getString("title");
            movieOriginalName = intent.getExtras().getString("original_title");
            rating = intent.getExtras().getString("vote_average");
            synopsis = intent.getExtras().getString("overview");
            release = intent.getExtras().getString("release_date");

            Glide.with(this)
                    .load(thumbnail)
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(imageView);

            nameOfMovie.setText(movieName);
            plotSynopsis.setText(synopsis);
            userRating.setText(rating);
            releaseDate.setText(release);


            //Qua bisogna passare il trailer. Penso che è d'obbligo farlo qui perché non ci sono altri punti nel codice
            //Dove possiamo passare al metodo getMovieTrailer l'id del film che serve per fare la chiamata all'api.




            /*String key;
            MutableLiveData<HashSet<Trailer>> trailer = MoviesPersistentData.getInstance().getTrailer(Integer.parseInt(id));
            if(trailer ==null) System.out.println("NULLPTR");
            else key = trailer.getClass().getKey();*/


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
        } else
            Toast.makeText(this, "no api data", Toast.LENGTH_SHORT).show();

        //favorite button
        LikeButton likeButtonFavorite =
                (LikeButton) findViewById(R.id.favorite_button);

        if (!checkFilm()) {
            likeButtonFavorite.setOnLikeListener(new OnLikeListener() {
                @Override
                public void liked(LikeButton likeButtonFavorite) {
                    Log.d(TAG, "cliccato favorite");

                    saveFavorite();
                    Snackbar.make(likeButtonFavorite, "Added to Favorite",
                            Snackbar.LENGTH_SHORT).show();
                    mostraDb();

                }

                @Override
                public void unLiked(LikeButton likeButtonFavorite) {
                    Log.d(TAG, "cliccato unfavorite");
                    cancellaDb();
                }
            });
        } else {
            likeButtonFavorite.setLiked(true);
            likeButtonFavorite.setOnLikeListener(new OnLikeListener() {
                @Override
                public void liked(LikeButton likeButtonFavorite) {
                    Log.d(TAG, "cliccato favorite 2");
                    saveFavorite();
                    Snackbar.make(likeButtonFavorite, "Added to Favorite",
                            Snackbar.LENGTH_SHORT).show();
                    mostraDb();
                }

                @Override
                public void unLiked(LikeButton likeButtonFavorite) {
                    Log.d(TAG, "cliccato unfavorite 2");
                    cancellaDb();
                }
            });
        }

        Log.d(TAG, "end of the intent");
    }


    public void initCollapsingToolbar() {
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
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle(getString(R.string.movie_details));
                    isShown = true;
                } else if (isShown) {
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

    private void saveFavorite() {
        favorite = new Favorite();

        favorite.setMovie_id(Integer.parseInt(movie_id));
        favorite.setTitle(movieOriginalName);
        favorite.setPosterPath(thumbnail);
        favorite.setUserRating(rating);
        favorite.setPlotSynopsys(synopsis);

        MainActivity.dbStructure.dbInterface().addFavorite(favorite);
        Log.d(TAG, "entarto nel Db");
    }

    //sEMPLICE LOG PER MOSTRARE IL TITOLE E GLI ID DEL DB.
    private void mostraDb() {
        line = MainActivity.dbStructure.dbInterface().getFavorite();
        for (Favorite favorite : line) {
            Log.d(TAG, "ID: " + favorite.getMovie_id() +
                    ",  Title: " + favorite.getTitle() +
                    ", PosterPath: " + favorite.getPosterPath());
        }
    }

    // CONTROLLA SE IL FILM è PRESENTE NEL DB ////////////////////////////////////////////
    private boolean checkFilm() {
        Log.d(TAG, "entrato nel check");
        line = MainActivity.dbStructure.dbInterface().getFavorite();
        for (Favorite favorite : line) {
            if (favorite.getMovie_id() == Integer.parseInt(movie_id))
                return (true);
        }
        return (false);
    }

    //DA CANCELLARE UNA VOLTA FIXATO
    private void cancellaDb() {
        MainActivity.dbStructure.clearAllTables();
        line = MainActivity.dbStructure.dbInterface().getFavorite();
        Log.d(TAG, "db size: " + line.size());
        mostraDb();
    }
}
