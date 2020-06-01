package com.example.cinemhub.ui.nuovi_arrivi;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinemhub.R;
import com.example.cinemhub.adapter.MoviesAdapter;
import com.example.cinemhub.model.Movie;
import com.example.cinemhub.ricerca.SearchHandler;

import java.util.List;


public class NuoviArriviFragment extends Fragment {
    private static final String TAG = "NuoviArriviFragment";
    private NuoviArriviViewModel nuoviArriviViewModel;
    private MoviesAdapter moviesAdapter;
    RecyclerView prossimeUsciteRV;
    int currentItems, totalItemCount, scrolledOutItems;
    private boolean isScrolling = false;
    ProgressBar progressBar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_nuovi_arrivi, container, false);
        progressBar = root.findViewById(R.id.progressBarLoadingMovie);
        prossimeUsciteRV = root.findViewById(R.id.recycler_view_nuovi_arrivi);

        nuoviArriviViewModel =
        new ViewModelProvider(this).get(NuoviArriviViewModel.class);

        nuoviArriviViewModel.getProssimeUscite().observe(getViewLifecycleOwner(), new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> set) {
                initMoviesRV(set);
            }
        });

        setHasOptionsMenu(true);

        return root;
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main3, menu);
        SearchHandler searchOperation = new SearchHandler(menu, this);
        searchOperation.implementSearch(2);
        super.onCreateOptionsMenu(menu, inflater);
    }



    public void initMoviesRV (List<Movie> lista){

        if(moviesAdapter == null)
            moviesAdapter = new MoviesAdapter(getActivity(), lista);
        else
            moviesAdapter.setData(lista);

        GridLayoutManager layoutManager;

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            layoutManager = new GridLayoutManager(getActivity(), 3);
        else
            layoutManager = new GridLayoutManager(getActivity(), 4);

        prossimeUsciteRV.setAdapter(moviesAdapter);
        prossimeUsciteRV.setItemAnimator(new DefaultItemAnimator());

        prossimeUsciteRV.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                scrolledOutItems = layoutManager.findFirstVisibleItemPosition();
                totalItemCount = layoutManager.getItemCount();

                if (isScrolling && (currentItems+scrolledOutItems) == 12){
                    Log.d(TAG, "DENTRO");
                    isScrolling = false;
                    int page = nuoviArriviViewModel.getPage()+1;
                    nuoviArriviViewModel.setPage(page);
                    progressBar.setVisibility(View.VISIBLE);
                    nuoviArriviViewModel.getMoreProssimeUscite();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }
}