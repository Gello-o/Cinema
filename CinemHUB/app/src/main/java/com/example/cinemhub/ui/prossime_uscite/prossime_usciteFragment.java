package com.example.cinemhub.ui.prossime_uscite;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.cinemhub.R;

public class prossime_usciteFragment extends Fragment {

    private prossime_usciteViewModel prossime_usciteViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        prossime_usciteViewModel = new ViewModelProvider(this).get(prossime_usciteViewModel.class);
        View root = inflater.inflate(R.layout.fragment_prossime_uscite, container, false);
        final TextView textView = root.findViewById(R.id.text_prossime_uscite);
        prossime_usciteViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
