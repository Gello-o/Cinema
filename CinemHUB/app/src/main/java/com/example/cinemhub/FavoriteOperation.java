package com.example.cinemhub;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.cinemhub.model.Favorite;
import com.example.cinemhub.model.FavoriteDB;
import com.example.cinemhub.model.UserRatingDB;
import com.google.android.material.snackbar.Snackbar;
import com.like.LikeButton;
import com.like.OnLikeListener;

import java.util.List;

public class FavoriteOperation {
    private static final String TAG = "FavoriteOperation";
    public Activity activity;
    private Context context;

    private Favorite favorite;
    private List<Favorite> line;
    private LikeButton likeButtonFavorite;

    String thumbnail, movieName, synopsis, rating, release, id, originalMovieName, voteCount, genre;


    //costructor allow findview and Toast
    public FavoriteOperation(Activity _activity, Context _context){
        this.activity = _activity;
        this.context = _context;
    }

    public void eseguiPreferiti(String _id, String _movieName, String _thumbnail,String _rating,String _synopsis,
                                String _release,String _genre,String _originalMovieName,String _voteCount){
        id = _id;
        movieName = _movieName;
        thumbnail = _thumbnail;
        rating  = _rating;
        synopsis = _synopsis;
        release = _release;
        genre = _genre;
        originalMovieName = _originalMovieName;
        voteCount = _voteCount;

        likeButtonFavorite = this.activity.findViewById(R.id.favorite_button);

        if(checkFilm()) {
            likeButtonFavorite.setLiked(true);
        }

        likeButtonFavorite.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButtonFavorite) {
                Log.d(TAG, "cliccato favorite");
                saveFavorite();
                Snackbar.make(likeButtonFavorite, "Added to Favorite", Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void unLiked(LikeButton likeButtonFavorite) {
                favorite = new Favorite();
                Log.d(TAG, "cliccato unfavorite");
                favorite.setMovieId(Integer.parseInt(_id));
                FavoriteDB.getInstance().dbInterface().deleteFavorite(favorite);
            }
        });
    }

    private void saveFavorite() {
        favorite = new Favorite();

        Log.d(TAG,"entrato nel save");
        Log.d(TAG,"contenuto id:" + id);

        favorite.setMovieId(Integer.parseInt(id));
        favorite.setTitle(movieName);
        favorite.setPosterPath(thumbnail);
        favorite.setUserRating(rating);
        favorite.setPlotSynopsys(synopsis);
        favorite.setReleaseDate(release);
        favorite.setGenreId(genre);
        favorite.setOriginalTitle(originalMovieName);
        favorite.setVoteCount(voteCount);

        FavoriteDB.getInstance().dbInterface().addFavorite(favorite);
        Log.d(TAG,"entarto nella tab Favorite");
    }

    private void mostraDb() {
        line = FavoriteDB.getInstance().dbInterface().getFavorite();
        for(Favorite favorite : line){
            Log.d(TAG,"Database: " +
                    "ID: " + favorite.getMovieId() +
                    "Title: " + favorite.getTitle());
        }
    }

    private void cancellaDb() {
        line = FavoriteDB.getInstance().dbInterface().getFavorite();
        FavoriteDB.getInstance().clearAllTables();
        Log.d(TAG, "db size: " + line.size());
        mostraDb();
    }

    private boolean checkFilm(){
        Log.d(TAG,"entrato nel check");
        line = FavoriteDB.getInstance().dbInterface().getFavorite();
        for(Favorite favorite : line){
            if(favorite.getMovieId() == Integer. parseInt(id))
                return true;
        }
        return false;
    }



}

