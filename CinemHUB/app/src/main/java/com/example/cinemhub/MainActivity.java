package com.example.cinemhub;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.cinemhub.model.FavoriteDB;
import com.google.android.material.navigation.NavigationView;
import androidx.annotation.NonNull;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private static final String TAG = "MainActivity";
    private final Context mContext = this;
    private DrawerLayout drawer;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Test iniziale su connessione dispositivo (essenziale) per l'app
        if (!isConnected()) {
            new AlertDialog.Builder(this).setIcon(R.drawable.dialog_alert).setTitle("Internet Connection Alert")
                    .setMessage("Please Check your internet connection").setPositiveButton("close", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            })
                    .show();
        } else {
            Toast.makeText(MainActivity.this, "Welcome in CinemHUB", Toast.LENGTH_LONG).show();


            Toolbar toolbar = findViewById(R.id.toolbar_main);
            setSupportActionBar(toolbar);


            drawer = findViewById(R.id.drawer_layout);

            NavigationView navigationView = findViewById(R.id.nav_view);

            // menu should be considered as top level destinations.
            mAppBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.nav_home, R.id.nav_preferiti, R.id.nav_add_list, R.id.nav_categorie, R.id.nav_nuovi_arrivi, R.id.nav_prossime_uscite, R.id.nav_piu_visti)
                    .setDrawerLayout(drawer)
                    .build();
            navController = Navigation.findNavController(this, R.id.nav_host_fragment);
            NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
            NavigationUI.setupWithNavController(navigationView, navController);

            FavoriteDB.getInstance(getApplicationContext());
            FavoriteDB.getInstanceUser();

            Log.d(TAG, "creato il Db");
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.filter:
                //implementFilter();
                /*
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
                alertDialog.setTitle("Enter Name");
                final EditText input = new EditText(mContext);
                final Spinner spinner = new Spinner(mContext);
                final Spinner spinner2 = new Spinner(mContext);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                spinner.setLayoutParams(lp);
                alertDialog.setView(spinner);
                spinner2.setLayoutParams(lp);
                alertDialog.setView(spinner2);
                alertDialog.setPositiveButton("Save",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                Toast.makeText(getApplicationContext(),
                                        "Name successfully changed", Toast.LENGTH_SHORT).show();
                            }
                        });
                alertDialog.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                alertDialog.show();
                */
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer;
        drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService((Context.CONNECTIVITY_SERVICE));

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnected();
    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        implementSearch(menu);
        super.onCreateOptionsMenu(menu);
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
                // the search view is now open
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
                // the search view is closing.
                return true;
            }

        });

        HomeFragmentDirections.GoToSearchAction action = 
                HomeFragmentDirections.goToSearchAction("");
        
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            int numeroDiSpazi = 0;

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d(TAG, "" + newText);
                //qua potremmo mostrare suggerimenti volendo: da capire
                return false;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                String queryFinal = query.trim().replaceAll("\\s+", "+").replaceAll("[^a-zA-Z0-9+]", "");
                System.out.println("Query: " + queryFinal);

                action.setQuery(queryFinal);
                navController.navigate(action);
                // Do your task here
                return false;
            }
        });

    }
    
*/

    void spinnerList(Spinner spinner, String s1, String s2, String s3, String s4) {
        List<String> categroyList = new ArrayList<>();

        categroyList.add(s1);
        categroyList.add(s2);
        categroyList.add(s3);
        categroyList.add(s4);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, categroyList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("Enter");
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }


}





