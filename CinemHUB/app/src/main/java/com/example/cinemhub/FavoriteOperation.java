package com.example.cinemhub;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.example.cinemhub.model.Favorite;
import com.example.cinemhub.model.FavoriteDB;
import com.google.android.material.snackbar.Snackbar;
import com.like.LikeButton;
import com.like.OnLikeListener;

import java.util.List;

public class FavoriteOperation {
    private static final String TAG = "FavoriteOperation";
    public Activity activity;

    private Favorite favorite;

    private String thumbnail, movieName, synopsis, rating, release, id, originalMovieName, voteCount, genre;
    
    //costructor allow findview and Toast
    public FavoriteOperation(Activity activity, Context context){
        this.activity = activity;
    }

    public void eseguiPreferiti(String id, String movieName, String thumbnail,String rating,String synopsis,
                                String release,String genre,String originalMovieName,String voteCount){
        this.id = id;
        this.movieName = movieName;
        this.thumbnail = thumbnail;
        this.rating  = rating;
        this.synopsis = synopsis;
        this.release = release;
        this.genre = genre;
        this.originalMovieName = originalMovieName;
        this.voteCount = voteCount;

        LikeButton likeButtonFavorite = this.activity.findViewById(R.id.favorite_button);

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
                favorite.setMovieId(Integer.parseInt(id));
                FavoriteDB.getInstance().dbInterface().deleteFavorite(favorite);
                Snackbar.make(likeButtonFavorite, "Removed to Favorite", Snackbar.LENGTH_SHORT).show();
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

    private boolean checkFilm(){
        Log.d(TAG,"entrato nel check");
        List<Favorite> table = FavoriteDB.getInstance().dbInterface().getFavorite();
        for(Favorite favorite : table){
            if(favorite.getMovieId() == Integer.parseInt(id))
                return true;
        }
        return false;
    }

}

