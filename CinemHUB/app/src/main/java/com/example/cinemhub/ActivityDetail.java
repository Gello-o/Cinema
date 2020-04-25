package com.example.cinemhub;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.Objects;

public class ActivityDetail extends AppCompatActivity {
    TextView nameOfMovie, plotSynopsis, userRating, releaseDate;
    ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = findViewById(R.id.toolbar_activity_detail);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        initCollapsingToolbar();

        imageView = findViewById(R.id.thumbnail);
        nameOfMovie = findViewById(R.id.movieTitle);
        plotSynopsis = findViewById(R.id.plotsynopsis);
        userRating = findViewById(R.id.usersRating);
        releaseDate = findViewById(R.id.releaseDate);

        Intent intent = getIntent();

        if(intent.hasExtra("original_title")){
            try {
                String thumnail = intent.getExtras().getString("poster_path");
                String movieName = intent.getExtras().getString("original_title");
                String synopsis = intent.getExtras().getString("overview");
                String rating = intent.getExtras().getString("vote_average");
                String release = intent.getExtras().getString("release_date");

                Glide.with(this)
                        .load(thumnail)
                        .placeholder(R.drawable.ic_launcher_background)
                        .into(imageView);

                nameOfMovie.setText(movieName);
                plotSynopsis.setText(synopsis);
                userRating.setText(rating);
                releaseDate.setText(release);

            }catch(NullPointerException e){
                System.out.println("qualcuno degli assegnamenti è null");
            }
        }
        else
            Toast.makeText(this, "no api data", Toast.LENGTH_SHORT).show();
    }

    public void initCollapsingToolbar(){
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
}
