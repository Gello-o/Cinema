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

public class CategorieFragment extends Fragment {

    private CategorieViewModel categorieViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        categorieViewModel =
                new ViewModelProvider(this).get(CategorieViewModel.class);
        View root = inflater.inflate(R.layout.fragment_categorie, container, false);
        final TextView textView = root.findViewById(R.id.text_categorie);
        categorieViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
