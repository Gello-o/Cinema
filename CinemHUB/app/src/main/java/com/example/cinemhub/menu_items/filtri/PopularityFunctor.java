package com.example.cinemhub.menu_items.filtri;

import com.example.cinemhub.model.Movie;

import java.util.Comparator;

/* Funtore per confronto e ordinamento di film per popolarità */

public class PopularityFunctor implements Comparator<Movie> {
    private String order;
    @Override
    public int compare(Movie o1, Movie o2) {
        if(order.equals("A"))
            return o1.getVoteCount().compareTo(o2.getVoteCount());
        else
            return o2.getVoteCount().compareTo(o1.getVoteCount());
    }

    PopularityFunctor(String order) {
        this.order = order;
    }
}
