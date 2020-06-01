package com.example.cinemhub.ui.home;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import com.example.cinemhub.R;
import com.example.cinemhub.adapter.MoviesAdapter;
import com.example.cinemhub.adapter.SliderPagerAdapter;
import com.example.cinemhub.model.Movie;
import com.example.cinemhub.ricerca.SearchHandler;
import com.google.android.material.tabs.TabLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import android.os.Handler;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;

import java.util.TimerTask;


public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";
    private HomeViewModel homeViewModel;
    private RecyclerView popularRV;
    private MoviesAdapter popularAdapter;
    private RecyclerView topRatedRV;
    private MoviesAdapter topRatedAdapter;
    private RecyclerView prossimeUsciteRV;
    private MoviesAdapter prossimeUsciteAdapter;
    private ViewPager sliderpager;
    private TabLayout indicator;
    List<Movie> slides;
    SliderPagerAdapter slideAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        popularRV = root.findViewById(R.id.recycler_view_popular);
        topRatedRV = root.findViewById(R.id.recycler_view_top_rated);
        prossimeUsciteRV = root.findViewById(R.id.recycler_prossime_uscite);
        sliderpager = root.findViewById(R.id.slider_pager);
        indicator = root.findViewById(R.id.indicator);

        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        homeViewModel.getPopolari().observe(getViewLifecycleOwner(), new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> moviesSet) {
                if(moviesSet == null)
                    Log.d(TAG, "moviesSet nullo");
                else
                    Log.d(TAG, ""+moviesSet.size());
                initPopularRV(moviesSet);
                popularAdapter.notifyDataSetChanged();
            }
        });

        homeViewModel.getTopRated().observe(getViewLifecycleOwner(), new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> moviesSet) {
                if(moviesSet == null)
                    Log.d(TAG, "moviesSet nullo");
                else
                    Log.d(TAG, ""+moviesSet.size());
                initTopRatedRV(moviesSet);
                topRatedAdapter.notifyDataSetChanged();
            }
        });

        homeViewModel.getProssimeUscite().observe(getViewLifecycleOwner(), new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> moviesSet) {
                if(moviesSet == null)
                    Log.d(TAG, "moviesSet nullo");
                else
                    Log.d(TAG, ""+moviesSet.size());
                initProssimeUscite(moviesSet);
                prossimeUsciteAdapter.notifyDataSetChanged();
            }
        });


        homeViewModel.getAlCinema().observe(getViewLifecycleOwner(), new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> moviesSet) {
                slides = new ArrayList<>();
                for(int i=0; i<4; i++){
                    slides.add(moviesSet.get(i));
                }
                initSlider();
                messageHandler = new Handler(Looper.getMainLooper());
                indicator.setupWithViewPager(sliderpager,true);
                Timer timer = new Timer();
                timer.scheduleAtFixedRate(new HomeFragment.SliderTimer(),4000,4000);

                slideAdapter.notifyDataSetChanged();
            }
        });

        setHasOptionsMenu(true);

        return root;
    }

    public void initPopularRV (List<Movie>set){
        if(set == null)
            Log.d(TAG, "SET POPOLARI NULL");
        else if(set.isEmpty())
            Log.d(TAG, "SET POPOLARI vuoto");
        popularAdapter = new MoviesAdapter(getContext(), set);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        popularRV.setLayoutManager(layoutManager);
        popularRV.setAdapter(popularAdapter);
        popularRV.setItemAnimator(new DefaultItemAnimator());
    }

    public void initTopRatedRV (List<Movie>set){
        if(set == null)
            Log.d(TAG, "SET top NULL");
        else if(set.isEmpty())
            Log.d(TAG, "SET top vuoto");
        topRatedAdapter = new MoviesAdapter(getContext(), set);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        topRatedRV.setLayoutManager(layoutManager);
        topRatedRV.setAdapter(topRatedAdapter);
        topRatedRV.setItemAnimator(new DefaultItemAnimator());
    }

    public void initProssimeUscite (List<Movie>set){
        if(set == null)
            Log.d(TAG, "SET Prossime NULL");
        else if(set.isEmpty())
            Log.d(TAG, "SET Prossime vuoto");
        prossimeUsciteAdapter = new MoviesAdapter(getContext(), set);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        prossimeUsciteRV.setLayoutManager(layoutManager);
        prossimeUsciteRV.setAdapter(prossimeUsciteAdapter);
        prossimeUsciteRV.setItemAnimator(new DefaultItemAnimator());
    }

    public void initSlider(){
        if(slides == null)
            Log.d(TAG, "Slider null");
        else if(slides.isEmpty())
            Log.d(TAG, "Slider vuoto");
        slideAdapter = new SliderPagerAdapter(getActivity(), slides);
        sliderpager.setAdapter(slideAdapter);
    }


    class SliderTimer extends TimerTask {


        @Override
        public void run() {

            HomeFragment.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if(slides != null) {
                        if (sliderpager.getCurrentItem() < slides.size() - 1) {
                            sliderpager.setCurrentItem(sliderpager.getCurrentItem() + 1);
                        } else
                            sliderpager.setCurrentItem(0);
                    }
                }
            });


        }
    }

    private Handler messageHandler;
    protected void runOnUiThread(Runnable action) {
        messageHandler.post(action);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        SearchHandler searchOperation = new SearchHandler(menu, this);
        searchOperation.implementSearch(1);
        super.onCreateOptionsMenu(menu, inflater);
    }
}
