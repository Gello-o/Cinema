package com.example.cinemhub;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.Menu;
import android.widget.Toast;

import com.example.cinemhub.adapter.MoviesAdapter;
import com.example.cinemhub.api.Client;
import com.example.cinemhub.api.Service;
import com.example.cinemhub.model.Movie;
import com.example.cinemhub.model.MoviesResponse;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private static final String LOG_TAG = MoviesAdapter.class.getName();
    private RecyclerView recyclerView;
    private MoviesAdapter adapter;
    private List<Movie> movieList;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        DrawerLayout drawer;
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_preferiti, R.id.nav_add_list,R.id.nav_categorie,R.id.nav_nuovi_arrivi,R.id.nav_prossime_uscite,R.id.nav_piu_visti)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        initViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == R.id.menu_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public Activity getActivity(){
        Context context = this;
        while(context instanceof ContextWrapper){
            if(context instanceof Activity)
                return (Activity) context;
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }

    public void initViews(){
        pd = new ProgressDialog(this);
        pd.setMessage("fetching movies");
        pd.setCancelable(false);
        pd.show();

        recyclerView = findViewById(R.id.recycler_view);

        movieList = new ArrayList<>();

        adapter = new MoviesAdapter(this, movieList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(adapter);

        adapter.notifyDataSetChanged();

        pd.dismiss();
        //loadJSON();
    }

    private void loadJSON(){
        try{
            /*if(BuildConfig.the_movie_db_api.isEmpty()){
                Toast.makeText(getApplicationContext(), "Please obtain API key firstly from themoviedb", Toast.LENGTH_SHORT);
                pd.dismiss();
                return;
            }*/
            Service apiService = Client.getClient().create(Service.class);
            Call<MoviesResponse> call;
            call = apiService.getPopularMovies(getString(R.string.THE_MOVIE_DB_API_TOKEN));
            call.enqueue(new Callback<MoviesResponse>() {
                @Override
                public void onResponse(@NonNull Call<MoviesResponse> call, @NonNull Response<MoviesResponse> response) {
                    List<Movie> movies;
                    if(response.body() != null) {
                        movies = response.body().getResults();
                        recyclerView.setAdapter(new MoviesAdapter(getApplicationContext(), movies));
                        recyclerView.smoothScrollToPosition(0);
                        pd.dismiss();
                    }
                    else
                        System.out.println("corpo della response == null");
                }

                @Override
                public void onFailure(@NonNull Call<MoviesResponse> call, @NonNull Throwable t) {
                    if(t.getMessage() != null)
                        Log.d("Error", t.getMessage());
                    else
                        System.out.println("qualcosa è andato storto");
                    Toast.makeText(MainActivity.this, "Error fetching data", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            System.out.println("Eccezione" + "/n" + e.getMessage());
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
