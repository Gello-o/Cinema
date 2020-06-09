package com.example.cinemhub.menu_items.filtri;

import com.example.cinemhub.model.Movie;

import java.util.Comparator;

public class NameFunctor implements Comparator<Movie> {
    String order;
        @Override
        public int compare(Movie o1, Movie o2) {
            if(order.equals("A"))
                return o1.getTitle().compareTo(o2.getTitle());
            else
                return o2.getTitle().compareTo(o1.getTitle());
        }

        public NameFunctor(String order) {
            this.order = order;
        }
}
