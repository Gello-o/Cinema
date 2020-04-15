package com.example.cinemhub.ui.nuovi_arrivi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.cinemhub.R;
import com.example.cinemhub.ui.categorie.CategorieViewModel;

public class nuovi_arriviFragment extends Fragment {

    private nuovi_arriviViewModel nuovi_arriviViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        nuovi_arriviViewModel =
                ViewModelProviders.of(this).get(nuovi_arriviViewModel.class);
        View root = inflater.inflate(R.layout.fragment_nuovi_arrivi, container, false);
        final TextView textView = root.findViewById(R.id.text_nuovi_arrivi);
        nuovi_arriviViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
