package com.example.cinemhub;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.SearchView;

import com.example.cinemhub.adapter.MoviesAdapter;
import com.example.cinemhub.model.Movie;
import com.example.cinemhub.model.MoviesRepository;
import com.example.cinemhub.ricerca.SearchFragment;
import com.example.cinemhub.ricerca.SearchViewModel;
import com.example.cinemhub.ui.home.HomeFragment;
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

public class MainActivity extends AppCompatActivity  implements KeyEvent.Callback{

    private AppBarConfiguration mAppBarConfiguration;
    private static final String TAG = "MainActivity";
    ProgressDialog pd;
    private Context mContext;

    private MutableLiveData<List<Movie>> mText;
    MoviesRepository repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

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
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Insert here the title");

        searchView.setOnQueryTextListener(new OnQueryTextListener() {

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.setOnSearchClickListener(Navigation.createNavigateOnClickListener(R.id.action_nav_home_to_searchFragment, null));
                query = query.replace(' ', '+');
                /*if(newText.charAt(newText.length()-1) == ' ')
                    if(newText.length()>1 && newText.charAt(newText.length()-2) == ' ')
                        newText = newText.substring(0, newText.length()-2);*/
                //String s = "";
                int i = 0;
                /*while(i<query.length()) {
                    if(query.charAt(i) != '+' && query.charAt(i+1) != '+')
                        System.out.println("Corretto");
                    i++;
                    //s = s + query.charAt(i);
                }*/
                System.out.println("Query: " + query);
                ComponentName cm = new ComponentName(mContext, SearchFragment.class);
                //if(searchManager.getSearchableInfo(cm)==null) System.out.println("Null idiota");
                //searchView.setSearchableInfo(searchManager.getSearchableInfo(cm));
                return false;
            }

        });
        return true;
    }

    /*
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event){
        switch (keyCode) {
            case KeyEvent.KEYCODE_ENTER:
                Log.d(TAG, "messaggio1");
                try
                {
                    //
                    if(event.getAction()== KeyEvent.ACTION_UP)
                    {
                        System.out.println(event.getAction() + " " + event.getKeyCode() + " - " + (char) event.getUnicodeChar());
                    }
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }

                return true;

            // moveShip(MOVE_RIGHT);

            case KeyEvent.KEYCODE_F:
                // moveShip(MOVE_RIGHT);
                Log.d(TAG, "messaggio2");
                return true;
            default:
                Log.d(TAG, "messaggio3");
                return super.onKeyUp(keyCode, event);
        }
    }*/



    /*
    public boolean dispatchKeyEvent(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
            Log.d("CHAR","YOU CLICKED ENTER KEY");
            return false;
        }
        return super.dispatchKeyEvent(e);
    };
    */


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
