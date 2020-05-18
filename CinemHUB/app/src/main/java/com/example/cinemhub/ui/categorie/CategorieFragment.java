package com.example.cinemhub.ui.categorie;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinemhub.R;
import com.example.cinemhub.adapter.MoviesAdapter;
import com.example.cinemhub.model.Movie;
import com.example.cinemhub.model.Trailer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


public class CategorieFragment extends Fragment {
    private static final String TAG = "CategorieFragment";
    private CategorieViewModel categorieViewModel;
    private RecyclerView actionMoviesRV;
    private MoviesAdapter moviesAdapter;
    
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        categorieViewModel =
                new ViewModelProvider(this).get(CategorieViewModel.class);

        View root = inflater.inflate(R.layout.fragment_categorie, container, false);

        actionMoviesRV = root.findViewById(R.id.recycler_view_categorie);

        categorieViewModel.getPopolari().observe(getViewLifecycleOwner(), new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                if(movies == null)
                    Log.d(TAG, "caricamento fallito");
                initMovieRV(movies);
                moviesAdapter.notifyDataSetChanged();
            }
        });


        return root;
    }

    public void initMovieRV(List<Movie> movies){
        moviesAdapter = new MoviesAdapter(getActivity(), movies);
        if(moviesAdapter == null)
            Log.d(TAG, "adapter null");
        else {
            if (moviesAdapter.getMovieList() == null)
                Log.d(TAG, "lista null");
            if (moviesAdapter.getContext() == null)
                Log.d(TAG, "contesto null");
        }
        RecyclerView.LayoutManager layoutManager;
        if(getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            layoutManager = new GridLayoutManager(getActivity(), 3);
        else
            layoutManager = new GridLayoutManager(getActivity(), 4);
        actionMoviesRV.setLayoutManager(layoutManager);
        actionMoviesRV.setAdapter(moviesAdapter);
        actionMoviesRV.setItemAnimator(new DefaultItemAnimator());

    }
}

