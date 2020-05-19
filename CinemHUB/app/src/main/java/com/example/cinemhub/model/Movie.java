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

    public Movie(String posterPath, String overview, String release_date, Integer id, String title, Double vote_average) {
        this.posterPath = posterPath;
        this.overview = overview;
        this.release_date = release_date;
        this.id = id;
        this.title = title;
        this.vote_average = vote_average;
    }

    public String getOverview() {
        return overview;
    }

    public List<Integer> getGenreIds() {
        return genre_ids;
    }

    public String getReleaseDate() {
        return release_date;
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

    public String getTitle() {
        return title;
    }

    public Double getVoteAverage() {
        return vote_average;
    }

    public String getPosterPath() {
        return base_image_Url + this.posterPath;
    }

}
