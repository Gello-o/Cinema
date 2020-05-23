package com.example.cinemhub.model;

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

    @ColumnInfo(name = "OriginalTitle")
    String originalTitle;

    @ColumnInfo(name = "Rating")
    String userRating;

    @ColumnInfo(name = "PosterPath")
    String posterPath;

    @ColumnInfo(name = "Plot")
    String plotSynopsys;

    @ColumnInfo(name = "ReleaseDate")
    String releaseDate;

    @ColumnInfo(name = "VoteCount")
    String voteCount;

    @ColumnInfo(name = "GenreId")
    String genreId;

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(String voteCount) {
        this.voteCount = voteCount;
    }

    public String getGenreId() {
        return genreId;
    }

    public void setGenreId(String genreId) {
        this.genreId = genreId;
    }

    public int getMovieId() {
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


    public void setMovieId(int movie_id) {
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
    }
}