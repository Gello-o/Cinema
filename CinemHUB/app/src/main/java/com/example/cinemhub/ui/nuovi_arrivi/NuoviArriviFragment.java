package com.example.cinemhub.ui.nuovi_arrivi;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinemhub.R;
import com.example.cinemhub.menu_items.Refresh;
import com.example.cinemhub.adapter.MoviesAdapter;
import com.example.cinemhub.menu_items.filtri.FilterHandler;
import com.example.cinemhub.model.Movie;
import com.example.cinemhub.menu_items.ricerca.SearchHandler;

import java.util.HashSet;
import java.util.List;


public class NuoviArriviFragment extends Fragment {
    private static final String TAG = "NuoviArriviFragment";
    private NuoviArriviViewModel nuoviArriviViewModel;
    private MoviesAdapter moviesAdapter;
    RecyclerView nuoviArriviRV;
    int lastVisibleItem, totalItemCount, visibleItemCount;
    int threshold = 1;
    FilterHandler filterOperation;
    Refresh refreshOperation;
    private HashSet<Movie> currentList = new HashSet<>();
    List<Movie> globalList;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        nuoviArriviViewModel =
                new ViewModelProvider(this).get(NuoviArriviViewModel.class);
        nuoviArriviRV.setItemAnimator(new DefaultItemAnimator());

        nuoviArriviViewModel.getNuoviArrivi().observe(getViewLifecycleOwner(), new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> set) {
                initMovieRV(set);

                refreshOperation.count();
                currentList.addAll(set);
                Log.d(TAG, "CurrentListSize: "+currentList.size());

                if (filterOperation != null) {
                    filterOperation.setMovie(set);
                    Log.d(TAG, "FilterSetMovie");
                }
                else
                    Log.d(TAG, "FilterOperationNull");

                if(refreshOperation != null && refreshOperation.getCount()==nuoviArriviViewModel.getPage()-1) {
                    refreshOperation.setMovie(currentList);
                    Log.d(TAG, "RefreshSetMovie");
                }
                else
                    Log.d(TAG, "RefreshOperationNull");
                //globalList = set;
            }
        });

        GridLayoutManager layoutManager;

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            layoutManager = new GridLayoutManager(getActivity(), 3);
        else
            layoutManager = new GridLayoutManager(getActivity(), 4);

        nuoviArriviRV.setLayoutManager(layoutManager);

        nuoviArriviRV.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.d(TAG, "entrato in onScrolled");

                totalItemCount = layoutManager.getItemCount();
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                visibleItemCount = layoutManager.getChildCount();

                Log.d(TAG, "total items " + totalItemCount);
                Log.d(TAG, "last visible item " + lastVisibleItem);
                Log.d(TAG, "visible items " + visibleItemCount);

                if(totalItemCount <= (lastVisibleItem + threshold) && dy > 0){
                    Log.d(TAG, "entrato in lazy loading");
                    int page = nuoviArriviViewModel.getPage() + 1;
                    nuoviArriviViewModel.setPage(page);
                    nuoviArriviViewModel.getMoreNuoviArrivi();
                }
            }
        });

        super.onViewCreated(view, savedInstanceState);
    }

    public void initMovieRV (List<Movie> lista) {
        moviesAdapter = new MoviesAdapter(getActivity(), lista);
        moviesAdapter.notifyDataSetChanged();
        nuoviArriviRV.setAdapter(moviesAdapter);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_nuovi_arrivi, container, false);
        nuoviArriviRV = root.findViewById(R.id.recycler_view_nuovi_arrivi);
        setHasOptionsMenu(true);
        return root;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main3, menu);
        SearchHandler searchOperation = new SearchHandler(menu, this);
        filterOperation = new FilterHandler(menu, this);
        refreshOperation = new Refresh(menu, this, filterOperation);
        searchOperation.implementSearch(2);
        filterOperation.implementFilter(2);
        refreshOperation.implementRefresh(2);
        super.onCreateOptionsMenu(menu, inflater);
    }

}