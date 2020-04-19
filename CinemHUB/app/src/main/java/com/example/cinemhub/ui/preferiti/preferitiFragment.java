package com.example.cinemhub.ui.preferiti;

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
import com.example.cinemhub.ui.piu_visti.piu_vistiViewModel;

public class preferitiFragment extends Fragment {

    private preferitiViewModel preferitiViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        preferitiViewModel = new ViewModelProvider(this).get(preferitiViewModel.class);
        View root = inflater.inflate(R.layout.fragment_preferiti, container, false);
        final TextView textView = root.findViewById(R.id.text_preferiti);
        preferitiViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
