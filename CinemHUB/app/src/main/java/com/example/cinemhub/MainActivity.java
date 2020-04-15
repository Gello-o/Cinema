package com.example.cinemhub;

import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.cinemhub.ui.activity.MovieActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinemhub.R;
import com.example.cinemhub.cinemhub.model.Movie;
import com.example.cinemhub.cinemhub.model.MoviePageResult;
import com.example.cinemhub.network.GetMovieDataService;
import com.example.cinemhub.network.RetrofitInstance;
import com.example.cinemhub.ui.adapter.MovieAdapter;
import com.example.cinemhub.ui.utils.EndlessRecyclerViewScrollListener;
import com.example.cinemhub.ui.utils.MovieClickListener;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    public static final String API_KEY = "740ef79d64b588653371072cdee99a0f";
    private static int totalPages;
    private static int currentSortMode = 1;
    private Call<MoviePageResult> call;
    private RecyclerView recyclerView;
    private List<Movie> movieResults;
    private MovieAdapter movieAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        recyclerView = findViewById(R.id.rv_movies);
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return 1;
            }
        });
        recyclerView.setLayoutManager(manager);

        loadPage(1);

        EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(manager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if ((page + 1) <= totalPages) {
                    loadPage(page + 1);
                }
            }
        };

        recyclerView.addOnScrollListener(scrollListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sort_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //SortID 1 -> Popularity
        //SortID 2 -> Top rated
        switch (item.getItemId()) {
            case R.id.sort_by_popularity:
                currentSortMode = 1;
                break;
            case R.id.sort_by_top:
                currentSortMode = 2;
                break;
        }
        loadPage(1);
        return super.onOptionsItemSelected(item);

    }

    private void loadPage(final int page) {
        GetMovieDataService movieDataService = RetrofitInstance.getRetrofitInstance().create(GetMovieDataService.class);

        switch(currentSortMode){
            case 1:
                call = movieDataService.getPopularMovies(page, API_KEY);
                break;
            case 2:
                call = movieDataService.getTopRatedMovies(page, API_KEY);
                break;
        }


        call.enqueue(new Callback<MoviePageResult>() {
            @Override
            public void onResponse(Call<MoviePageResult> call, Response<MoviePageResult> response) {

                if(page == 1) {
                    movieResults = response.body().getMovieResult();
                    totalPages = response.body().getTotalPages();

                    movieAdapter = new MovieAdapter(movieResults, new MovieClickListener() {
                        @Override
                        public void onMovieClick(Movie movie) {
                            Intent intent = new Intent(MainActivity.this, MovieActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("movie", movie);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    });
                    recyclerView.setAdapter(movieAdapter);
                } else {
                    List<Movie> movies = response.body().getMovieResult();
                    for(Movie movie : movies){
                        movieResults.add(movie);
                        movieAdapter.notifyItemInserted(movieResults.size() - 1);
                    }
                }

            }

            @Override
            public void onFailure(Call<MoviePageResult> call, Throwable t) {
                Toast.makeText(MainActivity.this, R.string.something_went_wrong, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static String movieImagePathBuilder(String imagePath) {
        return "https://image.tmdb.org/t/p/" +
                "w500" +
                imagePath;
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }*/

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}