package com.example.cinemhub.ui.piu_visti;

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
import com.example.cinemhub.Refresh;
import com.example.cinemhub.adapter.MoviesAdapter;
import com.example.cinemhub.filtri.FilterHandler;
import com.example.cinemhub.model.Movie;
import com.example.cinemhub.ricerca.SearchHandler;

import java.util.HashSet;
import java.util.List;

public class PiuVistiFragment extends Fragment {
    private static final String TAG = "PiuVistiFragment";
    private PiuVistiViewModel piuVistiViewModel;
    private MoviesAdapter moviesAdapter;
    RecyclerView prossimeUsciteRV;
    int lastVisibleItem, totalItemCount, visibleItemCount;
    int threshold = 1;
    FilterHandler filterOperation;
    Refresh refreshOperation;
    private HashSet<Movie> currentList2 = new HashSet<>();
    List<Movie> globalList;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        piuVistiViewModel =
                new ViewModelProvider(this).get(PiuVistiViewModel.class);

        prossimeUsciteRV.setItemAnimator(new DefaultItemAnimator());

        piuVistiViewModel.getPiuVisti().observe(getViewLifecycleOwner(), new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> set) {
                initMovieRV(set);

                refreshOperation.count();
                currentList2.addAll(set);
                Log.d(TAG, "CurrentListSize: "+currentList2.size());

                if (filterOperation != null) {
                    filterOperation.setMovie(set);
                    Log.d(TAG, "FilterSetMovie");
                }
                else
                    Log.d(TAG, "FilterOperationNull");

                if(refreshOperation != null && refreshOperation.getCount()==piuVistiViewModel.getPage()-1) {
                    refreshOperation.setMovie(currentList2);
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

        prossimeUsciteRV.setLayoutManager(layoutManager);

        super.onViewCreated(view, savedInstanceState);


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
                    int page = piuVistiViewModel.getPage() + 1;
                    piuVistiViewModel.setPage(page);
                    piuVistiViewModel.getMorePiuVisti();
                }
            }
        });
    }

    public void initMovieRV (List<Movie> lista){
        moviesAdapter = new MoviesAdapter(getActivity(), lista);
        moviesAdapter.notifyDataSetChanged();
        prossimeUsciteRV.setAdapter(moviesAdapter);
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
        refreshOperation = new Refresh(menu, this);
        searchOperation.implementSearch(2);
        filterOperation.implementFilter(2);
        refreshOperation.implementRefresh(2);
        super.onCreateOptionsMenu(menu, inflater);
    }
}
