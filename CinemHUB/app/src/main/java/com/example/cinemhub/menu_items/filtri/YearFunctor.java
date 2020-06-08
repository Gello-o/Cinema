package com.example.cinemhub.menu_items.filtri;

import com.example.cinemhub.model.Movie;

import java.util.Comparator;

public class YearFunctor implements Comparator<Movie> {
    String order;
    @Override
    public int compare(Movie o1, Movie o2) {
        if(order.equals("A"))
            return o1.getReleaseDate().compareTo(o2.getReleaseDate());
        else
            return o2.getReleaseDate().compareTo(o1.getReleaseDate());
    }

    public YearFunctor(String order) {
        this.order = order;
    }
}
