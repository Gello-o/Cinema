package com.example.cinemhub.menu_items.filtri;

import com.example.cinemhub.model.Movie;

import java.util.Comparator;

/* Funtore per confronto e ordinamento di film per popolarità */

class PopularityFunctor implements Comparator<Movie> {
    private String order;

    //Metodo override che ordina la lista di film in base alla popolarità
    @Override
    public int compare(Movie o1, Movie o2) {
        if(order.equals("A"))
            return o1.getVoteCount().compareTo(o2.getVoteCount());
        else
            return o2.getVoteCount().compareTo(o1.getVoteCount());
    }

    /*
    Costruttore che prende una stringa come parametro.
    La stringa andrà a determinare se l'ordinamento sarà fatto per ascendente o discendente.
     */
    PopularityFunctor(String order) {
        this.order = order;
    }
}
