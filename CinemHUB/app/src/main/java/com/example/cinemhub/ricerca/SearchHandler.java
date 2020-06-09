package com.example.cinemhub.ricerca;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.SearchView;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.cinemhub.R;
import com.example.cinemhub.ui.add_list.AddListFragment;
import com.example.cinemhub.ui.add_list.AddListFragmentDirections;
import com.example.cinemhub.ui.categorie.CategorieFragment;
import com.example.cinemhub.ui.categorie.CategorieFragmentDirections;
import com.example.cinemhub.ui.home.HomeFragment;
import com.example.cinemhub.ui.home.HomeFragmentDirections;
import com.example.cinemhub.ui.nuovi_arrivi.NuoviArriviFragment;
import com.example.cinemhub.ui.nuovi_arrivi.NuoviArriviFragmentDirections;
import com.example.cinemhub.ui.nuovi_arrivi.NuoviArriviViewModel;
import com.example.cinemhub.ui.piu_visti.PiuVistiFragment;
import com.example.cinemhub.ui.piu_visti.PiuVistiFragmentDirections;
import com.example.cinemhub.ui.preferiti.PreferitiFragment;
import com.example.cinemhub.ui.preferiti.PreferitiFragmentDirections;
import com.example.cinemhub.ui.prossime_uscite.ProssimeUsciteFragment;
import com.example.cinemhub.ui.prossime_uscite.ProssimeUsciteFragmentDirections;

public class SearchHandler {
    private static final String TAG = "SearchHandler";
    private Fragment fragment;
    private Menu menu;

    public SearchHandler(Menu menu, Fragment fragment){
        this.fragment = fragment;
        this.menu = menu;
    }
    
    public void implementSearch(int tipo) {
        SearchManager searchManager = (SearchManager) fragment.getActivity().getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchMenuItem;
        if(tipo == 1) {
            searchMenuItem = menu.findItem(R.id.action_search);
        }else{
            searchMenuItem = menu.findItem(R.id.action_search1);
        }

        if(searchMenuItem == null){
            Log.d(TAG, "NULLO");
        }else{
            Log.d(TAG, "NON NULLO");
            Log.d(TAG, "titolo" + searchMenuItem.getTitle());
            Log.d(TAG, "info" + searchMenuItem.getMenuInfo());
            Log.d(TAG, "view" + searchMenuItem.getActionView());

            SearchView searchView = (SearchView) searchMenuItem.getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(fragment.getActivity().getComponentName()));
            searchView.setIconifiedByDefault(false);

            searchMenuItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
                @Override
                public boolean onMenuItemActionExpand(MenuItem item) {
                    // the search view is now open
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            searchView.requestFocus();
                            InputMethodManager imm = (InputMethodManager) fragment.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
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

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

                @Override
                public boolean onQueryTextChange(String newText) {
                    //qua potremmo mostrare suggerimenti volendo: da capire
                    return false;
                }

                @Override
                public boolean onQueryTextSubmit(String query) {
                    String queryFinal = query.trim().replaceAll("\\s+", "+").replaceAll("[^a-zA-Z0-9+]", "");
                    System.out.println("Query: " + queryFinal);
                    View view = fragment.getView();
                    NavController navController = Navigation.findNavController(view);

                    Log.d(TAG, "query submitted");
                    if(fragment instanceof PreferitiFragment){
                        Log.d(TAG, "preferiti");
                        PreferitiFragmentDirections.GoToSearchAction action =
                                PreferitiFragmentDirections.goToSearchAction(queryFinal);
                        navController.navigate(action);
                    }else if(fragment instanceof PiuVistiFragment){
                        Log.d(TAG, "piu visti");
                        PiuVistiFragmentDirections.GoToSearchAction action =
                                PiuVistiFragmentDirections.goToSearchAction(queryFinal);
                        navController.navigate(action);
                    }else if(fragment instanceof AddListFragment){
                        Log.d(TAG, "addlist");
                        AddListFragmentDirections.GoToSearchAction action =
                                AddListFragmentDirections.goToSearchAction(queryFinal);
                        navController.navigate(action);
                    }else if(fragment instanceof ProssimeUsciteFragment) {
                        Log.d(TAG, "prossime uscite");
                        ProssimeUsciteFragmentDirections.GoToSearchAction action =
                                ProssimeUsciteFragmentDirections.goToSearchAction(queryFinal);
                        navController.navigate(action);
                    }else if(fragment instanceof NuoviArriviFragment){
                        Log.d(TAG, "nuovi arrivi");
                        NuoviArriviFragmentDirections.GoToSearchAction action =
                                NuoviArriviFragmentDirections.goToSearchAction(queryFinal);
                        navController.navigate(action);
                    }else if(fragment instanceof HomeFragment){
                        Log.d(TAG, "home");
                        HomeFragmentDirections.GoToSearchAction action =
                                HomeFragmentDirections.goToSearchAction(queryFinal);
                        navController.navigate(action);
                    }else if(fragment instanceof CategorieFragment){
                        Log.d(TAG, "categorie");
                        CategorieFragmentDirections.GoToSearchAction action =
                                CategorieFragmentDirections.goToSearchAction(queryFinal);
                        navController.navigate(action);
                    }
                    else{
                   /*gestione casi in cui non devo lanciare la ricerca:
                   A) NON MOSTRO IL BOTTONE
                   B) QUANDO CLICCA SU SUBMIT MOSTRO ALERT
                   */Log.d(TAG, "altro mannaggia");

                    }

                    return false;
                }
            });
        }


    }

}
