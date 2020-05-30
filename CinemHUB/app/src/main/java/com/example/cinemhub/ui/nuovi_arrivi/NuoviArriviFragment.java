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
    private int totalItemCount;
    private int lastVisibleItem;
    private int visibleItemCount;
    private int threshold = 1;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_nuovi_arrivi, container, false);

        prossimeUsciteRV = root.findViewById(R.id.recycler_view_nuovi_arrivi);

        nuoviArriviViewModel =
        new ViewModelProvider(this).get(NuoviArriviViewModel.class);

        nuoviArriviViewModel.getProssimeUscite().observe(getViewLifecycleOwner(), new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> set) {
                initMoviesRV(set);
                if(!nuoviArriviViewModel.isLoading()){

                }
                moviesAdapter.notifyDataSetChanged();
            }
        });

        setHasOptionsMenu(true);

        return root;
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main3, menu);
        SearchHandler searchOperation = new SearchHandler(menu, this);
        searchOperation.implementSearch();
        super.onCreateOptionsMenu(menu, inflater);
    }



    public void initMoviesRV (List<Movie> lista){

        moviesAdapter = new MoviesAdapter(getActivity(), lista);
        RecyclerView.LayoutManager layoutManager;

        if(getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            layoutManager = new GridLayoutManager(getActivity(), 3);
        else
            layoutManager = new GridLayoutManager(getActivity(), 4);

        prossimeUsciteRV.setAdapter(moviesAdapter);
        prossimeUsciteRV.setItemAnimator(new DefaultItemAnimator());

        prossimeUsciteRV.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                totalItemCount = layoutManager.getItemCount();
                lastVisibleItem = ((GridLayoutManager)recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                visibleItemCount = layoutManager.getChildCount();

                if (totalItemCount == visibleItemCount || (
                        totalItemCount <= (lastVisibleItem + threshold) && dy > 0 && !nuoviArriviViewModel.isLoading()) &&
                        nuoviArriviViewModel.getProssimeUscite().getValue() != null &&
                        nuoviArriviViewModel.getCurrentResults() != nuoviArriviViewModel.getProssimeUscite().getValue().size()){

                    MutableLiveData<List<Movie>> moviesData = nuoviArriviViewModel.getProssimeUscite();

                    if(moviesData.getValue() != null){
                        List<Movie> movies = moviesData.getValue();
                        movies.add(null);
                        nuoviArriviViewModel.setLoading(true);
                        int page = nuoviArriviViewModel.getPage() + 1;
                        nuoviArriviViewModel.setPage(page);
                        nuoviArriviViewModel.getProssimeUscite();
                    }
                }
            }
        });
    }

}