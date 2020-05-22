package com.example.cinemhub.ui.preferiti;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cinemhub.MainActivity;
import com.example.cinemhub.model.Movie;
import com.example.cinemhub.room.DbStructure;
import com.example.cinemhub.room.Favorite;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

// tutto da fixare

public class PreferitiViewModel extends ViewModel {
    private static final String TAG = "PreferitiViewModel";

    private MutableLiveData<List<Movie>> mText;
    List<Favorite> line;
    Movie movie;
    List<Movie> movieList, checkList;
    int id;
    String title, userRating, posterPath, plotSynopsis;


    public MutableLiveData<List<Movie>> queryRoom() {
        line = MainActivity.dbStructure.dbInterface().getFavorite();
        movie = new Movie();
        movieList = new ArrayList<>();
        //checkliste è solo per il debug poi si puo cancellare
        checkList = new ArrayList<>();

        if (mText == null) {
            mText = new MutableLiveData<>();
        }

        if (line == null) {
            Log.d(TAG, "line uguale null");
            return (mText);
        }
        else if (!line.isEmpty()) {
                for (Favorite favorite : line) {
                    id = favorite.getMovie_id();
                    title = favorite.getTitle();
                    userRating = favorite.getUserRating();
                    posterPath = favorite.getPosterPath();
                    plotSynopsis = favorite.getPlotSynopsys();

                    //creare in Movie un costruttore
                    movie.setId(id);
                    movie.setTitle(title);
                    movie.setVoteAverage(Double.parseDouble(userRating));
                    movie.setPosterPath(posterPath);
                    movie.setOverview(plotSynopsis);

                    movieList.add(movie);

                    Log.d(TAG, "ID da lista: " + id +
                            " Title da movie: " + movie.getTitle());
                }
            }
            mText.setValue(movieList);
            checkList = mText.getValue();
            Log.d(TAG, "dimensione mText: " + checkList.size());
            return (mText);
        }
    }



