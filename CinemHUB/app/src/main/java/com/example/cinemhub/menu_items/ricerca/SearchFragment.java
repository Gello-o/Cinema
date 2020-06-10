package com.example.cinemhub.menu_items.ricerca;

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
import com.example.cinemhub.menu_items.filtri.FilterHandler;
import com.example.cinemhub.model.Movie;
import java.util.List;


public class SearchFragment extends Fragment {
    private static final String TAG = "SearchFragment";
    private SearchViewModel searchViewModel;
    private RecyclerView searchMoviesRV;
    private MoviesAdapter moviesAdapter;
    private String query;
    FilterHandler filterOperation;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        searchViewModel =
                new ViewModelProvider(this).get(SearchViewModel.class);

        View root = inflater.inflate(R.layout.search, container, false);

        query = SearchFragmentArgs.fromBundle(getArguments()).getQuery();

        searchMoviesRV = root.findViewById(R.id.recycler_view_ricerca);

        searchViewModel.doSearch(query).observe(getViewLifecycleOwner(), new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                if(movies == null)
                    Log.d(TAG, "caricamento fallito");
                initMovieRV(movies);
                if (filterOperation != null) {
                    filterOperation.setMovie(movies);
                    //initFilterObserver();
                } else
                    Log.d(TAG, "FilterOperationNull");
                moviesAdapter.notifyDataSetChanged();
            }
        });

        setHasOptionsMenu(true);
        return root;
    }

    public void initMovieRV(List<Movie> movies){
        moviesAdapter = new MoviesAdapter(getActivity(), movies);

        RecyclerView.LayoutManager layoutManager;
        if(getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            layoutManager = new GridLayoutManager(getActivity(), 3);
        else
            layoutManager = new GridLayoutManager(getActivity(), 4);
        searchMoviesRV.setLayoutManager(layoutManager);
        searchMoviesRV.setAdapter(moviesAdapter);
        searchMoviesRV.setItemAnimator(new DefaultItemAnimator());

    }

    public void initMovieRV(List<Movie> movies, Fragment fragment) {
        moviesAdapter = new MoviesAdapter(fragment.getActivity(), movies);

        RecyclerView.LayoutManager layoutManager;
        if (getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            layoutManager = new GridLayoutManager(getActivity(), 3);
        else
            layoutManager = new GridLayoutManager(getActivity(), 4);
        searchMoviesRV.setLayoutManager(layoutManager);
        searchMoviesRV.setAdapter(moviesAdapter);
        searchMoviesRV.setItemAnimator(new DefaultItemAnimator());

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main2, menu);
        filterOperation = new FilterHandler(menu, this);
        filterOperation.implementFilter(1);
        super.onCreateOptionsMenu(menu, inflater);
    }

}