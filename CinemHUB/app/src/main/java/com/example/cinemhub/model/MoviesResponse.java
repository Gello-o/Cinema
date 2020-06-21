package com.example.cinemhub.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

/*
Per Movie:
oggetto risultante dalla conversione del file JSON ottenuto da TMDB in linguaggio Java,
con i rispettivi attributi e metodi get e set.
*/

public class MoviesResponse {
    @SerializedName("page")
    @Expose
    private int page;
    @SerializedName("results")
    @Expose
    private List<Movie> results;
    @SerializedName("total_results")
    @Expose
    private int total_results;
    @SerializedName("total_pages")
    @Expose
    private int total_pages;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List <Movie> results) {
        this.results = results;
    }

    public int getTotalResults() {
        return total_results;
    }

    public void setTotalResults(int total_results) {
        this.total_results = total_results;
    }

    public int getTotalPages() {
        return total_pages;
    }

    public void setTotalPages(int total_pages) {
        this.total_pages = total_pages;
    }
}
