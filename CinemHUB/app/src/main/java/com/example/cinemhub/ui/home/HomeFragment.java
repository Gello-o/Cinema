package com.example.cinemhub.ui.home;

import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.cinemhub.R;
import com.example.cinemhub.adapter.MoviesAdapter;
import com.example.cinemhub.adapter.SliderPagerAdapter;
import com.example.cinemhub.model.Movie;
import com.example.cinemhub.model.Slide;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.List;
import java.util.List;
import java.util.Timer;
import android.os.Handler;
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
    private List<Slide> lstSlides;
    private ViewPager sliderpager;
    private TabLayout indicator;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        popularRV = root.findViewById(R.id.recycler_view_popular);
        topRatedRV = root.findViewById(R.id.recycler_view_top_rated);
        prossimeUsciteRV = root.findViewById(R.id.recycler_prossime_uscite);

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

        sliderpager = root.findViewById(R.id.slider_pager);

        indicator = root.findViewById(R.id.indicator);

        lstSlides = new ArrayList<>();
        lstSlides.add(new Slide(R.drawable.tolo, "tolo tolo"));
        lstSlides.add(new Slide(R.drawable.future, "back to the future"));
        lstSlides.add(new Slide(R.drawable.tolo, "tolo tolo"));
        lstSlides.add(new Slide(R.drawable.future, "back to the future"));


        SliderPagerAdapter adapter2 = new SliderPagerAdapter(getContext(), lstSlides);
        sliderpager.setAdapter(adapter2);


        messageHandler = new Handler(Looper.getMainLooper());
        // setup timer
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new HomeFragment.SliderTimer(),4000,4000);


        indicator.setupWithViewPager(sliderpager,true);


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


    class SliderTimer extends TimerTask {


        @Override
        public void run() {

            HomeFragment.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (sliderpager.getCurrentItem()<lstSlides.size()-1) {
                        sliderpager.setCurrentItem(sliderpager.getCurrentItem()+1);
                    }
                    else
                        sliderpager.setCurrentItem(0);
                }
            });


        }
    }

    private Handler messageHandler;
    protected void runOnUiThread(Runnable action) {
        messageHandler.post(action);
    }




}

