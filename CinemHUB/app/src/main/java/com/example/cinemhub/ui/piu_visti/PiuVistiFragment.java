package com.example.cinemhub.ui.piu_visti;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.example.cinemhub.R;
import com.example.cinemhub.ricerca.SearchHandler;

public class PiuVistiFragment extends Fragment {

    private PiuVistiViewModel piuVistiViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        piuVistiViewModel =
                new ViewModelProvider(this).get(PiuVistiViewModel.class);
        View root = inflater.inflate(R.layout.fragment_piu_visti, container, false);
        final TextView textView = root.findViewById(R.id.text_piu_visti);
        piuVistiViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(getContext().toString());
            }
        });

        setHasOptionsMenu(true);

        return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main3, menu);
        SearchHandler searchOperation = new SearchHandler(menu, this);
        searchOperation.implementSearch();
        super.onCreateOptionsMenu(menu, inflater);
    }
}
