package com.example.cinemhub.ui.categorie;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.cinemhub.R;
import com.example.cinemhub.model.Movie;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


public class CategorieFragment extends Fragment {

    private CategorieViewModel categorieViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        categorieViewModel =
                new ViewModelProvider(this).get(CategorieViewModel.class);
        View root = inflater.inflate(R.layout.fragment_categorie, container, false);
        final TextView textView = root.findViewById(R.id.text_categorie);

        categorieViewModel.getPopolari().observe(getViewLifecycleOwner(), new Observer<HashSet<Movie>>() {
            @Override
            public void onChanged(@Nullable HashSet<Movie> moviesSet) {
                List<Movie> moviesList = new ArrayList<>();
                moviesList.addAll(moviesSet);

                if(moviesList.isEmpty())
                    textView.setText("adesso smadonno");
                else
                    textView.setText(moviesList.get(0).getOriginal_title());
            }
        });
        return root;
    }
}
