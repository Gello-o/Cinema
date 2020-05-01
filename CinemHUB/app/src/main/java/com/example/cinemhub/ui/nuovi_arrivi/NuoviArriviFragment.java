package com.example.cinemhub.ui.nuovi_arrivi;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class NuoviArriviFragment extends Fragment {
    private static final String TAG = "NuoviArriviFragment";
    private NuoviArriviViewModel nuoviArriviViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        nuoviArriviViewModel =
                new ViewModelProvider(this).get(NuoviArriviViewModel.class);
        View root = inflater.inflate(R.layout.fragment_nuovi_arrivi, container, false);
        final TextView textView = root.findViewById(R.id.text_nuovi_arrivi);
        nuoviArriviViewModel.getText().observe(getViewLifecycleOwner(), new Observer<HashSet<Movie>>() {
            @Override
            public void onChanged(@Nullable HashSet<Movie> set) {

            }
        });
        return root;
    }
}
