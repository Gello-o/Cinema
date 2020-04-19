package com.example.cinemhub.ui.piu_visti;

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
import androidx.lifecycle.ViewModelProviders;

import com.example.cinemhub.R;
import com.example.cinemhub.ui.categorie.CategorieViewModel;

public class piu_vistiFragment extends Fragment {

    private piu_vistiViewModel piu_vistiViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        piu_vistiViewModel = new ViewModelProvider(this).get(piu_vistiViewModel.class);
        View root = inflater.inflate(R.layout.fragment_piu_visti, container, false);
        final TextView textView = root.findViewById(R.id.text_piu_visti);
        piu_vistiViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
