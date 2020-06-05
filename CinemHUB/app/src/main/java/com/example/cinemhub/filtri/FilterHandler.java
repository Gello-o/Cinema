package com.example.cinemhub.filtri;

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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import com.example.cinemhub.R;
import com.example.cinemhub.model.Movie;
import com.example.cinemhub.ricerca.SearchFragment;
import com.example.cinemhub.ui.add_list.AddListFragment;
import com.example.cinemhub.ui.categorie.MostraCategoriaFragment;
import com.example.cinemhub.ui.nuovi_arrivi.NuoviArriviFragment;
import com.example.cinemhub.ui.piu_visti.PiuVistiFragment;
import com.example.cinemhub.ui.piu_visti.PiuVistiViewModel;
import com.example.cinemhub.ui.prossime_uscite.ProssimeUsciteFragment;
import com.example.cinemhub.utils.Constants;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
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


    public FilterHandler(Menu menu, Fragment fragment){
        this.fragment = fragment;
        this.menu = menu;
        this.movieWork = new ArrayList<>();
        this.moviesGlobal = new ArrayList<>();
    }

    public void implementFilter(int tipo) {
        LayoutInflater factory = LayoutInflater.from(fragment.getActivity());
        final View textEntryView = factory.inflate(R.layout.filter_dialog, null);

        MenuItem filterMenuItem;
        if(tipo==1)
            filterMenuItem = menu.findItem(R.id.filter);
        else
            filterMenuItem = menu.findItem(R.id.filter1);

        final Spinner spinnerCategroy = (Spinner) textEntryView.findViewById(R.id.spinner1);
        final Spinner spinnerOrder = (Spinner) textEntryView.findViewById(R.id.spinner2);
        final EditText editTextVote = (EditText) textEntryView.findViewById(R.id.vote);
        final EditText editTextYear = (EditText) textEntryView.findViewById(R.id.year);


        List<String> listVote = new ArrayList<>();
        listVote.add("");
        listVote.add("Action");
        listVote.add("Romance");
        listVote.add("Thriller");
        listVote.add("Animation");
        spinnerList(spinnerCategroy, listVote);


        List<String> listYear = new ArrayList<>();
        listYear.add("");
        listYear.add("Name");
        listYear.add("Vote");
        listYear.add("Popularity");
        listYear.add("Year");
        spinnerList(spinnerOrder, listYear);



        //spinnerList(spinnerCategroy, "Action", "Romance", "Thriller", "Animation");
        //spinnerList(spinnerOrder, "Name", "Vote", "Popularity", "Year");

        editTextVote.setText("", TextView.BufferType.EDITABLE);
        editTextYear.setText("", TextView.BufferType.EDITABLE);

        View filterOpt = filterMenuItem.getActionView();
        Log.d(TAG, "FilterMenuItem: "+filterMenuItem);
        Log.d(TAG, "FilterOpt: "+filterOpt);



        final AlertDialog.Builder alert = new AlertDialog.Builder(fragment.getActivity()).setView(textEntryView).setTitle(" Filter:");
        filterMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                alert.setIcon(R.drawable.heart_on).setTitle(" Filter:").setView(textEntryView).setPositiveButton("Save",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                                Matcher matcher = DIGIT_PATTERN_COMPILED.matcher(editTextVote.getText().toString());
                                if(matcher.matches()) {
                                    int a1 = Integer.parseInt(editTextVote.getText().toString());
                                    if(a1<1 || a1>10)
                                        Log.d(TAG, "Qua facciamo spuntare un messaggio d'errore");
                                }
                                Matcher matcher2 = DIGIT_PATTERN_COMPILED.matcher(editTextYear.getText().toString());
                                if(matcher2.matches()) {
                                    int a2 = Integer.parseInt(editTextYear.getText().toString());
                                    if(a2<1900 || a2>2020)
                                        Log.d(TAG, "Qua facciamo spuntare un messaggio d'errore");
                                }
                                String stringSpinnerCategory = spinnerCategroy.getSelectedItem().toString();
                                String stringSpinnerOrder = spinnerOrder.getSelectedItem().toString();

                                filter2(editTextVote.getText().toString(), editTextYear.getText().toString(),
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

    public MutableLiveData<List<Movie>> getGlobalFilter() {
        return new MutableLiveData<>();
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
            this.movieWork = moviesGlobal;
            Log.d(TAG, "MoviesGlobalSize: "+movieWork.size());
        }
    }

    public void filterVote(String vote) {
        Boolean flag = false;
        for(Movie m : movieWork) {
            if(m.getVoteAverage() >= Integer.parseInt(vote)) { // && !movieFiltered.contains(m)
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
            if(order.equals("Name"))
                Collections.sort(movieWork, new NameFunctor());
            else if(order.equals("Vote"))
                Collections.sort(movieWork, new VoteFunctor());
            else if(order.equals("Popularity"))
                Collections.sort(movieWork, new PopularityFunctor());
            else if(order.equals("Year"))
                Collections.sort(movieWork, new YearFunctor());
        //movieWork.clear();
        //movieWork.addAll(movieFiltered);
        //movieFiltered.clear();
    }

    public void filterYear(String year) {
        Boolean flag = false;
        int yearInt = Integer.parseInt(year);
        int yearMovieInt;
        //String s;
        for(Movie m : movieWork) {
            //s = "";
            yearMovieInt = 0;
            if(m.getReleaseDate().length()==10)
                yearMovieInt = Integer.parseInt(m.getReleaseDate().substring(0,4));
                //Log.d(TAG, "ReleaseMovieMFId: "+m.getId()+ " " + m.getReleaseDate());
            if(yearMovieInt >= yearInt) {
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

    public void filter2(String voto, String anno, String category, String order) {
        if(anno.equals("") && voto.equals("") && category.equals("") && order.equals("")){
            Log.d(TAG, "Entrato0");
            if(fragment instanceof AddListFragment)
                ((AddListFragment) fragment).initMovieRV(moviesGlobal);
            if(fragment instanceof NuoviArriviFragment)
                ((NuoviArriviFragment) fragment).initMovieRV(moviesGlobal);
            if(fragment instanceof SearchFragment)
                ((SearchFragment) fragment).initMovieRV(moviesGlobal);
            if(fragment instanceof ProssimeUsciteFragment)
                ((ProssimeUsciteFragment) fragment).initMovieRV(moviesGlobal);
            if(fragment instanceof PiuVistiFragment)
                ((PiuVistiFragment) fragment).initMovieRV(moviesGlobal);
            if(fragment instanceof MostraCategoriaFragment)
                ((MostraCategoriaFragment) fragment).initMovieRV(moviesGlobal);
        }

        else {
            int genId = 0;
            if(category.equals("Action"))
                genId = Constants.ACTION;
            else {
                if(category.equals("Romance"))
                    genId = Constants.ROMANCE;
                else if(category.equals("Thriller"))
                    genId = Constants.THRILLER;
                else if(category.equals("Animation"))
                    genId = Constants.ANIMATION;
            }

            int i = 0;
            //movieFiltered.clear();

            if(anno.equals("") && voto.equals("") && category.equals("") && !order.equals("")) {
                Log.d(TAG, "Entrato1");
                filterOrder(order);
            }

            else if(anno.equals("") && voto.equals("") && !category.equals("") && order.equals("")) {
                Log.d(TAG, "Entrato2");
                filterGen(genId);
            }

            else if(anno.equals("") && voto.equals("") && !category.equals("") && !order.equals("")) {
                Log.d(TAG, "Entrato3");
                filterGen(genId);
                filterOrder(order);
            }
            else if(anno.equals("") && !voto.equals("") && category.equals("") && order.equals("")) {
                Log.d(TAG, "Entrato4");
                filterVote(voto);
            }

            else if(anno.equals("") && !voto.equals("") && category.equals("") && !order.equals("")) {
                Log.d(TAG, "Entrato5");
                filterVote(voto);
                filterOrder(order);
            }
            else if(anno.equals("") && !voto.equals("") && !category.equals("") && order.equals("")) {
                Log.d(TAG, "Entrato6");
                filterVote(voto);
                filterGen(genId);
            }
            else if(anno.equals("") && !voto.equals("") && !category.equals("") && !order.equals("")) {
                Log.d(TAG, "Entrato7");
                filterVote(voto);
                filterGen(genId);
                filterOrder(order);
            }
            else if(!anno.equals("") && voto.equals("") && category.equals("") && order.equals("")) {
                Log.d(TAG, "Entrato8");
                filterYear(anno);
            }

            else if(!anno.equals("") && voto.equals("") && category.equals("") && !order.equals("")) {
                Log.d(TAG, "Entrato9");
                filterYear(anno);
                filterOrder(order);
            }
            else if(!anno.equals("") && voto.equals("") && !category.equals("") && order.equals("")) {
                Log.d(TAG, "Entrato10");
                filterYear(anno);
                filterGen(genId);
            }
            else if(!anno.equals("") && voto.equals("") && !category.equals("") && !order.equals("")) {
                Log.d(TAG, "Entrato11");
                filterYear(anno);
                filterGen(genId);
                filterOrder(order);
            }
            else if(!anno.equals("") && !voto.equals("") && category.equals("") && order.equals("")) {
                Log.d(TAG, "Entrato12");
                filterYear(anno);
                filterVote(voto);
            }
            else if(!anno.equals("") && !voto.equals("") && category.equals("") && !order.equals("")) {
                Log.d(TAG, "Entrato13");
                filterYear(anno);
                filterVote(voto);
                filterOrder(order);
            }
            else if(!anno.equals("") && !voto.equals("") && !category.equals("") && order.equals("")) {
                Log.d(TAG, "Entrato14");
                filterYear(anno);
                filterVote(voto);
                filterGen(genId);
            }
            else if(!anno.equals("") && !voto.equals("") && !category.equals("") && !order.equals("")) {
                Log.d(TAG, "Entrato15");
                filterYear(anno);
                filterVote(voto);
                filterGen(genId);
                filterOrder(order);
            }

            //movieWork.clear();
            //movieWork.addAll(movieFiltered);

            if(fragment instanceof AddListFragment)
                ((AddListFragment) fragment).initMovieRV(moviesGlobal);
            if(fragment instanceof NuoviArriviFragment)
                ((NuoviArriviFragment) fragment).initMovieRV(moviesGlobal);
            if(fragment instanceof SearchFragment)
                ((SearchFragment) fragment).initMovieRV(moviesGlobal);
            if(fragment instanceof ProssimeUsciteFragment)
                ((ProssimeUsciteFragment) fragment).initMovieRV(moviesGlobal);;
            if(fragment instanceof PiuVistiFragment)
                ((PiuVistiFragment) fragment).initMovieRV(moviesGlobal);
            if(fragment instanceof MostraCategoriaFragment)
                ((MostraCategoriaFragment) fragment).initMovieRV(moviesGlobal);
        }
    }
}