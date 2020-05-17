package com.example.cinemhub.ui.preferiti;

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
import com.example.cinemhub.ui.piu_visti.PiuVistiViewModel;

import java.util.List;

public class PreferitiFragment extends Fragment {

    private static final String TAG = "PreferitiFragment";
    private PreferitiViewModel preferitiViewModel;
    MoviesAdapter adapter;
    RecyclerView preferitiRV;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        preferitiViewModel =
                new ViewModelProvider(this).get(PreferitiViewModel.class);
        View root = inflater.inflate(R.layout.fragment_preferiti, container, false);

        root.findViewById(R.id.recycler_view_preferiti);
        
        preferitiViewModel.queryRoom().observe(getViewLifecycleOwner(), new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> set) {
                initMoviesRV(set);
                adapter.notifyDataSetChanged();
            }
        });
        return root;
    }

    public void initMoviesRV (List<Movie> lista){
        adapter = new MoviesAdapter(getActivity(), lista);
        if(adapter == null)
            Log.d(TAG, "adapter null");
        else {
            if (adapter.getMovieList() == null)
                Log.d(TAG, "lista null");
            if (adapter.getContext() == null)
                Log.d(TAG, "contesto null");
        }
        RecyclerView.LayoutManager layoutManager;
        if(getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            layoutManager = new GridLayoutManager(getActivity(), 3);
        else
            layoutManager = new GridLayoutManager(getActivity(), 3);
        preferitiRV.setLayoutManager(layoutManager);
        preferitiRV.setAdapter(adapter);
        preferitiRV.setItemAnimator(new DefaultItemAnimator());

    }
}

