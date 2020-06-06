package com.example.cinemhub.menu_items.filtri;

import com.example.cinemhub.model.Movie;

import java.util.Comparator;

public class VoteFunctor implements Comparator<Movie> {
    String order;

    @Override
    public int compare(Movie o1, Movie o2) {
        if(order.equals("A"))
            return o1.getVoteAverage().compareTo(o2.getVoteAverage());
        else
            return o2.getVoteAverage().compareTo(o1.getVoteAverage());
    }

    public VoteFunctor(String order) {
        this.order = order;
    }
}
