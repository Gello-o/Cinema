package com.example.cinemhub.menu_items.filtri;

import com.example.cinemhub.model.Movie;

import java.util.Comparator;

/* Funtore per confronto e ordinamento di film per nome */

class NameFunctor implements Comparator<Movie> {
    private String order;

    //Metodo override che ordina la lista di film in base al nome.
        @Override
        public int compare(Movie o1, Movie o2) {
            if(order.equals("A"))
                return o1.getTitle().compareTo(o2.getTitle());
            else
                return o2.getTitle().compareTo(o1.getTitle());
        }

        /*
        Costruttore che prende una stringa come parametro.
        La stringa andrà a determinare se l'ordinamento sarà fatto per ascendente o discendente.
         */
        NameFunctor(String order) {
            this.order = order;
        }
}
