package com.example.cinemhub.ui.home;

import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
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
import com.example.cinemhub.menu_items.ricerca.SearchHandler;
import com.google.android.material.tabs.TabLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import android.os.Handler;
import android.widget.TextView;

import java.util.TimerTask;


public class HomeFragment extends Fragment {

    //private static final String TAG = "HomeFragment";
    private RecyclerView popularRV;
    private MoviesAdapter popularAdapter;
    private TextView popularTV;
    private RecyclerView topRatedRV;
    private MoviesAdapter topRatedAdapter;
    private TextView topRatedTV;
    private RecyclerView prossimeUsciteRV;
    private MoviesAdapter prossimeUsciteAdapter;
    private TextView prossimeUsciteTV;
    private ViewPager sliderpager;
    private TabLayout indicator;
    private List<Movie> slides;
    private SliderPagerAdapter slideAdapter;

    private Timer timer;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        popularRV = root.findViewById(R.id.recycler_view_popular);
        topRatedRV = root.findViewById(R.id.recycler_view_top_rated);
        prossimeUsciteRV = root.findViewById(R.id.recycler_prossime_uscite);
        popularTV = root.findViewById(R.id.text_popular);
        topRatedTV = root.findViewById(R.id.text_topRated);
        prossimeUsciteTV = root.findViewById(R.id.text_prossime_uscite);
        sliderpager = root.findViewById(R.id.slider_pager);
        indicator = root.findViewById(R.id.indicator);

        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        homeViewModel.getPopolari().observe(getViewLifecycleOwner(), moviesSet -> {
            initPopularRV(moviesSet);
            popularAdapter.notifyDataSetChanged();
        });

        homeViewModel.getTopRated().observe(getViewLifecycleOwner(), moviesSet -> {
            initTopRatedRV(moviesSet);
            topRatedAdapter.notifyDataSetChanged();
        });

        homeViewModel.getProssimeUscite().observe(getViewLifecycleOwner(), moviesSet -> {
            initProssimeUscite(moviesSet);
            prossimeUsciteAdapter.notifyDataSetChanged();
        });


        homeViewModel.getAlCinema().observe(getViewLifecycleOwner(), moviesSet -> {
            slides = new ArrayList<>();
            if(moviesSet != null && !moviesSet.isEmpty()) {
                for (int i = 0; i < moviesSet.size(); i++) {
                    if(moviesSet.get(i) != null && moviesSet.get(i).getVoteAverage() > 7)
                        slides.add(moviesSet.get(i));
                }
            }
            initSlider();
            messageHandler = new Handler(Looper.getMainLooper());
            indicator.setupWithViewPager(sliderpager,true);

            timer = new Timer();
            timer.scheduleAtFixedRate(new SliderTimer(),4000,4000);
            //timer.schedule(new SliderTimer(), 4000);
            slideAdapter.notifyDataSetChanged();
        });

        setHasOptionsMenu(true);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(timer != null) {
            timer.cancel();
        }
    }

    private void initPopularRV (List<Movie>set){
        popularAdapter = new MoviesAdapter(getActivity(), set);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        popularRV.setLayoutManager(layoutManager);
        popularRV.setAdapter(popularAdapter);
        popularRV.setItemAnimator(new DefaultItemAnimator());
        popularTV.setText(R.string.popolari);
    }

    private void initTopRatedRV (List<Movie>set){
        topRatedAdapter = new MoviesAdapter(getActivity(), set);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        topRatedRV.setLayoutManager(layoutManager);
        topRatedRV.setAdapter(topRatedAdapter);
        topRatedRV.setItemAnimator(new DefaultItemAnimator());
        topRatedTV.setText(R.string.top_rated);
    }

    private void initProssimeUscite (List<Movie>set){
        prossimeUsciteAdapter = new MoviesAdapter(getActivity(), set);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        prossimeUsciteRV.setLayoutManager(layoutManager);
        prossimeUsciteRV.setAdapter(prossimeUsciteAdapter);
        prossimeUsciteRV.setItemAnimator(new DefaultItemAnimator());
        prossimeUsciteTV.setText(R.string.upcoming);
    }

    private void initSlider(){
        slideAdapter = new SliderPagerAdapter(getActivity(), slides);
        sliderpager.setAdapter(slideAdapter);
    }


    class SliderTimer extends TimerTask {


        @Override
        public void run() {

            HomeFragment.this.runOnUiThread(() -> {

                if(slides != null) {
                    if (sliderpager.getCurrentItem() < slides.size() - 1)
                        sliderpager.setCurrentItem(sliderpager.getCurrentItem() + 1);
                     else
                        sliderpager.setCurrentItem(0);
                }
            });


        }
    }

    private Handler messageHandler;
    
    private void runOnUiThread(Runnable action) {
        messageHandler.post(action);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        SearchHandler searchOperation = new SearchHandler(menu, this);
        searchOperation.implementSearch(1);
        super.onCreateOptionsMenu(menu, inflater);
    }
}
