package com.example.cinemhub;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Menu;

import com.example.cinemhub.adapter.MoviesAdapter;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private static final String LOG_TAG = MoviesAdapter.class.getName();
    ProgressDialog pd;

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

        //initViews();
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

        if(item.getItemId() == R.id.menu_settings)
            return true;
        else
            return super.onOptionsItemSelected(item);
        /*
        switch(id){
            case R.id.categorie_fragment:
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_container, new CategorieFragment()).commit();
            case R.id.add_list_fragment:
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_container, new AddListFragment()).commit();
            case R.id.nuovi_arrivi_fragment:
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_container, new NuoviArriviFragment()).commit();
            case R.id.preferiti_fragment:
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_container, new PreferitiFragment()).commit();
            case R.id.piu_visti_fragment:
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_container, new CategorieFragment()).commit();
            case R.id.prossime_uscite_fragment:
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_container, new ProssimeUsciteFragment()).commit();
            default:
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_container, new HomeFragment()).commit();

        }
        */

    }

    public void initViews(){
        pd = new ProgressDialog(this);
        pd.setMessage("fetching movies");
        pd.setCancelable(false);
        pd.show();

        /*getSupportFragmentManager().beginTransaction()
                .replace(R.id.nav_host_fragment, homeFragment).commit();
        //View view = homeFragment.getView(); apperò
        adapter = homeFragment.getAdapter();
        movieList = homeFragment.getMovieList();

        loadJSON();*/
    }

}
