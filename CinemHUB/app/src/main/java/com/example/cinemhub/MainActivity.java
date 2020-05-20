package com.example.cinemhub;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.SearchView;

import com.example.cinemhub.adapter.MoviesAdapter;
import com.example.cinemhub.model.Movie;
import com.example.cinemhub.model.MoviesRepository;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.app.SearchManager;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;

import java.io.Serializable;
import java.util.List;

public class MainActivity extends AppCompatActivity{

    private AppBarConfiguration mAppBarConfiguration;
    private static final String TAG = "MainActivity";
    ProgressDialog pd;

    private MutableLiveData<List<Movie>> mText;
    MoviesRepository repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        DrawerLayout drawer;
        drawer = findViewById(R.id.drawer_layout);
        /*
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        */



        //creato per provare a lanciare search result activity
        Intent intent = getIntent();
        if (intent.hasExtra("result")) {
            // Launched from search results
            Serializable selectedResult = (Serializable) intent.getSerializableExtra("result");
        }





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
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);

        implementSearch(menu);
        return true;
    }

    private void implementSearch(final Menu menu) {
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final MenuItem searchMenuItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) searchMenuItem.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);

        searchMenuItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                // the search view is now open. add your logic if you want
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        searchView.requestFocus();
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        if (imm != null) { // it's never null. I've added this line just to make the compiler happy
                            imm.showSoftInput(searchView.findFocus(), 0);
                        }


                    }
                });
                return true;
            }


            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                // the search view is closing. add your logic if you want
                return true;
            }

        });
        /*
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Search View Hint");
*/
        searchView.setOnQueryTextListener(new OnQueryTextListener() {

            @Override
            public boolean onQueryTextChange(String newText) {
                //newText = newText.replace(' ', '+');
                return false;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                query = query.replace(' ', '+');

                System.out.println("Query: " + query);

                // Do your task here

                return false;
            }

        });

      //  return true;
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
/*
            case R.id.button:
                Intent intent = new Intent(this, com.example.cinemhub.ricerca.SearchFragment.class);
                startActivity(intent);
                return true;
*/
            case R.id.menu_settings:
                Intent intent2 = new Intent(this, SettingsActivity.class);
                startActivity(intent2);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer;
        drawer = findViewById(R.id.drawer_layout);
        if(drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

}
