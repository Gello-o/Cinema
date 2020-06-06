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
import com.example.cinemhub.menu_items.Refresh;
import com.example.cinemhub.adapter.MoviesAdapter;
import com.example.cinemhub.menu_items.filtri.FilterHandler;
import com.example.cinemhub.model.Movie;
import com.example.cinemhub.menu_items.ricerca.SearchHandler;

import java.util.HashSet;
import java.util.List;

public class AddListFragment extends Fragment {

    private static final String TAG = "AddListFragment";
    private AddListViewModel addListViewModel;
    private RecyclerView actionMoviesRV;
    private MoviesAdapter moviesAdapter;
    FilterHandler filterOperation;
    Refresh refreshOperation;
    private HashSet<Movie> currentList2 = new HashSet<>();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_add_list, container, false);

        actionMoviesRV = root.findViewById(R.id.recycler_view_add_list);

        setHasOptionsMenu(true);
        return root;
    }

    public void initMovieRV(List<Movie> movies) {
        moviesAdapter = new MoviesAdapter(getActivity(), movies);
        moviesAdapter.notifyDataSetChanged();
        actionMoviesRV.setAdapter(moviesAdapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main3, menu);
        SearchHandler searchOperation = new SearchHandler(menu, this);
        filterOperation = new FilterHandler(menu, this);
        searchOperation.implementSearch(2);
        filterOperation.implementFilter(2);

        refreshOperation = new Refresh(menu, this, filterOperation);
        refreshOperation.implementRefresh(2);
        Log.d(TAG, "onCreateOptionsMenu");

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        addListViewModel =
                new ViewModelProvider(this).get(AddListViewModel.class);

        actionMoviesRV.setItemAnimator(new DefaultItemAnimator());

        addListViewModel.getText().observe(getViewLifecycleOwner(), new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> s) {
                Log.d(TAG, "OnChanged");
                if (s == null)
                    Log.d(TAG, "caricamento fallito");
                initMovieRV(s);

                refreshOperation.count();
                currentList2.addAll(s);
                Log.d(TAG, "CurrentListSize: "+currentList2.size());

                if (filterOperation != null) {
                    filterOperation.setMovie(s);
                    Log.d(TAG, "FilterSetMovie");
                }
                else
                    Log.d(TAG, "FilterOperationNull");

                if(refreshOperation != null && refreshOperation.getCount()==addListViewModel.getPage()-1) {
                    refreshOperation.setMovie(currentList2);
                    Log.d(TAG, "RefreshSetMovie");
                }
                else
                    Log.d(TAG, "RefreshOperationNull");

            }
        });

        RecyclerView.LayoutManager layoutManager;
        if (getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            layoutManager = new GridLayoutManager(getActivity(), 3);
        else
            layoutManager = new GridLayoutManager(getActivity(), 4);
        actionMoviesRV.setLayoutManager(layoutManager);

        super.onViewCreated(view, savedInstanceState);
    }
}