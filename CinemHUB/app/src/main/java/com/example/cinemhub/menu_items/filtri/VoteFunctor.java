package com.example.cinemhub.menu_items.filtri;

import com.example.cinemhub.model.Movie;

import java.util.Comparator;

/* Funtore per confronto e ordinamento di film per voto medio */

class VoteFunctor implements Comparator<Movie> {
    String order;

    //Metodo override che ordina la lista di film in base al voto.
    @Override
    public int compare(Movie o1, Movie o2) {
        if(order.equals("A"))
            return o1.getVoteAverage().compareTo(o2.getVoteAverage());
        else
            return o2.getVoteAverage().compareTo(o1.getVoteAverage());
    }

    /*
    Costruttore che prende una stringa come parametro.
    La stringa andrà a determinare se l'ordinamento sarà fatto per ascendente o discendente.
     */
    VoteFunctor(String order) {
        this.order = order;
    }
}
