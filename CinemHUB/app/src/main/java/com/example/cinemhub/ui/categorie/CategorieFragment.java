package com.example.cinemhub.ui.categorie;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinemhub.R;
import com.example.cinemhub.adapter.MoviesAdapter;
import com.example.cinemhub.model.Movie;
import com.example.cinemhub.menu_items.ricerca.SearchHandler;
import com.example.cinemhub.utils.Constants;

import java.util.List;

public class CategorieFragment extends Fragment {
    private static final String TAG = "CategorieFragment";
    private RecyclerView azioneRV;
    private RecyclerView fantasyRV;
    private RecyclerView animationRV;
    private RecyclerView commedieRV;
    private RecyclerView romanceRV;
    private MoviesAdapter azioneAdapter;
    private MoviesAdapter fantasyAdapter;
    private MoviesAdapter animationAdapter;
    private MoviesAdapter commedieAdapter;
    private MoviesAdapter romanceAdapter;
    private ImageView actionBanner;
    private ImageView fantasyBanner;
    private ImageView animationBanner;
    private ImageView comedyBanner;
    private ImageView romanceBanner;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_categorie, container, false);

        azioneRV = root.findViewById(R.id.recycler_azione);
        fantasyRV = root.findViewById(R.id.recycler_fantasy);
        animationRV = root.findViewById(R.id.recycler_animation);
        commedieRV = root.findViewById(R.id.recycler_comedy);
        romanceRV = root.findViewById(R.id.recycler_romance);

        actionBanner = root.findViewById(R.id.image_action);
        fantasyBanner = root.findViewById(R.id.image_fantasy);
        animationBanner = root.findViewById(R.id.image_animation);
        comedyBanner = root.findViewById(R.id.image_comedy);
        romanceBanner = root.findViewById(R.id.image_romance);


        CategorieViewModel categorieViewModel = new ViewModelProvider(this).get(CategorieViewModel.class);

        categorieViewModel.getAzione().observe(getViewLifecycleOwner(), moviesSet -> {
            if(moviesSet == null)
                Log.d(TAG, "moviesSet nullo");
            else
                Log.d(TAG, ""+moviesSet.size());
            initAction(moviesSet);
            azioneAdapter.notifyDataSetChanged();
        });

        categorieViewModel.getFantasy().observe(getViewLifecycleOwner(), (List<Movie> moviesSet) -> {
            if(moviesSet == null)
                Log.d(TAG, "moviesSet nullo");
            else
                Log.d(TAG, ""+moviesSet.size());
            initFantasy(moviesSet);
            fantasyAdapter.notifyDataSetChanged();
        });

        categorieViewModel.getAnimation().observe(getViewLifecycleOwner(), moviesSet -> {
            if(moviesSet == null)
                Log.d(TAG, "moviesSet nullo");
            else
                Log.d(TAG, ""+moviesSet.size());
            initAnimation(moviesSet);
            animationAdapter.notifyDataSetChanged();
        });

        categorieViewModel.getCommedie().observe(getViewLifecycleOwner(), moviesSet -> {
            if(moviesSet == null)
                Log.d(TAG, "moviesSet nullo");
            else
                Log.d(TAG, ""+moviesSet.size());
            initCommedie(moviesSet);
            commedieAdapter.notifyDataSetChanged();
        });

        categorieViewModel.getRomance().observe(getViewLifecycleOwner(), moviesSet -> {
            if(moviesSet == null)
                Log.d(TAG, "moviesSet nullo");
            else
                Log.d(TAG, ""+moviesSet.size());
            initRomance(moviesSet);
            romanceAdapter.notifyDataSetChanged();
        });
        initImage();
        setHasOptionsMenu(true);

        return root;
    }

    private void initAction(List<Movie> set){
        if(set == null)
            Log.d(TAG, "SET POPOLARI NULL");
        else if(set.isEmpty())
            Log.d(TAG, "SET POPOLARI vuoto");
        azioneAdapter = new MoviesAdapter(getContext(), set);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        azioneRV.setLayoutManager(layoutManager);
        azioneRV.setAdapter(azioneAdapter);
        azioneRV.setItemAnimator(new DefaultItemAnimator());
    }

    private void initFantasy(List<Movie> set){
        if(set == null)
            Log.d(TAG, "SET top NULL");
        else if(set.isEmpty())
            Log.d(TAG, "SET top vuoto");
        fantasyAdapter = new MoviesAdapter(getContext(), set);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        fantasyRV.setLayoutManager(layoutManager);
        fantasyRV.setAdapter(fantasyAdapter);
        fantasyRV.setItemAnimator(new DefaultItemAnimator());
    }

    private void initAnimation(List<Movie> set){
        if(set == null)
            Log.d(TAG, "SET Prossime NULL");
        else if(set.isEmpty())
            Log.d(TAG, "SET Prossime vuoto");
        animationAdapter = new MoviesAdapter(getContext(), set);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        animationRV.setLayoutManager(layoutManager);
        animationRV.setAdapter(animationAdapter);
        animationRV.setItemAnimator(new DefaultItemAnimator());
    }

    private void initCommedie(List<Movie> set){
        if(set == null)
            Log.d(TAG, "SET Prossime NULL");
        else if(set.isEmpty())
            Log.d(TAG, "SET Prossime vuoto");
        commedieAdapter = new MoviesAdapter(getContext(), set);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        commedieRV.setLayoutManager(layoutManager);
        commedieRV.setAdapter(commedieAdapter);
        commedieRV.setItemAnimator(new DefaultItemAnimator());
    }

    private void initRomance(List<Movie> set){
        if(set == null)
            Log.d(TAG, "SET Prossime NULL");
        else if(set.isEmpty())
            Log.d(TAG, "SET Prossime vuoto");
        romanceAdapter = new MoviesAdapter(getContext(), set);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        romanceRV.setLayoutManager(layoutManager);
        romanceRV.setAdapter(romanceAdapter);
        romanceRV.setItemAnimator(new DefaultItemAnimator());
    }

    private void initImage() {
        actionBanner.setOnClickListener(v -> {
            CategorieFragmentDirections.GoToGenereAction action = CategorieFragmentDirections.goToGenereAction(0);
            action.setGenere(Constants.ACTION);
            Navigation.findNavController(v).navigate(action);
            });

        fantasyBanner.setOnClickListener(v -> {
            CategorieFragmentDirections.GoToGenereAction action = CategorieFragmentDirections.goToGenereAction(0);
            action.setGenere(Constants.FANTASY);
            Navigation.findNavController(v).navigate(action);
        });

        animationBanner.setOnClickListener(v -> {
            CategorieFragmentDirections.GoToGenereAction action = CategorieFragmentDirections.goToGenereAction(0);
            action.setGenere(Constants.ANIMATION);
            Navigation.findNavController(v).navigate(action);
        });

        comedyBanner.setOnClickListener(v -> {
            CategorieFragmentDirections.GoToGenereAction action = CategorieFragmentDirections.goToGenereAction(0);
            action.setGenere(Constants.COMEDY);
            Navigation.findNavController(v).navigate(action);
        });

        romanceBanner.setOnClickListener(v -> {
            CategorieFragmentDirections.GoToGenereAction action = CategorieFragmentDirections.goToGenereAction(0);
            action.setGenere(Constants.ROMANCE);
            Navigation.findNavController(v).navigate(action);
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        SearchHandler searchOperation = new SearchHandler(menu, this);
        searchOperation.implementSearch(1);
        super.onCreateOptionsMenu(menu, inflater);
    }
}