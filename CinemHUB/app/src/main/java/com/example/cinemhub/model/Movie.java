package com.example.cinemhub.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class Movie {
    @SerializedName("poster_path")
    @Expose
    private String posterPath;
    @SerializedName("overview")
    @Expose
    private String overview;

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



    public Movie(){}

    public Movie(String posterPath, String overview, String release_date, Integer id, String original_title, String title, Integer vote_count, Double vote_average, Integer genre) {
        this.posterPath = posterPath;
        this.overview = overview;
        this.release_date = release_date;
        this.id = id;
        this.original_title = original_title;
        this.title = title;
        this.vote_count = vote_count;
        this.vote_average = vote_average;
        this.genre_ids = new ArrayList<>();
        genre_ids.add(genre);
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
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

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getVoteCount() {
        return vote_count;
    }

    public Double getVoteAverage() {
        return vote_average;
    }

}