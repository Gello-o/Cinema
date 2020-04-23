package com.example.cinemhub.ui.categorie;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.cinemhub.JsonPlaceHolderApi;
import com.example.cinemhub.MainActivity;
import com.example.cinemhub.R;
import com.example.cinemhub.tmdb_util;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CategorieFragment extends Fragment { //Fragment

    private CategorieViewModel categorieViewModel;
    private String BASE_URL = "https://api.themoviedb.org";
    public static int PAGE = 1;
    public static String API_KEY = "740ef79d64b588653371072cdee99a0f";
    public static String LANGUAGE = "en-US";
    public static String CATEGORY = "popular";

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



        //Prova di chiamata all'API
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<tmdb_util> call = jsonPlaceHolderApi.getTMDB(CATEGORY, API_KEY, LANGUAGE, PAGE);

        call.enqueue(new Callback<tmdb_util>() {
            @Override
            public void onResponse(Call<tmdb_util> call, Response<tmdb_util> response) {
                tmdb_util results = response.body();
                List<tmdb_util.tmdb> listofMovies = results.getResults();
                tmdb_util.tmdb first = listofMovies.get(0);

                textView.setText(first.getTitle());
            }

            @Override
            public void onFailure(Call<tmdb_util> call, Throwable t) {
                t.printStackTrace();
            }
        });


        return root;
    }
}
