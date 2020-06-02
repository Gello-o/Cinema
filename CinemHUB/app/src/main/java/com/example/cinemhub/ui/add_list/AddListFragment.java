package com.example.cinemhub.ui.add_list;

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
import com.example.cinemhub.model.Movie;
import com.example.cinemhub.ricerca.FilterHandler;
import com.example.cinemhub.ricerca.SearchHandler;

import java.util.ArrayList;
import java.util.List;

public class AddListFragment extends Fragment {

    private static final String TAG = "AddListFragment";
    private AddListViewModel addListViewModel;
    private RecyclerView actionMoviesRV;
    private MoviesAdapter moviesAdapter;
    private List<Movie> currentList = new ArrayList<>();
    private FilterHandler filterHandler;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add_list, container, false);
        actionMoviesRV = root.findViewById(R.id.recycler_view_add_list);
        setHasOptionsMenu(true);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addListViewModel =
                new ViewModelProvider(this).get(AddListViewModel.class);

        RecyclerView.LayoutManager layoutManager;

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            layoutManager = new GridLayoutManager(getActivity(), 3);
        else
            layoutManager = new GridLayoutManager(getActivity(), 4);

        actionMoviesRV.setLayoutManager(layoutManager);
        actionMoviesRV.setItemAnimator(new DefaultItemAnimator());

        addListViewModel.getText().observe(getViewLifecycleOwner(), new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> s) {
                if(s == null)
                    Log.d(TAG, "caricamento fallito");
                initMoviesRV(s);
                if(filterHandler != null){
                    filterHandler.setMovie(s);
                    Log.d(TAG, "INIZIALIZZATO MOVIE HANDLER");
                }
                else
                    Log.d(TAG, "INIZIALIZZAZIONE FALLITA");
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main3, menu);
        SearchHandler searchOperation = new SearchHandler(menu, this);
        searchOperation.implementSearch(2);
        filterHandler = new FilterHandler(menu, this);
        filterHandler.implementFilter(2);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public void initMoviesRV(List<Movie> lista) {
        moviesAdapter = new MoviesAdapter(getContext(), lista);
        moviesAdapter.notifyDataSetChanged();
        actionMoviesRV.setAdapter(moviesAdapter);
    }
}