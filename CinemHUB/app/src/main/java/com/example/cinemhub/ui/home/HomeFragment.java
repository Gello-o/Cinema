package com.example.cinemhub.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinemhub.MainActivity;
import com.example.cinemhub.R;
import com.example.cinemhub.adapter.MoviesAdapter;
import com.example.cinemhub.model.Movie;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL;


public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";
    private HomeViewModel homeViewModel;
    private RecyclerView popularRV;
    private MoviesAdapter popularAdapter;
    private RecyclerView topRatedRV;
    private MoviesAdapter topRatedAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        popularRV = root.findViewById(R.id.recycler_view_popular);
        topRatedRV = root.findViewById(R.id.recycler_view_top_rated);

        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        homeViewModel.getPopolari().observe(getViewLifecycleOwner(), new Observer<HashSet<Movie>>() {
            @Override
            public void onChanged(@Nullable HashSet<Movie> moviesSet) {
                if(moviesSet.isEmpty())
                    Log.d(TAG, "moviesSet nullo");
                popularAdapter.notifyDataSetChanged();
            }
        });

        homeViewModel.getTopRated().observe(getViewLifecycleOwner(), new Observer<HashSet<Movie>>() {
            @Override
            public void onChanged(@Nullable HashSet<Movie> moviesSet) {
                if(moviesSet.isEmpty())
                    Log.d(TAG, "moviesSet nullo");
                topRatedAdapter.notifyDataSetChanged();
            }
        });

        initPopularRV();

        initTopRatedRV();

        return root;
    }

    public void initPopularRV (){
        HashSet<Movie> set = homeViewModel.getPopolari().getValue();
        ArrayList<Movie> list = new ArrayList<>();
        list.addAll(set);
        popularAdapter = new MoviesAdapter(getContext(), list);
        if(popularAdapter == null)
            Log.d(TAG, "adapter null");
        if(popularRV == null)
            Log.d(TAG, "RECYCLER null");
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        popularRV.setLayoutManager(layoutManager);
        popularRV.setAdapter(popularAdapter);
    }

    public void initTopRatedRV (){
        HashSet<Movie> set = homeViewModel.getTopRated().getValue();
        ArrayList<Movie> list = new ArrayList<>();
        list.addAll(set);
        topRatedAdapter = new MoviesAdapter(getContext(), list);
        if(topRatedAdapter == null)
            Log.d(TAG, "adapter null");
        if(topRatedRV == null)
            Log.d(TAG, "RECYCLER null");
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        topRatedRV.setLayoutManager(layoutManager);
        topRatedRV.setAdapter(topRatedAdapter);
    }

}

