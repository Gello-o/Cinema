package com.example.cinemhub.filtri;

import com.example.cinemhub.model.Movie;

import java.util.Comparator;

public class PopularityFunctor implements Comparator<Movie> {
    @Override
    public int compare(Movie o1, Movie o2) {
        return o2.getVoteCount().compareTo(o1.getVoteCount());
    }
}
