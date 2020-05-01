package com.example.cinemhub.ui.categorie;

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

import com.example.cinemhub.R;
import com.example.cinemhub.model.Movie;
import com.example.cinemhub.model.Trailer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


public class CategorieFragment extends Fragment {
    private static final String TAG = "CategorieFragment";
    private CategorieViewModel categorieViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        categorieViewModel =
                new ViewModelProvider(this).get(CategorieViewModel.class);
        View root = inflater.inflate(R.layout.fragment_categorie, container, false);
        final TextView textView = root.findViewById(R.id.text_categorie);

        categorieViewModel.getPopolari().observe(getViewLifecycleOwner(), new Observer<HashSet<Trailer>>() {
            @Override
            public void onChanged(@Nullable HashSet<Trailer> trailersSet) {
                if(trailersSet == null)
                    Log.d(TAG, "caricamento trailer fallito");
                List<Trailer> moviesList = new ArrayList<>();
                moviesList.addAll(trailersSet);

                if(moviesList.isEmpty())
                    textView.setText("adesso smadonno");
                else
                    textView.setText(moviesList.get(0).getKey());
            }
        });
        return root;
    }
}
