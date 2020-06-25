package com.example.cinemhub.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

/*
Per Trailer:
oggetto risultante dalla conversione del file JSON ottenuto da TMDB in linguaggio Java,
con i rispettivi attributi e metodi get e set.
*/

public class TrailerResponse {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("results")
    @Expose
    private List<Trailer> results;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Trailer> getTrailers() {
        return results;
    }
}
