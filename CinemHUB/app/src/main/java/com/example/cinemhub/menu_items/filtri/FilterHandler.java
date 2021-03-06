package com.example.cinemhub.menu_items.filtri;

import android.annotation.SuppressLint;
import android.app.AlertDialog;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.example.cinemhub.R;

import com.example.cinemhub.menu_items.ricerca.SearchFragment;
import com.example.cinemhub.model.Movie;
import com.example.cinemhub.ui.categorie.MostraCategoriaFragment;
import com.example.cinemhub.ui.nuovi_arrivi.NuoviArriviFragment;
import com.example.cinemhub.ui.piu_visti.PiuVistiFragment;
import com.example.cinemhub.ui.prossime_uscite.ProssimeUsciteFragment;
import com.example.cinemhub.utils.Constants;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
l'oggetto FilterHandler ha la responsabilità di filtrare la lista di Movie.
Mostra un AlertDialog in cui l'utente ha la possibilità di scegliere tra le combinazioni dei seguenti filtri:
ordinamento per una specifica carattestica, filtro per genere, per voto e/o per anno di uscita
Quando l'utente decide di filtrare, l'oggetto FilterHandler disabilita il lazy loading
*/

public class FilterHandler {
    private static final String TAG = "FilterHandler";
    private Fragment fragment;
    private Menu menu;
    private List<Movie> moviesGlobal;
    private List<Movie> movieFiltered = new ArrayList<>();
    private TextView textViewVoteMax;
    private TextView textViewVoteMin;
    private TextView textViewYearMax;
    private TextView textViewYearMin;
    private CrystalRangeSeekbar rangeSeekbarVote;
    private CrystalRangeSeekbar rangeSeekbarYear;
    private Spinner spinnerCategroy;
    private Spinner spinnerOrder;

    /*
    Costruttore della classe.
    Gli vengono passati come parametro il fragment su cui andrà ad essere inserito e il menu.
     */

    public FilterHandler(Menu menu, Fragment fragment){
        this.fragment = fragment;
        this.menu = menu;
    }

    /*
    Metodo in cui verranno costruiti view e widget in cui sarà permesso filtrare.
     */
    public void implementFilter(int tipo) {
        LayoutInflater factory = LayoutInflater.from(fragment.getActivity());
        //final View textEntryView = factory.inflate(R.layout.filter_dialog, null);
        @SuppressLint("InflateParams") final View textEntryView = factory.inflate(R.layout.filter_dialog, null);

        //Ci sono varie barre di menu, si andrà a selezionare l'item in base a quella passato per parametro.
        MenuItem filterMenuItem;
        if(tipo==1)
            filterMenuItem = menu.findItem(R.id.filter);
        else
            filterMenuItem = menu.findItem(R.id.filter1);

        //Costruzione dei widget e spinner.
        spinnerCategroy = textEntryView.findViewById(R.id.spinnerCategory);
        spinnerOrder = textEntryView.findViewById(R.id.spinnerOrder);
        rangeSeekbarVote = textEntryView.findViewById(R.id.rangeSeekbarVote);
        rangeSeekbarYear = textEntryView.findViewById(R.id.rangeSeekbarYear);

        //Costruzioone di textview associate agli spinner.
        textViewVoteMax = textEntryView.findViewById(R.id.textViewMaxVote);
        textViewVoteMin = textEntryView.findViewById(R.id.textViewMinVote);
        textViewYearMax = textEntryView.findViewById(R.id.textViewMaxYear);
        textViewYearMin = textEntryView.findViewById(R.id.textViewMinYear);

        //Passaggio di una lista conenente i generi dei film e costruzione del primo spinner.
        List<String> listVote = Constants.setGenre();
        spinnerList(spinnerCategroy, listVote);

        //Costruzione lista contenente i modi per cui si vuole ordinare e costruzione del secondo spinner.
        List<String> listYear = new ArrayList<>();
        listYear.add("");
        listYear.add("Name ASC");
        listYear.add("Name DESC");
        listYear.add("Vote ASC");
        listYear.add("Vote DESC");
        listYear.add("Popularity ASC");
        listYear.add("Popularity DESC");
        listYear.add("Year ASC");
        listYear.add("Year DESC");
        spinnerList(spinnerOrder, listYear);

        //Definizione della dialog che andrà a contenere i widget dei filtri
        final AlertDialog.Builder alert = new AlertDialog.Builder(fragment.getActivity()).setView(textEntryView).setTitle(" Filter:");

        //Click dell'item di menu
        filterMenuItem.setOnMenuItemClickListener(item -> {
            Log.d(TAG, "onClick");

            ViewGroup viewGroup = (ViewGroup) textEntryView.getParent();

            //Gestione nel caso la view dell'alert non sia stata rimossa in precedenza
            if(viewGroup != null)
                viewGroup.removeView(textEntryView);

            //Gestione del movimento delle seekbar.
            seekBarMove();

            //Click del pulsante save
            alert.setTitle("").setView(textEntryView).setPositiveButton("Save",
                    (dialog, whichButton) -> {

                        setFragmentCanLoad();

                        //Prende gli elementi dei rispettivi spinner.
                        String stringSpinnerCategory = spinnerCategroy.getSelectedItem().toString();
                        String stringSpinnerOrder = spinnerOrder.getSelectedItem().toString();

                        //Metodo usato per il filtraggio.
                        filter(textViewVoteMin.getText().toString(), textViewVoteMax.getText().toString(),
                                textViewYearMin.getText().toString(), textViewYearMax.getText().toString(),
                                stringSpinnerCategory, stringSpinnerOrder);

                        //Click del pulsante cancel.
                    }).setNegativeButton("Cancel",
                    (dialog, whichButton) -> Log.d(TAG, "Cancel"));

            alert.show();
            return true;
        });
    }

