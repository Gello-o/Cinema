package com.example.cinemhub.model;

import com.example.cinemhub.model.Trailer;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class TrailerResponse {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("trailers")
    @Expose
    private List<Trailer> trailers;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Trailer> getTrailers() {
        return trailers;
    }

    public void setTrailer(List<Trailer> trailers) {
        this.trailers = trailers;
    }
}
