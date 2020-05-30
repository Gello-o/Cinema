package com.example.cinemhub.ui.categorie;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.CharacterStyle;
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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinemhub.MainActivity;
import com.example.cinemhub.R;
import com.example.cinemhub.adapter.MoviesAdapter;
import com.example.cinemhub.model.Movie;
import com.example.cinemhub.model.Trailer;
import com.example.cinemhub.ricerca.SearchHandler;
import com.example.cinemhub.ui.home.HomeFragment;
import com.example.cinemhub.ui.home.HomeViewModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Timer;

public class CategorieFragment extends Fragment {
    private static final String TAG = "CategorieFragment";
    private CategorieViewModel categorieViewModel;
    private RecyclerView azioneRV;
    private RecyclerView avventuraRV;
    private RecyclerView crimineRV;
    private MoviesAdapter azioneAdapter;
    private MoviesAdapter avventuraAdapter;
    private MoviesAdapter crimineAdapter;
    private TextView azioneTxt;
    private TextView avventuraTxt;
    private TextView crimineTxt;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_categorie, container, false);

        azioneRV = root.findViewById(R.id.recycler_azione);
        avventuraRV = root.findViewById(R.id.recycler_avventura);
        crimineRV = root.findViewById(R.id.recycler_crimine);
        azioneTxt = root.findViewById(R.id.azione_txt);
        avventuraTxt = root.findViewById(R.id.avventura_txt);
        crimineTxt = root.findViewById(R.id.crimine_txt);

        categorieViewModel =
                new ViewModelProvider(this).get(CategorieViewModel.class);

        categorieViewModel.getAzione().observe(getViewLifecycleOwner(), new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> moviesSet) {
                if(moviesSet == null)
                    Log.d(TAG, "moviesSet nullo");
                else
                    Log.d(TAG, ""+moviesSet.size());
                initPopularRV(moviesSet);
                azioneAdapter.notifyDataSetChanged();
            }
        });

        categorieViewModel.getAvventura().observe(getViewLifecycleOwner(), new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> moviesSet) {
                if(moviesSet == null)
                    Log.d(TAG, "moviesSet nullo");
                else
                    Log.d(TAG, ""+moviesSet.size());
                initTopRatedRV(moviesSet);
                avventuraAdapter.notifyDataSetChanged();
            }
        });

        categorieViewModel.getCrime().observe(getViewLifecycleOwner(), new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> moviesSet) {
                if(moviesSet == null)
                    Log.d(TAG, "moviesSet nullo");
                else
                    Log.d(TAG, ""+moviesSet.size());
                initProssimeUscite(moviesSet);
                crimineAdapter.notifyDataSetChanged();
            }
        });

        initTexts();
        setHasOptionsMenu(true);

        return root;
    }

    public void initPopularRV (List<Movie>set){
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

    public void initTopRatedRV (List<Movie>set){
        if(set == null)
            Log.d(TAG, "SET top NULL");
        else if(set.isEmpty())
            Log.d(TAG, "SET top vuoto");
        avventuraAdapter = new MoviesAdapter(getContext(), set);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        avventuraRV.setLayoutManager(layoutManager);
        avventuraRV.setAdapter(avventuraAdapter);
        avventuraRV.setItemAnimator(new DefaultItemAnimator());
    }

    public void initProssimeUscite (List<Movie>set){
        if(set == null)
            Log.d(TAG, "SET Prossime NULL");
        else if(set.isEmpty())
            Log.d(TAG, "SET Prossime vuoto");
        crimineAdapter = new MoviesAdapter(getContext(), set);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        crimineRV.setLayoutManager(layoutManager);
        crimineRV.setAdapter(crimineAdapter);
        crimineRV.setItemAnimator(new DefaultItemAnimator());
    }

    public void initTexts(){
        SpannableString ss1 = new SpannableString(azioneTxt.getText());
        SpannableString ss2 = new SpannableString(avventuraTxt.getText());
        SpannableString ss3 = new SpannableString(crimineTxt.getText());

       CategorieFragmentDirections.GoToGenereAction action = CategorieFragmentDirections.goToGenereAction(0);

        ClickableSpan azioneClickableSpan = new ClickableSpan(){

            @Override
            public void onClick(@NonNull View widget) {
                Log.d(TAG, "cliccato");
               action.setGenere(28);
               Navigation.findNavController(widget).navigate(action);
            }
        };


        ClickableSpan avventuraClickableSpan = new ClickableSpan(){

            @Override
            public void onClick(@NonNull View widget) {
                Log.d(TAG, "cliccato");
                action.setGenere(12);
                Navigation.findNavController(widget).navigate(action);
            }
        };

        ClickableSpan crimineClickableSpan = new ClickableSpan(){


            @Override
            public void onClick(@NonNull View widget) {
                Log.d(TAG, "cliccato");
                action.setGenere(80);
                Navigation.findNavController(widget).navigate(action);


            }
        };

        ss1.setSpan(azioneClickableSpan, 0, 6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss2.setSpan(avventuraClickableSpan, 0, 8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss3.setSpan(crimineClickableSpan, 0, 7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        azioneTxt.setText(ss1);
        avventuraTxt.setText(ss2);
        crimineTxt.setText(ss3);

        azioneTxt.setMovementMethod(LinkMovementMethod.getInstance());
        avventuraTxt.setMovementMethod(LinkMovementMethod.getInstance());
        crimineTxt.setMovementMethod(LinkMovementMethod.getInstance());


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        SearchHandler searchOperation = new SearchHandler(menu, this);
        searchOperation.implementSearch();
        super.onCreateOptionsMenu(menu, inflater);
    }
}