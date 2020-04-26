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
import androidx.lifecycle.ViewModelProvider;

import com.example.cinemhub.R;

public class NuoviArriviFragment extends Fragment {

    private NuoviArriviViewModel nuoviArriviViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        nuoviArriviViewModel =
                new ViewModelProvider(this).get(NuoviArriviViewModel.class);
        View root = inflater.inflate(R.layout.fragment_nuovi_arrivi, container, false);
        final TextView textView = root.findViewById(R.id.text_nuovi_arrivi);
        nuoviArriviViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
