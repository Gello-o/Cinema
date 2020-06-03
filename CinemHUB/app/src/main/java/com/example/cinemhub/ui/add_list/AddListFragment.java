package com.example.cinemhub.ui.add_list;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.cinemhub.R;
import com.example.cinemhub.adapter.MoviesAdapter;
import com.example.cinemhub.filtri.FilterHandler;
import com.example.cinemhub.model.Movie;
import com.example.cinemhub.ricerca.SearchHandler;

import java.util.ArrayList;
import java.util.List;

public class AddListFragment extends Fragment {

    private static final String TAG = "AddListFragment";
    private AddListViewModel addListViewModel;
    private RecyclerView actionMoviesRV;
    private MoviesAdapter moviesAdapter;
    private List<Movie> currentList = new ArrayList<>();
    FilterHandler filterOperation;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        addListViewModel =
                new ViewModelProvider(this).get(AddListViewModel.class);

        View root = inflater.inflate(R.layout.fragment_add_list, container, false);

        actionMoviesRV = root.findViewById(R.id.recycler_view_add_list);

        addListViewModel.getText().observe(getViewLifecycleOwner(), new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> s) {
                Log.d(TAG, "Observe");
                if (s == null)
                    Log.d(TAG, "caricamento fallito");
                initMovieRV(s);
                if (filterOperation != null) {
                    filterOperation.setMovie(s);
                    //initFilterObserver();
                } else
                    Log.d(TAG, "FilterOperationNull");
                Log.d(TAG, "OnChanged");
            }
        });

        setHasOptionsMenu(true);
        return root;
    }

    public void initMovieRV(List<Movie> movies) {
        moviesAdapter = new MoviesAdapter(getActivity(), movies);
        //Log.d(TAG, "primofilm: " + movies.get(0).getVoteAverage());

        RecyclerView.LayoutManager layoutManager;
        if (getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            layoutManager = new GridLayoutManager(getActivity(), 3);
        else
            layoutManager = new GridLayoutManager(getActivity(), 4);
        actionMoviesRV.setLayoutManager(layoutManager);
        actionMoviesRV.setAdapter(moviesAdapter);
        actionMoviesRV.setItemAnimator(new DefaultItemAnimator());

    }

    public void initMovieRV(List<Movie> movies, Fragment fragment) {
        moviesAdapter = new MoviesAdapter(fragment.getActivity(), movies);

        RecyclerView.LayoutManager layoutManager;
        if (getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            layoutManager = new GridLayoutManager(getActivity(), 3);
        else
            layoutManager = new GridLayoutManager(getActivity(), 4);
        actionMoviesRV.setLayoutManager(layoutManager);
        actionMoviesRV.setAdapter(moviesAdapter);
        actionMoviesRV.setItemAnimator(new DefaultItemAnimator());

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main3, menu);
        SearchHandler searchOperation = new SearchHandler(menu, this);
        filterOperation = new FilterHandler(menu, this);
        searchOperation.implementSearch(2);
        filterOperation.implementFilter(2);

        Log.d(TAG, "OnCreateOptionsMenu");
        super.onCreateOptionsMenu(menu, inflater);
    }

}