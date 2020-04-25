package com.example.cinemhub.ui.add_list;

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

public class add_listFragment extends Fragment {

    private add_listViewModel add_listViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        add_listViewModel =
                new ViewModelProvider(this).get(add_listViewModel.class);
        View root = inflater.inflate(R.layout.fragment_add_list, container, false);
        final TextView textView = root.findViewById(R.id.text_add_list);
        add_listViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
