package com.example.cinemhub.ui.add_list;

import android.content.res.Configuration;
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
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.cinemhub.R;
import com.example.cinemhub.adapter.MoviesAdapter;
import com.example.cinemhub.model.Movie;
import java.util.List;

public class AddListFragment extends Fragment {

    private static final String TAG = "AddListFragment";
    private AddListViewModel addListViewModel;
    private RecyclerView actionMoviesRV;
    private MoviesAdapter moviesAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        addListViewModel =
                new ViewModelProvider(this).get(AddListViewModel.class);

        View root = inflater.inflate(R.layout.fragment_add_list, container, false);

        actionMoviesRV = root.findViewById(R.id.recycler_view_add_list);

        addListViewModel.getText().observe(getViewLifecycleOwner(), new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> s) {
                if(s == null)
                    Log.d(TAG, "caricamento fallito");
                initMovieRV(s);
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