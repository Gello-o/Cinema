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

import com.example.cinemhub.JsonPlaceHolderApi;
import com.example.cinemhub.R;
import com.example.cinemhub.tmdb;
import com.example.cinemhub.tmdb_util;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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











        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);


        Call<List<tmdb>> call = jsonPlaceHolderApi.getTMDB();


        call.enqueue(new Callback<List<tmdb>>() {
            @Override
            public void onResponse(Call<List<tmdb>> call, Response<List<tmdb>> response) {

                if(!response.isSuccessful()) {
                    textView.setText("Code: " + response.code());
                    return;
                }
                List<tmdb> tmdb = response.body();

                for(tmdb tmdb1 : tmdb) {
                    String content = "";
                    content += "ID: " + tmdb1.getId() + "\n";
                    content += "Title: " + tmdb1.getTitle() + "\n";
                    content += "Vote Avarage: " + tmdb1.getVoteAverage() + "\n";
                    content += "Original Language: " + tmdb1.getOriginalLanguage() + "\n\n";


                    textView.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<tmdb>> call, Throwable t) {
                textView.setText(t.getMessage());

            }
        });

















        return root;
    }
}
