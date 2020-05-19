package com.example.cinemhub.ui.nuovi_arrivi;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinemhub.MainActivity;
import com.example.cinemhub.R;
import com.example.cinemhub.adapter.MoviesAdapter;
import com.example.cinemhub.model.Movie;
import com.example.cinemhub.ui.home.HomeFragment;
import com.example.cinemhub.ui.home.HomeViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.List;
import java.util.TimerTask;

public class NuoviArriviFragment extends Fragment {
    private static final String TAG = "NuoviArriviFragment";
    private NuoviArriviViewModel nuoviArriviViewModel;
    private MoviesAdapter moviesAdapter;
    RecyclerView prossimeUsciteRV;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_nuovi_arrivi, container, false);

        prossimeUsciteRV = root.findViewById(R.id.recycler_view_nuovi_arrivi);

        nuoviArriviViewModel =
                new ViewModelProvider(this).get(NuoviArriviViewModel.class);

        nuoviArriviViewModel.getProssimeUscite().observe(getViewLifecycleOwner(), new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> set) {
                initMoviesRV(set);
                moviesAdapter.notifyDataSetChanged();
            }
        });

        return root;
    }

    @Override
    public void onStop() {
        super.onStop();
        nuoviArriviViewModel.stopRepeatingTask();
        nuoviArriviViewModel.resetIndex();
    }

    @Override
    public void onResume() {
        super.onResume();
        nuoviArriviViewModel.setRepeatingAsyncTask();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        nuoviArriviViewModel.stopRepeatingTask();
        nuoviArriviViewModel.resetIndex();
    }


    public void initMoviesRV (List<Movie> lista){

        moviesAdapter = new MoviesAdapter(getActivity(), lista);
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
        prossimeUsciteRV.setLayoutManager(layoutManager);
        prossimeUsciteRV.setAdapter(moviesAdapter);
        prossimeUsciteRV.setItemAnimator(new DefaultItemAnimator());

    }

}
