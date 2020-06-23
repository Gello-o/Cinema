package com.example.cinemhub.ui.prossime_uscite;

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
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinemhub.model.Resource;
import com.example.cinemhub.R;
import com.example.cinemhub.adapter.MoviesAdapter;
import com.example.cinemhub.menu_items.Refresh;
import com.example.cinemhub.model.Movie;


import java.util.List;

public class ProssimeUsciteFragment extends Fragment {
    private static final String TAG = "ProssimeUscite";
    private ProssimeUsciteViewModel prossimeUsciteViewModel;
    private MoviesAdapter moviesAdapter;
    private RecyclerView prossimeUsciteRV;
    private int totalItemCount;
    private int lastVisibleItem;
    private int visibleItemCount;
    private int threshold = 1;
    private List<Movie> currentMovies;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_prossime_uscite, container, false);
        prossimeUsciteRV = root.findViewById(R.id.recycler_view_prossime_uscite);
        setHasOptionsMenu(true);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        prossimeUsciteViewModel =
                new ViewModelProvider(this).get(ProssimeUsciteViewModel.class);

        GridLayoutManager layoutManager;

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            layoutManager = new GridLayoutManager(getActivity(), 3);
        else
            layoutManager = new GridLayoutManager(getActivity(), 4);

        prossimeUsciteRV.setLayoutManager(layoutManager);
        prossimeUsciteRV.setItemAnimator(new DefaultItemAnimator());

        moviesAdapter = new MoviesAdapter(getActivity(), getMovies());
        prossimeUsciteRV.setAdapter(moviesAdapter);

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

                if ((totalItemCount == visibleItemCount ||
                        (totalItemCount <= (lastVisibleItem + threshold) && dy > 0  && !prossimeUsciteViewModel.isLoading()) &&
                                prossimeUsciteViewModel.getMoviesLiveData().getValue() != null &&
                                prossimeUsciteViewModel.getCurrentResults() != prossimeUsciteViewModel.getMoviesLiveData().getValue().getTotalResults())
                        && prossimeUsciteViewModel.canLoad()
                ) {
                    Resource<List<Movie>> moviesResource = new Resource<>();

                    MutableLiveData<Resource<List<Movie>>> moviesMutableLiveData = prossimeUsciteViewModel.getMoviesLiveData();

                    if (moviesMutableLiveData.getValue() != null) {

                        prossimeUsciteViewModel.setLoading(true);

                        List<Movie> currentMovies = moviesMutableLiveData.getValue().getData();

                        // It adds a null element to enable the visualization of the loading item (it is managed by the class NuoviArriviAdapter)
                        currentMovies.add(null);
                        moviesResource.setData(currentMovies);
                        moviesResource.setStatusMessage(moviesMutableLiveData.getValue().getStatusMessage());
                        moviesResource.setTotalResults(moviesMutableLiveData.getValue().getTotalResults());
                        moviesResource.setStatusCode(moviesMutableLiveData.getValue().getStatusCode());
                        Log.d(TAG, "STO CARICANDO");
                        moviesResource.setLoading(true);
                        moviesMutableLiveData.postValue(moviesResource);

                        int page = prossimeUsciteViewModel.getPage() + 1;
                        prossimeUsciteViewModel.setPage(page);

                        prossimeUsciteViewModel.getMoreProssimeUscite();
                    }
                }
            }
        });

        prossimeUsciteViewModel.getProssimeUscite().observe(getViewLifecycleOwner(), resource -> {
            if(resource!=null && resource.getData()!=null){
                moviesAdapter.setData(resource.getData());
                currentMovies = resource.getData();

                if(resource.getData().size() < 20)
                    prossimeUsciteViewModel.setCanLoad(false);

                Log.d(TAG, "CurrentListSize: "+resource.getData().size());

                if (!resource.isLoading() /*&& canLoad*/) {
                    Log.d(TAG, "STA CARICANDO");
                    prossimeUsciteViewModel.setLoading(false);
                    if (resource.getData() != null) {
                        prossimeUsciteViewModel.setCurrentResults(resource.getData().size());
                    }
                }
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main3, menu);
        prossimeUsciteViewModel.initSearch(menu, this);
        prossimeUsciteViewModel.initFilters(menu, this);
        Refresh refreshOperation = new Refresh(menu, this);
        refreshOperation.implementRefresh(2);
        super.onCreateOptionsMenu(menu, inflater);
    }


    private List<Movie> getMovies() {

        Resource<List<Movie>> moviesResource = prossimeUsciteViewModel.getProssimeUscite().getValue();

        if (moviesResource != null) {
            return moviesResource.getData();
        }

        return null;
    }


    public List<Movie> getCurrentMovies() {
        return currentMovies;
    }

    public void initMovieRV (List<Movie> lista) {
        Log.d(TAG, "size " + lista.size());
        moviesAdapter = new MoviesAdapter(getActivity(), lista);
        prossimeUsciteRV.setAdapter(moviesAdapter);
        moviesAdapter.notifyDataSetChanged();
    }

    public void setCanLoad(boolean canLoad) {
        prossimeUsciteViewModel.setCanLoad(canLoad);
    }

    @Override
    public void onStop() {
        super.onStop();
        if(!prossimeUsciteViewModel.canLoad()){
            prossimeUsciteViewModel.setCanLoad(true);
        }
    }
}