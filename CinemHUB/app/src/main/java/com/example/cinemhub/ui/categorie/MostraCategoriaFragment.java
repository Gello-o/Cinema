package com.example.cinemhub.ui.categorie;

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
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinemhub.R;
import com.example.cinemhub.adapter.MoviesAdapter;
import com.example.cinemhub.model.Movie;

import java.util.List;

public class MostraCategoriaFragment extends Fragment {
    private int genere;
    private RecyclerView genereRV;
    private MoviesAdapter genereAdapter;
    private MostraCategoriaViewModel mostraCategoriaViewModel;
    private final static String TAG = "MostraCategoriaFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_genere, container, false);

        genereRV = root.findViewById(R.id.recycler_genere);

       genere = MostraCategoriaFragmentArgs.fromBundle(getArguments()).getGenere();

        mostraCategoriaViewModel =
                new ViewModelProvider(this).get(MostraCategoriaViewModel.class);

        mostraCategoriaViewModel.getGenere(genere).observe(getViewLifecycleOwner(), new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> moviesSet) {
                if(moviesSet == null)
                    Log.d(TAG, "moviesSet nullo");
                else
                    Log.d(TAG, ""+moviesSet.size());
                initMoviesRV(moviesSet);
                genereAdapter.notifyDataSetChanged();
            }
        });

        setHasOptionsMenu(false);

        return root;
    }

    public void initMoviesRV(List<Movie> movies){
        genereAdapter = new MoviesAdapter(getActivity(), movies);

        RecyclerView.LayoutManager layoutManager;
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            layoutManager = new GridLayoutManager(getActivity(), 3);
        else
            layoutManager = new GridLayoutManager(getActivity(), 4);
        genereRV.setLayoutManager(layoutManager);
        genereRV.setAdapter(genereAdapter);
        genereRV.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onStop() {
        super.onStop();
        mostraCategoriaViewModel.stopRepeatingTask();
        mostraCategoriaViewModel.resetIndex();
    }

    @Override
    public void onResume() {
        super.onResume();
        mostraCategoriaViewModel.setRepeatingAsyncTask();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mostraCategoriaViewModel.stopRepeatingTask();
        mostraCategoriaViewModel.resetIndex();

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main2, menu);
        //qua ci sono solo filtri
        super.onCreateOptionsMenu(menu, inflater);
    }
}