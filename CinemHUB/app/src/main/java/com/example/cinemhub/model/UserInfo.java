package com.example.cinemhub.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

//Oggetto che conserva e mostra i dati inerenti a rating e commento di un utente

@Entity(tableName = "UserInfo")
public class UserInfo {
    public String TAG = "UserInfo";

    // definizione del Db
    @PrimaryKey
    int movieId;

    @ColumnInfo(name = "Rating")
    private float rating;

    @ColumnInfo(name = "Overview")
    private String overview;


    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }
}