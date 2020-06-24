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


public class NuoviArriviFragment extends Fragment {
    private static final String TAG = "NuoviArriviFragment";
    private NuoviArriviViewModel nuoviArriviViewModel;
    private MoviesAdapter moviesAdapter;
    private RecyclerView prossimeUsciteRV;
    private int totalItemCount;
    private int lastVisibleItem;
    private int visibleItemCount;
    private int threshold = 1;
    private List<Movie> currentMovies;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_nuovi_arrivi, container, false);
        prossimeUsciteRV = root.findViewById(R.id.recycler_view_nuovi_arrivi);
        setHasOptionsMenu(true);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        nuoviArriviViewModel =
                new ViewModelProvider(this).get(NuoviArriviViewModel.class);

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
                        (totalItemCount <= (lastVisibleItem + threshold) && dy > 0  && !nuoviArriviViewModel.isLoading()) &&
                                nuoviArriviViewModel.getMoviesLiveData().getValue() != null &&
                                nuoviArriviViewModel.getCurrentResults() != nuoviArriviViewModel.getMoviesLiveData().getValue().getTotalResults())
                                && nuoviArriviViewModel.canLoad()
                ) {
                    Resource<List<Movie>> moviesResource = new Resource<>();

                    MutableLiveData<Resource<List<Movie>>> moviesMutableLiveData = nuoviArriviViewModel.getMoviesLiveData();

                    if (moviesMutableLiveData.getValue() != null) {

                        nuoviArriviViewModel.setLoading(true);

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

                        int page = nuoviArriviViewModel.getPage() + 1;
                        nuoviArriviViewModel.setPage(page);

                        nuoviArriviViewModel.getMoreProssimeUscite();
                    }
                }
            }
        });

        nuoviArriviViewModel.getProssimeUscite().observe(getViewLifecycleOwner(), resource -> {

            if(resource != null) {
                moviesAdapter.setData(resource.getData());
                currentMovies = resource.getData();

                if(resource.getData().size() < 20)
                    nuoviArriviViewModel.setCanLoad(false);

                Log.d(TAG, "CurrentListSize: " + resource.getData().size());

                if (!resource.isLoading() /*&& nuoviArriviViewModel.canLoad()*/) {
                    Log.d(TAG, "STA CARICANDO");
                    nuoviArriviViewModel.setLoading(false);
                    if (resource.getData() != null) {
                        nuoviArriviViewModel.setCurrentResults(resource.getData().size());
                    }
                }
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main3, menu);
        nuoviArriviViewModel.initFilters(menu, this);
        nuoviArriviViewModel.initSearch(menu, this);
        Refresh refreshOperation = new Refresh(menu, this);
        refreshOperation.implementRefresh(2);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onStop() {
        super.onStop();
        if(!nuoviArriviViewModel.canLoad()){
            nuoviArriviViewModel.setCanLoad(true);
        }
    }

    private List<Movie> getMovies() {

        Resource<List<Movie>> moviesResource = nuoviArriviViewModel.getProssimeUscite().getValue();

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

    public void setCanLoad(boolean canLoad){
        nuoviArriviViewModel.setCanLoad(canLoad);
    }
}