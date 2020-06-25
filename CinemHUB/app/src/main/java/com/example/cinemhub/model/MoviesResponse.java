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

}
