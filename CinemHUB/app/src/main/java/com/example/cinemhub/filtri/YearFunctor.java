package com.example.cinemhub.filtri;

import com.example.cinemhub.model.Movie;

import java.util.Comparator;

public class YearFunctor implements Comparator<Movie> {
    @Override
    public int compare(Movie o1, Movie o2) {
        return o2.getReleaseDate().compareTo(o1.getReleaseDate());
    }
}
