package com.example.cinemhub.room;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Favorite")
public class Favorite {
    public String TAG = "Favorite";

        // definizione del Db
        @PrimaryKey
        @NonNull
        int movie_id;

        @ColumnInfo(name = "Title")
        String title;

        @ColumnInfo(name = "Rating")
        String userRating;

        @ColumnInfo(name = "PosterPath")
        String posterPath;

        @ColumnInfo(name = "Plot")
        String plotSynopsys;

        //metodi get

        public int getMovie_id() {
            return movie_id;
        }

        public String getTitle() {
            return title;
        }

        public String getUserRating() {
            return userRating;
        }

        public String getPosterPath() {
            return posterPath;
        }

        public String getPlotSynopsys() {
            return plotSynopsys;
        }

        // metodi Set


        public void setMovie_id(int movie_id) {
            this.movie_id = movie_id;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setUserRating(String userRating) {
            this.userRating = userRating;
        }

        public void setPosterPath(String posterPath) {
            this.posterPath = posterPath;
        }

        public void setPlotSynopsys(String plotSynopsys) {
            this.plotSynopsys = plotSynopsys;
        }}

