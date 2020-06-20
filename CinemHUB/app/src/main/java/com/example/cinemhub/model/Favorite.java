package com.example.cinemhub.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


//Oggetto che conserva e mostra i dati degli oggetti Movie salvati nei preferiti

@Entity(tableName = "Favorite")
public class Favorite {
    public String TAG = "Favorite";


    @PrimaryKey
    int movie_id;

    @ColumnInfo(name = "Title")
    private String title;

    @ColumnInfo(name = "OriginalTitle")
    private String originalTitle;

    @ColumnInfo(name = "Rating")
    private String userRating;

    @ColumnInfo(name = "PosterPath")
    private String posterPath;

    @ColumnInfo(name = "Plot")
    private String plotSynopsys;

    @ColumnInfo(name = "ReleaseDate")
    private String releaseDate;

    @ColumnInfo(name = "VoteCount")
    private String voteCount;

    @ColumnInfo(name = "GenreId")
    private String genreId;

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