    //Metodo utilizzato per inizializzare gli oggetti di uno spinner in base ad una lista data.
    private void spinnerList(Spinner spinner, List<String> spinnerFilter) {

        List<String> categoryList = new ArrayList<>(spinnerFilter);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(fragment.getActivity(),
                android.R.layout.simple_spinner_item, categoryList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("Enter");
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    /*
    Metodo che permette di rilevare il movimento delle seekbar e che scrive nelle textview
    asscoiate il valore dei rispettivi thumb.
     */
    private void seekBarMove() {

        // set listener
        rangeSeekbarVote.setOnRangeSeekbarChangeListener((minValue, maxValue) -> {
            Log.d(TAG, "ChangedVote: "+minValue+", "+maxValue);
            textViewVoteMin.setText(String.valueOf(minValue));
            textViewVoteMax.setText(String.valueOf(maxValue));
        });
        // set final value listener
        rangeSeekbarVote.setOnRangeSeekbarFinalValueListener((minValue, maxValue) -> Log.d("CRS=>", minValue + " : " + maxValue));

        // set listener
        rangeSeekbarYear.setOnRangeSeekbarChangeListener((minValue, maxValue) -> {
            Log.d(TAG, "ChangedYear: "+minValue+", "+maxValue);
            textViewYearMin.setText(String.valueOf(minValue));
            textViewYearMax.setText(String.valueOf(maxValue));
        });

        // set final value listener
        rangeSeekbarYear.setOnRangeSeekbarFinalValueListener((minValue, maxValue) -> Log.d("CRS=>", minValue + " : " + maxValue));
    }

    //Metodo di supporto al metodo filter: filtra voto.
    private List<Movie> filterVote(String voteMin, String voteMax) {
        int intVoteMin = Integer.parseInt(voteMin);
        int intVoteMax = Integer.parseInt(voteMax);

        //if(intVoteMin == 0 && intVoteMax == 10)
          //  return moviesGlobal;
        if(moviesGlobal == null)
            return new ArrayList<>();


        List<Movie> tmp = new ArrayList<>();

        for(Movie m : moviesGlobal) {
            if(m!=null && m.getVoteAverage() != null && m.getVoteAverage() >= intVoteMin &&
                    m.getVoteAverage() <= intVoteMax) {
                if(!tmp.contains(m))
                    tmp.add(m);
            }
            else
                Log.d(TAG, "MovieNull");
        }

        return tmp;
    }

    //Metodo di supporto a filter: filtra per genere
    private List<Movie> filterGen(int genId, List<Movie> tmp) {
        List<Movie> tmp2 = new ArrayList<>();

        for(Movie m : tmp) {
            if(m!=null && m.getGenreIds() != null) {
                if(!tmp2.contains(m)) {
                    for (Integer id : m.getGenreIds()) {
                        if (id == genId)
                            tmp2.add(m);
                    }
                }
            }
            else
                Log.d(TAG, "MovieNull");
        }
        return tmp2;
    }

    //Metodo di supporto a filter: filtra per anno d'uscita.
    private List<Movie> filterYear(String yearMin, String yearMax, List<Movie> tmp) {
        int yearIntMin = Integer.parseInt(yearMin);
        int yearIntMax = Integer.parseInt(yearMax);
        int yearMovieInt = 0;

        //if(yearIntMin == 1929 && yearIntMax == 2027)
          //  return tmp;

        List<Movie> tmp1 = new ArrayList<>();

        for(Movie m : tmp) {
            if(m != null && m.getReleaseDate() != null && m.getReleaseDate().length()==10)
                yearMovieInt = Integer.parseInt(m.getReleaseDate().substring(0,4));
            else
                Log.d(TAG, "MovieNull");

            if(yearMovieInt >= yearIntMin && yearMovieInt <= yearIntMax) {
                if(!tmp1.contains(m))
                    tmp1.add(m);
            }
        }
        return tmp1;
    }

    //Metodo di supporto al metodo filter: ordina in base al parametro order.
    private void filterOrder(String order) {
        switch (order) {
            case "Name ASC":
                Collections.sort(movieFiltered, new NameFunctor("A"));
                break;
            case "Name DESC":
                Collections.sort(movieFiltered, new NameFunctor("D"));
                break;
            case "Vote ASC":
                Collections.sort(movieFiltered, new VoteFunctor("A"));
                break;
            case "Vote DESC":
                Collections.sort(movieFiltered, new VoteFunctor("D"));
                break;
            case "Popularity ASC":
                Collections.sort(movieFiltered, new PopularityFunctor("A"));
                break;
            case "Popularity DESC":
                Collections.sort(movieFiltered, new PopularityFunctor("D"));
                break;
            case "Year ASC":
                Collections.sort(movieFiltered, new YearFunctor("A"));
                break;
            case "Year DESC":
                Collections.sort(movieFiltered, new YearFunctor("D"));
                break;
        }
    }

    /*
    Metodo che fa il filtraggio della lista dei film corrispondente, utilizzando metodi di supporto della classe
    Infine aggiorna la lista dei film nella stessa pagina
     */
    private void filter(String votoMin, String votoMax, String annoMin, String annoMax, String category, String order) {
        movieFiltered.clear();

        moviesGlobal = getCurrents();

        if(category.equals("") && order.equals("")){
            movieFiltered = filterYear(annoMin, annoMax, filterVote(votoMin, votoMax));
            Log.d(TAG, "Entrato0");
        }

        else {
            int genId = genSet(category);

            if(!category.equals("") && !order.equals("")) {
                Log.d(TAG, "Entrato1");
                movieFiltered = filterGen(genId, filterYear(annoMin, annoMax, filterVote(votoMin, votoMax)));
                filterOrder(order);
            }
            else if(category.equals("")) {
                Log.d(TAG, "Entrato2");
                movieFiltered = filterYear(annoMin, annoMax, filterVote(votoMin, votoMax));
                filterOrder(order);
            }else {
                Log.d(TAG, "Entrato3");
                movieFiltered = filterGen(genId, filterYear(annoMin, annoMax, filterVote(votoMin, votoMax)));
            }

        }

        if(fragment instanceof NuoviArriviFragment) {
            ( (NuoviArriviFragment) fragment ).initMovieRV(movieFiltered);
            ( (NuoviArriviFragment) fragment ).saveFiltered(movieFiltered);
        }else if(fragment instanceof SearchFragment) {
            ( (SearchFragment) fragment ).initMovieRV(movieFiltered);
            ( (SearchFragment) fragment ).saveFiltered(movieFiltered);
        }else if(fragment instanceof ProssimeUsciteFragment){
            ((ProssimeUsciteFragment) fragment).initMovieRV(movieFiltered);
            ((ProssimeUsciteFragment) fragment).saveFiltered(movieFiltered);
        }else if(fragment instanceof PiuVistiFragment) {
            ( (PiuVistiFragment) fragment ).initMovieRV(movieFiltered);
            ( (PiuVistiFragment) fragment ).saveFiltered(movieFiltered);
        }else if(fragment instanceof MostraCategoriaFragment) {
            ( (MostraCategoriaFragment) fragment ).initMovieRV(movieFiltered);
            ( (MostraCategoriaFragment) fragment ).saveFiltered(movieFiltered);
        }
        /*
        for(int i=0; i< movieFiltered.size(); i++){
            if(movieFiltered.get(i) != null)
                Log.d(TAG, "film " + movieFiltered.get(i).getTitle());
        }
        */
    }

    //Metodo di supporto a filter: Serve per associare al genere l'id corrispondente all'api tmdb.
    private int genSet(String category) {
        int genId = 0;
        if(category.equals("Action"))
            genId = Constants.ACTION;
        else {
            switch (category) {
                case "Adventure":
                    genId = Constants.ADVENTURE;
                    break;
                case "Animation":
                    genId = Constants.ANIMATION;
                    break;
                case "Comedy":
                    genId = Constants.COMEDY;
                    break;
                case "Crime":
                    genId = Constants.CRIME;
                    break;
                case "Documentary":
                    genId = Constants.DOCUMENTARY;
                    break;
                case "Drama":
                    genId = Constants.DRAMA;
                    break;
                case "Family":
                    genId = Constants.FAMILY;
                    break;
                case "Fantasy":
                    genId = Constants.FANTASY;
                    break;
                case "History":
                    genId = Constants.HISTORY;
                    break;
                case "Horror":
                    genId = Constants.HORROR;
                    break;
                case "Music":
                    genId = Constants.MUSIC;
                    break;
                case "Mystery":
                    genId = Constants.MYSTERY;
                    break;
                case "Romance":
                    genId = Constants.ROMANCE;
                    break;
                case "SciFi":
                    genId = Constants.SCIFI;
                    break;
                case "Thriller":
                    genId = Constants.THRILLER;
                    break;
                case "Tv-Movie":
                    genId = Constants.TVMOVIE;
                    break;
                case "War":
                    genId = Constants.WAR;
                    break;
                case "Western":
                    genId = Constants.WESTERN;
                    break;
            }
        }
        return genId;
    }


    private void setFragmentCanLoad(){
        if(fragment instanceof MostraCategoriaFragment) {
            ((MostraCategoriaFragment) fragment).setCanLoad(false);
        }else if(fragment instanceof PiuVistiFragment){
            ((PiuVistiFragment) fragment).setCanLoad(false);
        }else if(fragment instanceof ProssimeUsciteFragment) {
            ((ProssimeUsciteFragment) fragment).setCanLoad(false);
        }else if(fragment instanceof NuoviArriviFragment){
            ((NuoviArriviFragment) fragment).setCanLoad(false);
        }else if(fragment instanceof SearchFragment){
            ((SearchFragment) fragment).setCanLoad(false);
        }
    }

    private List<Movie> getCurrents(){
        if(fragment instanceof MostraCategoriaFragment) {
            return ((MostraCategoriaFragment) fragment).getCurrentMovies();
        }else if(fragment instanceof PiuVistiFragment){
            return ((PiuVistiFragment) fragment).getCurrentMovies();
        }else if(fragment instanceof ProssimeUsciteFragment) {
            return ((ProssimeUsciteFragment) fragment).getCurrentMovies();
        }else if(fragment instanceof NuoviArriviFragment){
            return ((NuoviArriviFragment) fragment).getCurrentMovies();
        }else if(fragment instanceof SearchFragment){
            return ((SearchFragment) fragment).getCurrentMovies();
        }
        Log.d(TAG, "nessuno dei fragment");
        return new ArrayList<>();

    }
}