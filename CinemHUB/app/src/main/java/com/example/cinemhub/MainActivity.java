package com.example.cinemhub;

import android.app.AlertDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.cinemhub.model.FavoriteDB;
import com.google.android.material.navigation.NavigationView;
import androidx.annotation.NonNull;

import android.widget.Toast;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;



public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Test iniziale su connessione dispositivo (essenziale) per l'app
        if (!isConnected()) {
            new AlertDialog.Builder(this).setIcon(R.drawable.dialog_alert).setTitle("Internet Connection Alert")
                    .setMessage("Please Check your internet connection").setPositiveButton("close", (dialog, which) -> finish())
                    .show();
        } else {
            Toast.makeText(MainActivity.this, "Welcome in CinemHUB", Toast.LENGTH_LONG).show();


            Toolbar toolbar = findViewById(R.id.toolbar_main);
            setSupportActionBar(toolbar);


            DrawerLayout drawer = findViewById(R.id.drawer_layout);

            NavigationView navigationView = findViewById(R.id.nav_view);

            // menu should be considered as top level destinations.
            mAppBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.nav_home, R.id.nav_preferiti, R.id.nav_categorie, R.id.nav_nuovi_arrivi, R.id.nav_prossime_uscite, R.id.nav_piu_visti)
                    .setDrawerLayout(drawer)
                    .build();
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
            NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
            NavigationUI.setupWithNavController(navigationView, navController);

            Log.d(TAG, "creato il Db");
        }
        FavoriteDB.getInstance(getApplicationContext());
        FavoriteDB.getInstanceUser();

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
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

    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService((Context.CONNECTIVITY_SERVICE));

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnected();
    }

}





