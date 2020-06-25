package com.example.cinemhub;

import android.app.AlertDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.cinemhub.model.FavoriteDB;
import com.google.android.material.navigation.NavigationView;
import androidx.annotation.NonNull;

import androidx.annotation.RequiresApi;
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
    private boolean isRunning = true;

    @Override
    protected void onResume() {
        super.onResume();
        this.isRunning = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        this.isRunning = false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.isRunning = false;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Test iniziale su connessione dispositivo app
        if (!isConnected()) {
            new AlertDialog.Builder(MainActivity.this).setIcon(R.drawable.dialog_alert).setTitle(R.string.connection_alert)
                    .setMessage(R.string.connection_lost1).setPositiveButton("close", (dialog, which) -> finish())
                    .show();
        }
        else{
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkRequest.Builder builder = new NetworkRequest.Builder();
            AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this).setIcon(R.drawable.dialog_alert);

            connectivityManager.registerNetworkCallback(
                    builder.build(),
                    new ConnectivityManager.NetworkCallback() {

                        // Perdita connessione durante uso app
                        @Override
                        public void onLost(@NonNull Network network) {
                            if(isRunning){
                                alert.setTitle(R.string.connection_alert)
                                        .setMessage(R.string.connection_lost).setNegativeButton("close", (dialog, which) -> finish()).setPositiveButton("continue without network", (dialog, which) -> {
                                        })
                                        .show();
                            }
                        }

                    }
            );

            Toolbar toolbar = findViewById(R.id.toolbar_main);
            setSupportActionBar(toolbar);


            DrawerLayout drawer = findViewById(R.id.drawer_layout);

            NavigationView navigationView = findViewById(R.id.nav_view);


            mAppBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.nav_home, R.id.nav_preferiti, R.id.nav_categorie, R.id.nav_nuovi_arrivi, R.id.nav_prossime_uscite, R.id.nav_piu_visti)
                    .setDrawerLayout(drawer)
                    .build();
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
            NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
            NavigationUI.setupWithNavController(navigationView, navController);

            Log.d(TAG, "creato il Db");
// }
            FavoriteDB.getInstance(getApplicationContext());
        }
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

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService((Context.CONNECTIVITY_SERVICE));

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnected();
    }

}




