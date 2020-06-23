package com.example.cinemhub.ui.categorie;

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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_categorie, container, false);

        azioneRV = root.findViewById(R.id.recycler_azione);
        fantasyRV = root.findViewById(R.id.recycler_fantasy);
        animationRV = root.findViewById(R.id.recycler_animation);
        commedieRV = root.findViewById(R.id.recycler_commedie);
        romanceRV = root.findViewById(R.id.recycler_romance);
        azioneTxt = root.findViewById(R.id.azione_txt);
        fantasyTxt = root.findViewById(R.id.fantasy_txt);
        animationTxt = root.findViewById(R.id.animation_txt);
        commedieTxt = root.findViewById(R.id.commedie_txt);
        romanceTxt = root.findViewById(R.id.romance_txt);

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

        initTexts();
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

    public void initTexts(){
        SpannableString ss1 = new SpannableString(azioneTxt.getText());
        SpannableString ss2 = new SpannableString(fantasyTxt.getText());
        SpannableString ss3 = new SpannableString(animationTxt.getText());
        SpannableString ss4 = new SpannableString(commedieTxt.getText());
        SpannableString ss5 = new SpannableString(romanceTxt.getText());

        CategorieFragmentDirections.GoToGenereAction action = CategorieFragmentDirections.goToGenereAction(0);

        ClickableSpan azioneClickableSpan = new ClickableSpan(){

            @Override
            public void onClick(@NonNull View widget) {
                Log.d(TAG, "cliccato");
                action.setGenere(28);
                Navigation.findNavController(widget).navigate(action);
            }
        };


        ClickableSpan fantasyClickableSpan = new ClickableSpan(){

            @Override
            public void onClick(@NonNull View widget) {
                Log.d(TAG, "cliccato");
                action.setGenere(14);
                Navigation.findNavController(widget).navigate(action);
            }
        };

        ClickableSpan animationClickableSpan = new ClickableSpan(){


            @Override
            public void onClick(@NonNull View widget) {
                Log.d(TAG, "cliccato");
                action.setGenere(16);
                Navigation.findNavController(widget).navigate(action);
            }
        };

        ClickableSpan commedieClickableSpan = new ClickableSpan(){


            @Override
            public void onClick(@NonNull View widget) {
                Log.d(TAG, "cliccato");
                action.setGenere(35);
                Navigation.findNavController(widget).navigate(action);
            }
        };

        ClickableSpan romanceClickableSpan = new ClickableSpan(){


            @Override
            public void onClick(@NonNull View widget) {
                Log.d(TAG, "cliccato");
                action.setGenere(10749);
                Navigation.findNavController(widget).navigate(action);
            }
        };

        ss1.setSpan(azioneClickableSpan, 0, 6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss2.setSpan(fantasyClickableSpan, 0, 7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss3.setSpan(animationClickableSpan, 0, 9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss4.setSpan(commedieClickableSpan, 0, 6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss5.setSpan(romanceClickableSpan, 0, 7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        azioneTxt.setText(ss1);
        fantasyTxt.setText(ss2);
        animationTxt.setText(ss3);
        commedieTxt.setText(ss4);
        romanceTxt.setText(ss5);

        azioneTxt.setMovementMethod(LinkMovementMethod.getInstance());
        fantasyTxt.setMovementMethod(LinkMovementMethod.getInstance());
        animationTxt.setMovementMethod(LinkMovementMethod.getInstance());
        commedieTxt.setMovementMethod(LinkMovementMethod.getInstance());
        romanceTxt.setMovementMethod(LinkMovementMethod.getInstance());

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        SearchHandler searchOperation = new SearchHandler(menu, this);
        searchOperation.implementSearch(1);
        super.onCreateOptionsMenu(menu, inflater);
    }
}