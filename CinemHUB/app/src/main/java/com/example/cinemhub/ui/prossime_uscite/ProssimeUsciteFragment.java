package com.example.cinemhub.ui.prossime_uscite;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinemhub.R;
import com.example.cinemhub.adapter.MoviesAdapter;
import com.example.cinemhub.filtri.FilterHandler;
import com.example.cinemhub.model.Movie;
import com.example.cinemhub.ricerca.SearchHandler;

import java.util.List;

public class ProssimeUsciteFragment extends Fragment {
    private static final String TAG = "ProssimeUsciteFragment";
    private ProssimeUsciteViewModel prossimeUsciteViewModel;
    private MoviesAdapter moviesAdapter;
    RecyclerView prossimeUsciteRV;
    int lastVisibleItem, totalItemCount, visibleItemCount;
    int threshold = 1;
    FilterHandler filterOperation;

    List<Movie> globalList;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        prossimeUsciteViewModel =
                new ViewModelProvider(this).get(ProssimeUsciteViewModel.class);

        prossimeUsciteViewModel.getProssimeUscite().observe(getViewLifecycleOwner(), new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> set) {
                initMoviesRV(set);
                if (filterOperation != null) {
                    filterOperation.setMovie(set);
                } else
                    Log.d(TAG, "FilterOperationNull");
                globalList = set;
            }
        });

        GridLayoutManager layoutManager;

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            layoutManager = new GridLayoutManager(getActivity(), 3);
        else
            layoutManager = new GridLayoutManager(getActivity(), 4);

        prossimeUsciteRV.setLayoutManager(layoutManager);
        prossimeUsciteRV.setItemAnimator(new DefaultItemAnimator());

        prossimeUsciteRV.addOnScrollListener(new RecyclerView.OnScrollListener() {

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
                    int page = prossimeUsciteViewModel.getPage() + 1;
                    prossimeUsciteViewModel.setPage(page);
                    prossimeUsciteViewModel.getMoreProssimeUscite();
                }
            }
        });
    }

    public void initMoviesRV (List<Movie> lista){
        if(moviesAdapter == null)
            moviesAdapter = new MoviesAdapter(getActivity(), lista);
        else
            moviesAdapter.setData(lista);

        prossimeUsciteRV.setAdapter(moviesAdapter);

    }

    public void initMovieRV(List<Movie> movies, Fragment fragment) {
        moviesAdapter = new MoviesAdapter(fragment.getActivity(), movies);
        Log.d(TAG, "primofilm: " + movies.get(0).getVoteAverage());

        RecyclerView.LayoutManager layoutManager;
        if (getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            layoutManager = new GridLayoutManager(getActivity(), 3);
        else
            layoutManager = new GridLayoutManager(getActivity(), 4);
        prossimeUsciteRV.setLayoutManager(layoutManager);
        prossimeUsciteRV.setAdapter(moviesAdapter);
        prossimeUsciteRV.setItemAnimator(new DefaultItemAnimator());

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_nuovi_arrivi, container, false);
        prossimeUsciteRV = root.findViewById(R.id.recycler_view_nuovi_arrivi);
        setHasOptionsMenu(true);
        return root;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main3, menu);
        SearchHandler searchOperation = new SearchHandler(menu, this);
        filterOperation = new FilterHandler(menu, this);
        searchOperation.implementSearch(2);
        filterOperation.implementFilter(2);
        super.onCreateOptionsMenu(menu, inflater);
    }
}
