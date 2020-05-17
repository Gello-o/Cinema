package com.example.cinemhub.ui.nuovi_arrivi;

import android.app.ProgressDialog;
import android.os.Build;
import android.text.method.MovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cinemhub.MainActivity;
import com.example.cinemhub.model.Movie;
import com.example.cinemhub.model.MoviesRepository;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NuoviArriviViewModel extends ViewModel {
    private static final String TAG = "NuoviArriviViewModel";
    private MutableLiveData<List<Movie>> tutti;
    private MoviesRepository db;

    public MutableLiveData<List<Movie>> getProssimeUscite() {
        if(tutti == null) {
            db = MoviesRepository.getInstance();
            tutti = new MutableLiveData<>();
            for(int i = 1; i < 6; i++){
                db.getMovies("upcoming", i, tutti);
            }
        }
        return tutti;
    }
}