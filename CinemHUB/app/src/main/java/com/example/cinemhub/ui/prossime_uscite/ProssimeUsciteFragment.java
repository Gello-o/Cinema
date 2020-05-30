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
import androidx.lifecycle.ViewModelProvider;

import com.example.cinemhub.R;

public class ProssimeUsciteFragment extends Fragment {

    private ProssimeUsciteViewModel prossimeUsciteViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        prossimeUsciteViewModel =
                new ViewModelProvider(this).get(ProssimeUsciteViewModel.class);
        View root = inflater.inflate(R.layout.fragment_prossime_uscite, container, false);
        final TextView textView = root.findViewById(R.id.text_prossime_uscite);
        prossimeUsciteViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
