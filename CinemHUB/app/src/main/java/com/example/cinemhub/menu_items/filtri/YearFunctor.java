package com.example.cinemhub.menu_items.filtri;

import com.example.cinemhub.model.Movie;

import java.util.Comparator;

/* Funtore per confronto e ordinamento di film per anno di uscita */

class YearFunctor implements Comparator<Movie> {
    private String order;

    //Metodo override che ordina la lista di film in base all'anno d'uscita.
    @Override
    public int compare(Movie o1, Movie o2) {
        if(order.equals("A"))
            return o1.getReleaseDate().compareTo(o2.getReleaseDate());
        else
            return o2.getReleaseDate().compareTo(o1.getReleaseDate());
    }

    /*
    Costruttore che prende una stringa come parametro.
    La stringa andrà a determinare se l'ordinamento sarà fatto per ascendente o discendente.
     */
    YearFunctor(String order) {
        this.order = order;
    }
}
