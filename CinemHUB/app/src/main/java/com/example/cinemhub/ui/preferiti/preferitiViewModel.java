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

public class PreferitiViewModel extends ViewModel {
    private static final String TAG = "PreferitiViewModel";

    private MutableLiveData<List<Movie>> mText;

    List<Favorite> line;
    Movie movie;
    List<Movie> movieList;
    int id;
    String title, userRating, posterPath, plotSynopsis;

    public MutableLiveData<List<Movie>> queryRoom() {
        line = MainActivity.dbStructure.dbInterface().getFavorite();
        movieList= new ArrayList<>();


        if(line == null)
            Log.d(TAG, "line uguale null");

        if (mText == null) {
            mText = new MutableLiveData<>();
        }

        if(!line.isEmpty()) {
             for (Favorite fav : line) {
                id = fav.getMovie_id();
                title = fav.getTitle();
                userRating = fav.getUserRating();
                posterPath = fav.getPosterPath();
                plotSynopsis = fav.getPlotSynopsys();

                //creare in Movie un costruttore
                movie.setId(id);
                movie.setTitle(title);
                movie.setVoteAverage(Double.parseDouble(userRating));
                movie.setPosterPath(posterPath);
                movie.setOverview(plotSynopsis);

                movieList.add(movie);

                Log.d(TAG, "Database: " +
                        "ID: " + id +
                        "id movie: " + movie.getTitle());
    }
}
        mText.setValue(movieList);
        return (mText);
    }
}
