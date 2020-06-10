package com.example.cinemhub.menu_items.filtri;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarFinalValueListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.example.cinemhub.R;
import com.example.cinemhub.model.Movie;
import com.example.cinemhub.menu_items.ricerca.SearchFragment;
import com.example.cinemhub.ui.add_list.AddListFragment;
import com.example.cinemhub.ui.categorie.MostraCategoriaFragment;
import com.example.cinemhub.ui.nuovi_arrivi.NuoviArriviFragment;
import com.example.cinemhub.ui.piu_visti.PiuVistiFragment;
import com.example.cinemhub.ui.prossime_uscite.ProssimeUsciteFragment;
import com.example.cinemhub.utils.Constants;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class FilterHandler {
    private static final String TAG = "FilterHandler";
    private Fragment fragment;
    private Menu menu;
    private static final String DIGIT_PATTERN = "\\d+";
    private static final Pattern DIGIT_PATTERN_COMPILED = Pattern.compile(DIGIT_PATTERN);
    List<Movie> moviesGlobal;
    List<Movie> movieFiltered = new ArrayList<>();
    List<Movie> movieWork;
    private TextView textViewVoteMax;
    private TextView textViewVoteMin;
    private TextView textViewYearMax;
    private TextView textViewYearMin;
    private CrystalRangeSeekbar rangeSeekbarVote;
    private CrystalRangeSeekbar rangeSeekbarYear;
    private Spinner spinnerCategroy;
    private Spinner spinnerOrder;


    public FilterHandler(Menu menu, Fragment fragment){
        this.fragment = fragment;
        this.menu = menu;
        this.movieWork = new ArrayList<>();
        this.moviesGlobal = new ArrayList<>();
    }

    public void implementFilter(int tipo) {
        LayoutInflater factory = LayoutInflater.from(fragment.getActivity());
        //final View textEntryView = factory.inflate(R.layout.filter_dialog, null);
        final View textEntryView = factory.inflate(R.layout.filter_dialog, null);


        MenuItem filterMenuItem;
        if(tipo==1)
            filterMenuItem = menu.findItem(R.id.filter);
        else
            filterMenuItem = menu.findItem(R.id.filter1);

        spinnerCategroy = textEntryView.findViewById(R.id.spinnerCategory);
        spinnerOrder = textEntryView.findViewById(R.id.spinnerOrder);
        rangeSeekbarVote = textEntryView.findViewById(R.id.rangeSeekbarVote);
        rangeSeekbarYear = textEntryView.findViewById(R.id.rangeSeekbarYear);
        textViewVoteMax = textEntryView.findViewById(R.id.textViewMaxVote);
        textViewVoteMin = textEntryView.findViewById(R.id.textViewMinVote);
        textViewYearMax = textEntryView.findViewById(R.id.textViewMaxYear);
        textViewYearMin = textEntryView.findViewById(R.id.textViewMinYear);


        List<String> listVote = Constants.setGenre();
        spinnerList(spinnerCategroy, listVote);


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

        View filterOpt = filterMenuItem.getActionView();
        Log.d(TAG, "FilterMenuItem: "+filterMenuItem);
        Log.d(TAG, "FilterOpt: "+filterOpt);

        final AlertDialog.Builder alert = new AlertDialog.Builder(fragment.getActivity()).setView(textEntryView).setTitle(" Filter:");
        filterMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Log.d(TAG, "onClick");

                seekBarMove();

                alert.setIcon(R.drawable.heart_on).setTitle(" Filter:").setView(textEntryView).setPositiveButton("Save",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {

                                String stringSpinnerCategory = spinnerCategroy.getSelectedItem().toString();
                                String stringSpinnerOrder = spinnerOrder.getSelectedItem().toString();

                                filter(textViewVoteMin.getText().toString(), textViewVoteMax.getText().toString(),
                                        textViewYearMin.getText().toString(), textViewYearMax.getText().toString(),
                                        stringSpinnerCategory, stringSpinnerOrder);

                                ViewGroup viewGroup = (ViewGroup) textEntryView.getParent();
                                viewGroup.removeView(textEntryView);
                            }

                        }).setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                                ViewGroup viewGroup = (ViewGroup) textEntryView.getParent();
                                viewGroup.removeView(textEntryView);
                                Log.d(TAG, "Cancel");
                            }
                        });
                alert.show();
                return true;
            }
        });
    }

    void spinnerList(Spinner spinner, List<String> spinnerFilter) {
        List<String> categroyList = new ArrayList<>();

        for(String s : spinnerFilter)
            categroyList.add(s);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(fragment.getActivity(),
                android.R.layout.simple_spinner_item, categroyList);
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

    public void seekBarMove() {


        // set listener
        rangeSeekbarVote.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                Log.d(TAG, "ChangedVote: "+minValue+", "+maxValue);
                textViewVoteMin.setText(String.valueOf(minValue));
                textViewVoteMax.setText(String.valueOf(maxValue));
            }
        });
        // set final value listener
        rangeSeekbarVote.setOnRangeSeekbarFinalValueListener(new OnRangeSeekbarFinalValueListener() {
            @Override
            public void finalValue(Number minValue, Number maxValue) {
                Log.d("CRS=>", String.valueOf(minValue) + " : " + String.valueOf(maxValue));
            }
        });

        // set listener
        rangeSeekbarYear.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                Log.d(TAG, "ChangedYear: "+minValue+", "+maxValue);
                textViewYearMin.setText(String.valueOf(minValue));
                textViewYearMax.setText(String.valueOf(maxValue));
            }
        });

        // set final value listener
        rangeSeekbarYear.setOnRangeSeekbarFinalValueListener(new OnRangeSeekbarFinalValueListener() {
            @Override
            public void finalValue(Number minValue, Number maxValue) {
                Log.d("CRS=>", String.valueOf(minValue) + " : " + String.valueOf(maxValue));
            }
        });
    }

    //Setta la lista globale
    public void setMovie(List<Movie> moviesGlobal) {
        if(moviesGlobal==null || moviesGlobal.size()==0) {
            Log.d(TAG, "Lista null");
            this.moviesGlobal = new ArrayList<>();
            this.movieWork = new ArrayList<>();
        }

        else {
            this.moviesGlobal = moviesGlobal;
            Log.d(TAG, "MoviesGlobalSize: "+moviesGlobal.size());
        }
    }

    public void filterVote(String voteMin, String voteMax) {
        Boolean flag = false;
        int intVoteMin = Integer.parseInt(voteMin);
        int intVoteMax = Integer.parseInt(voteMax);

        for(Movie m : movieWork) {
            if(m.getVoteAverage() >= intVoteMin &&
                    m.getVoteAverage() <= intVoteMax) {
                for(Movie m2 : movieFiltered) {
                    if (m2.getId().equals(m.getId())) {
                        flag = true;
                    }
                }
                if (!flag) {
                    movieFiltered.add(m);
                }
            }
        }
        movieWork.clear();
        movieWork.addAll(movieFiltered);
        movieFiltered.clear();
    }

    public void filterGen(int genId) {
        Boolean flag = false;
        for(Movie m : movieWork) {
            for(Integer genM : m.getGenreIds()) {
                if(genM.equals(genId)) {
                    for(Movie m2 : movieFiltered) {
                        if (m2.getId().equals(m.getId())) {
                            flag = true;
                        }
                    }
                    if(!flag)
                        movieFiltered.add(m);
                }
            }
        }
        movieWork.clear();
        movieWork.addAll(movieFiltered);
        movieFiltered.clear();
    }

    public void filterOrder(String order) {
        if(order.equals("Name ASC"))
            Collections.sort(movieWork, new NameFunctor("A"));
        else if(order.equals("Name DESC"))
            Collections.sort(movieWork, new NameFunctor("D"));
        else if(order.equals("Vote ASC"))
            Collections.sort(movieWork, new NameFunctor("A"));
        else if(order.equals("Vote DESC"))
            Collections.sort(movieWork, new VoteFunctor("D"));
        else if(order.equals("Popularity ASC"))
            Collections.sort(movieWork, new PopularityFunctor("A"));
        else if(order.equals("Popularity DESC"))
            Collections.sort(movieWork, new PopularityFunctor("D"));
        else if(order.equals("Year ASC"))
            Collections.sort(movieWork, new YearFunctor("A"));
        else if(order.equals("Year DESC"))
            Collections.sort(movieWork, new YearFunctor("D"));
    }

    public void filterYear(String yearMin, String yearMax) {
        Boolean flag = false;
        int yearIntMin = Integer.parseInt(yearMin);
        int yearIntMax = Integer.parseInt(yearMax);
        int yearMovieInt;
        //String s;
        for(Movie m : movieWork) {
            yearMovieInt = 0;
            if(m.getReleaseDate().length()==10)
                yearMovieInt = Integer.parseInt(m.getReleaseDate().substring(0,4));
            if(yearMovieInt >= yearIntMin && yearMovieInt <= yearIntMax) {
                for(Movie m2 : movieFiltered) {
                    if (m2.getId().equals(m.getId())) {
                        flag = true;
                    }
                }
                if (!flag) {
                    movieFiltered.add(m);
                }
            }
        }
        movieWork.clear();
        movieWork.addAll(movieFiltered);
        movieFiltered.clear();
    }

    public void filter(String votoMin, String votoMax, String annoMin, String annoMax, String category, String order) {
        movieWork.clear();
        movieWork.addAll(moviesGlobal);

        for(Movie m : movieWork)
            Log.d(TAG, "Work0. Name: "+m.getTitle()+", Year: "+m.getReleaseDate());

        if(category.equals("") && order.equals("")){
            filterVote(votoMin, votoMax);
            filterYear(annoMin, annoMax);
            Log.d(TAG, "Entrato0");
            for(Movie m : movieWork)
                Log.d(TAG, "Work1. Name: "+m.getTitle()+", Year: "+m.getReleaseDate());
        }

        else {
            int genId = genSet(category);

            if(category.equals("") && !order.equals("")) {
                Log.d(TAG, "Entrato1");
                filterVote(votoMin, votoMax);
                filterYear(annoMin, annoMax);
                filterOrder(order);
            }

            else if(!category.equals("") && order.equals("")) {
                Log.d(TAG, "Entrato2");
                filterVote(votoMin, votoMax);
                filterYear(annoMin, annoMax);
                filterGen(genId);
            }

            else if(!category.equals("") && !order.equals("")) {
                Log.d(TAG, "Entrato3");
                filterVote(votoMin, votoMax);
                filterYear(annoMin, annoMax);
                filterGen(genId);
                filterOrder(order);
            }
        }

        if(fragment instanceof AddListFragment)
            ((AddListFragment) fragment).initMovieRV(movieWork);
        if(fragment instanceof NuoviArriviFragment)
            ((NuoviArriviFragment) fragment).initMovieRV(movieWork);
        if(fragment instanceof SearchFragment)
            ((SearchFragment) fragment).initMovieRV(movieWork);
        if(fragment instanceof ProssimeUsciteFragment)
            ((ProssimeUsciteFragment) fragment).initMovieRV(movieWork);;
        if(fragment instanceof PiuVistiFragment)
            ((PiuVistiFragment) fragment).initMovieRV(movieWork);
        if(fragment instanceof MostraCategoriaFragment)
            ((MostraCategoriaFragment) fragment).initMovieRV(movieWork);

    }

    private int genSet(String category) {
        int genId = 0;
        if(category.equals("Action"))
            genId = Constants.ACTION;
        else {
            if(category.equals("Adventure"))
                genId = Constants.ADVENTURE;
            else if(category.equals("Animation"))
                genId = Constants.ANIMATION;
            else if(category.equals("Comedy"))
                genId = Constants.COMEDY;
            else if(category.equals("Crime"))
                genId = Constants.CRIME;
            else if(category.equals("Documentary"))
                genId = Constants.DOCUMENTARY;
            else if(category.equals("Drama"))
                genId = Constants.DRAMA;
            else if(category.equals("Family"))
                genId = Constants.FAMILY;
            else if(category.equals("Fantasy"))
                genId = Constants.FANTASY;
            else if(category.equals("History"))
                genId = Constants.HISTORY;
            else if(category.equals("Horror"))
                genId = Constants.HORROR;
            else if(category.equals("Music"))
                genId = Constants.MUSIC;
            else if(category.equals("Mystery"))
                genId = Constants.MYSTERY;
            else if(category.equals("Romance"))
                genId = Constants.ROMANCE;
            else if(category.equals("SciFi"))
                genId = Constants.SCIFI;
            else if(category.equals("Thriller"))
                genId = Constants.THRILLER;
            else if(category.equals("Tv-Movie"))
                genId = Constants.TVMOVIE;
            else if(category.equals("War"))
                genId = Constants.WAR;
            else if(category.equals("Western"))
                genId = Constants.WESTERN;
        }
        return genId;
    }
}