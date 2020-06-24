package com.example.cinemhub.ui.categorie;

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


public class MostraCategoriaFragment extends Fragment {
    private static final String TAG = "MostraCategoriaFragment";
    private MostraCategoriaViewModel mostraCategoriaViewModel;
    private MoviesAdapter moviesAdapter;
    private RecyclerView mostraCategoriaRV;
    private int genere;
    private int totalItemCount;
    private int lastVisibleItem;
    private int visibleItemCount;
    private int threshold = 1;
    private List<Movie> currentMovies;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_genere, container, false);
        mostraCategoriaRV = root.findViewById(R.id.recycler_genere);
        genere = MostraCategoriaFragmentArgs.fromBundle(requireArguments()).getGenere();
        setHasOptionsMenu(true);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mostraCategoriaViewModel =
                new ViewModelProvider(this).get(MostraCategoriaViewModel.class);

        GridLayoutManager layoutManager;

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            layoutManager = new GridLayoutManager(getActivity(), 3);
        else
            layoutManager = new GridLayoutManager(getActivity(), 4);

        mostraCategoriaRV.setLayoutManager(layoutManager);
        mostraCategoriaRV.setItemAnimator(new DefaultItemAnimator());

        moviesAdapter = new MoviesAdapter(getActivity(), getMovies());
        mostraCategoriaRV.setAdapter(moviesAdapter);

        mostraCategoriaRV.addOnScrollListener(new RecyclerView.OnScrollListener() {

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
                        (totalItemCount <= (lastVisibleItem + threshold) && dy > 0  && !mostraCategoriaViewModel.isLoading()) &&
                                mostraCategoriaViewModel.getMoviesLiveData().getValue() != null &&
                                mostraCategoriaViewModel.getCurrentResults() != mostraCategoriaViewModel.getMoviesLiveData().getValue().getTotalResults())
                        && mostraCategoriaViewModel.canLoad()
                ) {
                    Resource<List<Movie>> moviesResource = new Resource<>();

                    MutableLiveData<Resource<List<Movie>>> moviesMutableLiveData = mostraCategoriaViewModel.getMoviesLiveData();

                    if (moviesMutableLiveData.getValue() != null) {

                        mostraCategoriaViewModel.setLoading(true);

                        List<Movie> currentMovies = moviesMutableLiveData.getValue().getData();

                        // It adds a null element to enable the visualization of the loading item (it is managed by the class MostraCategoriaAdapter)
                        currentMovies.add(null);
                        moviesResource.setData(currentMovies);
                        moviesResource.setStatusMessage(moviesMutableLiveData.getValue().getStatusMessage());
                        moviesResource.setTotalResults(moviesMutableLiveData.getValue().getTotalResults());
                        moviesResource.setStatusCode(moviesMutableLiveData.getValue().getStatusCode());
                        Log.d(TAG, "STO CARICANDO");
                        moviesResource.setLoading(true);
                        moviesMutableLiveData.postValue(moviesResource);

                        int page = mostraCategoriaViewModel.getPage() + 1;
                        mostraCategoriaViewModel.setPage(page);

                        mostraCategoriaViewModel.getMoreGenere();
                    }
                }
            }
        });

        mostraCategoriaViewModel.getGenere(genere).observe(getViewLifecycleOwner(), resource -> {
            if(resource!=null && resource.getData() != null) {

                if(mostraCategoriaViewModel.getFiltered() == null)
                    moviesAdapter.setData(resource.getData());
                else
                    moviesAdapter.setData(mostraCategoriaViewModel.getFiltered());

                currentMovies = resource.getData();

                if(resource.getData().size() < 20)
                    mostraCategoriaViewModel.setCanLoad(false);

                Log.d(TAG, "CurrentListSize: "+resource.getData().size());


                if (!resource.isLoading() /*&& canLoad*/) {
                    Log.d(TAG, "STA CARICANDO");
                    mostraCategoriaViewModel.setLoading(false);
                    if (resource.getData() != null) {
                        mostraCategoriaViewModel.setCurrentResults(resource.getData().size());
                    }
                }
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main3, menu);
        mostraCategoriaViewModel.initFilters(menu, this);
        mostraCategoriaViewModel.initSearch(menu, this);
        Refresh refresh = new Refresh(menu, this);
        refresh.implementRefresh(2);
        super.onCreateOptionsMenu(menu, inflater);
    }


    private List<Movie> getMovies() {

        Resource<List<Movie>> moviesResource = mostraCategoriaViewModel.getGenere(genere).getValue();

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
        mostraCategoriaRV.setAdapter(moviesAdapter);
        moviesAdapter.notifyDataSetChanged();
    }

    public void setCanLoad(boolean canLoad){
        mostraCategoriaViewModel.setCanLoad(canLoad);
        mostraCategoriaViewModel.setFiltered(null);
    }

    public void saveFiltered(List <Movie> filtered){
        mostraCategoriaViewModel.setFiltered(filtered);
    }
}