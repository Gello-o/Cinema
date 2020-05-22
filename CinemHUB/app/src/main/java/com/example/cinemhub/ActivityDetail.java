package com.example.cinemhub;

import android.content.Intent;
import android.os.Bundle;
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
import com.example.cinemhub.model.MoviesRepository;
import com.example.cinemhub.model.Trailer;
import com.example.cinemhub.model.TrailerResponse;
import com.example.cinemhub.ui.home.HomeFragment;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

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

            String thumbnail = intent.getExtras().getString("poster_path");
            String movieName = intent.getExtras().getString("original_title");
            String synopsis = intent.getExtras().getString("overview");
            String rating = intent.getExtras().getString("vote_average");
            String release = intent.getExtras().getString("release_date");
            String id = intent.getExtras().getString("id");

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

            //Qua bisogna passare il trailer. Penso che è d'obbligo farlo qui perché non ci sono altri punti nel codice
            //Dove possiamo passare al metodo getMovieTrailer l'id del film che serve per fare la chiamata all'api.

            MoviesRepository.getInstance().getTrailer(id, webView);


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
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() == 0) {
            this.finish();
        } else {
            super.onBackPressed(); //replaced
        }
    }
}
