package com.example.cinemhub.ui.categorie;

import android.content.Context;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
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
    private CategorieViewModel categorieViewModel;
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
    private TextView azioneTxt;
    private TextView fantasyTxt;
    private TextView animationTxt;
    private TextView commedieTxt;
    private TextView romanceTxt;
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


        categorieViewModel =
                new ViewModelProvider(this).get(CategorieViewModel.class);

        categorieViewModel.getAzione().observe(getViewLifecycleOwner(), new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> moviesSet) {
                if(moviesSet == null)
                    Log.d(TAG, "moviesSet nullo");
                else
                    Log.d(TAG, ""+moviesSet.size());
                initAction(moviesSet);
                azioneAdapter.notifyDataSetChanged();
            }
        });

        categorieViewModel.getFantasy().observe(getViewLifecycleOwner(), new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> moviesSet) {
                if(moviesSet == null)
                    Log.d(TAG, "moviesSet nullo");
                else
                    Log.d(TAG, ""+moviesSet.size());
                initFantasy(moviesSet);
                fantasyAdapter.notifyDataSetChanged();
            }
        });

        categorieViewModel.getAnimation().observe(getViewLifecycleOwner(), new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> moviesSet) {
                if(moviesSet == null)
                    Log.d(TAG, "moviesSet nullo");
                else
                    Log.d(TAG, ""+moviesSet.size());
                initAnimation(moviesSet);
                animationAdapter.notifyDataSetChanged();
            }
        });

        categorieViewModel.getCommedie().observe(getViewLifecycleOwner(), new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> moviesSet) {
                if(moviesSet == null)
                    Log.d(TAG, "moviesSet nullo");
                else
                    Log.d(TAG, ""+moviesSet.size());
                initCommedie(moviesSet);
                commedieAdapter.notifyDataSetChanged();
            }
        });

        categorieViewModel.getRomance().observe(getViewLifecycleOwner(), new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> moviesSet) {
                if(moviesSet == null)
                    Log.d(TAG, "moviesSet nullo");
                else
                    Log.d(TAG, ""+moviesSet.size());
                initRomance(moviesSet);
                romanceAdapter.notifyDataSetChanged();
            }
        });
        initImage();
        setHasOptionsMenu(true);

        return root;
    }

    public void initAction(List<Movie>set){
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

    public void initFantasy (List<Movie>set){
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

    public void initAnimation (List<Movie>set){
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

    public void initCommedie (List<Movie>set){
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

    public void initRomance (List<Movie>set){
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

    public void initImage() {
        actionBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategorieFragmentDirections.GoToGenereAction action = CategorieFragmentDirections.goToGenereAction(0);
                action.setGenere(Constants.ACTION);
                Navigation.findNavController(v).navigate(action);
                }
            });

        fantasyBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategorieFragmentDirections.GoToGenereAction action = CategorieFragmentDirections.goToGenereAction(0);
                action.setGenere(Constants.FANTASY);
                Navigation.findNavController(v).navigate(action);
            }
        });

        animationBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategorieFragmentDirections.GoToGenereAction action = CategorieFragmentDirections.goToGenereAction(0);
                action.setGenere(Constants.ANIMATION);
                Navigation.findNavController(v).navigate(action);
            }
        });

        comedyBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategorieFragmentDirections.GoToGenereAction action = CategorieFragmentDirections.goToGenereAction(0);
                action.setGenere(Constants.COMEDY);
                Navigation.findNavController(v).navigate(action);
            }
        });

        romanceBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategorieFragmentDirections.GoToGenereAction action = CategorieFragmentDirections.goToGenereAction(0);
                action.setGenere(Constants.ROMANCE);
                Navigation.findNavController(v).navigate(action);
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        SearchHandler searchOperation = new SearchHandler(menu, this);
        searchOperation.implementSearch(1);
        super.onCreateOptionsMenu(menu, inflater);
    }
}