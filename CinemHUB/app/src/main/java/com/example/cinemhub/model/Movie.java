package com.example.cinemhub.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Movie {
    @SerializedName("poster_path")
    @Expose
    private String posterPath;
    @SerializedName("overview")
    @Expose
    private String overview;
    @SerializedName("adult")
    @Expose
    private boolean adult;
    @SerializedName("genre_ids")
    @Expose
    private List<Integer> genre_ids;
    @SerializedName("release_date")
    @Expose
    private String release_date;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("original_title")
    @Expose
    private String original_title;
    @SerializedName("original_language")
    @Expose
    private String original_language;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("backdrop_path")
    @Expose
    private String backdrop_path;
    @SerializedName("popularity")
    @Expose
    private Double popularity;
    @SerializedName("vote_count")
    @Expose
    private Integer vote_count;
    @SerializedName("vote_average")
    @Expose
    private Double vote_average;
    @SerializedName("video")
    @Expose
    private boolean video;

    // brutto cane qua devi memorizzare i trailer in qualche modo

    private final String base_image_Url = "https://image.tmdb.org/t/p/w500";

    public String getOverview() {
        return overview;
    }

    public boolean isAdult() {
        return adult;
    }

    public List<Integer> getGenreIds() {
        return genre_ids;
    }

    public String getReleaseDate() {
        return release_date;
    }

    public void setReleaseDate(String release_date) {
        this.release_date = release_date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOriginalTitle() {
        return original_title;
    }

    public String getOriginalLanguage() {
        return original_language;
    }

    public String getTitle() {
        return title;
    }

    public String getBackdropPath() {
        return backdrop_path;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public Integer getVoteCount() {
        return vote_count;
    }

    public void setVoteCount(Integer vote_count) {
        this.vote_count = vote_count;
    }

    public Double getVoteAverage() {
        return vote_average;
    }

    public void setVoteAverage(Double vote_average) {
        this.vote_average = vote_average;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public String getPosterPath() {
        return base_image_Url + this.posterPath;
    }

}
