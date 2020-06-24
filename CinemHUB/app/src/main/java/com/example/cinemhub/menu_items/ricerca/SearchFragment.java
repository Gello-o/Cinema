package com.example.cinemhub.menu_items.ricerca;

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

/*
fragment adibito alla ricerca: mostra gli oggetti di tipo Movie
restituiti dalla ricerca in un layout a griglia. Implementa il lazy loading
Dà la possibilità all'utente di filtrare i film all'interno della griglia
*/

public class SearchFragment extends Fragment {
    private static final String TAG = "SearchFragment";
    private SearchViewModel ricercaViewModel;
    private MoviesAdapter moviesAdapter;
    private RecyclerView ricercaRV;
    private String query;
    private int totalItemCount;
    private int lastVisibleItem;
    private int visibleItemCount;
    private int threshold = 1;
    private List<Movie> currentMovies;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.search, container, false);
        ricercaRV = root.findViewById(R.id.recycler_view_ricerca);
        query = SearchFragmentArgs.fromBundle(requireArguments()).getQuery();
        Log.d(TAG, "QUERY " + query);
        setHasOptionsMenu(true);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ricercaViewModel =
                new ViewModelProvider(this).get(SearchViewModel.class);

        GridLayoutManager layoutManager;

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            layoutManager = new GridLayoutManager(getActivity(), 3);
        else
            layoutManager = new GridLayoutManager(getActivity(), 4);

        ricercaRV.setLayoutManager(layoutManager);
        ricercaRV.setItemAnimator(new DefaultItemAnimator());

        moviesAdapter = new MoviesAdapter(getActivity(), getMovies());
        ricercaRV.setAdapter(moviesAdapter);

        ricercaRV.addOnScrollListener(new RecyclerView.OnScrollListener() {

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
                        (totalItemCount <= (lastVisibleItem + threshold) && dy > 0  && !ricercaViewModel.isLoading()) &&
                                ricercaViewModel.getMoviesLiveData().getValue() != null &&
                                ricercaViewModel.getCurrentResults() != ricercaViewModel.getMoviesLiveData().getValue().getTotalResults())
                        && ricercaViewModel.canLoad()
                ) {
                    Resource<List<Movie>> moviesResource = new Resource<>();

                    MutableLiveData<Resource<List<Movie>>> moviesMutableLiveData = ricercaViewModel.getMoviesLiveData();

                    if (moviesMutableLiveData.getValue() != null) {

                        ricercaViewModel.setLoading(true);

                        List<Movie> currentMovies = moviesMutableLiveData.getValue().getData();

                        // It adds a null element to enable the visualization of the loading item (it is managed by the class SearchAdapter)
                        currentMovies.add(null);
                        moviesResource.setData(currentMovies);
                        moviesResource.setStatusMessage(moviesMutableLiveData.getValue().getStatusMessage());
                        moviesResource.setTotalResults(moviesMutableLiveData.getValue().getTotalResults());
                        moviesResource.setStatusCode(moviesMutableLiveData.getValue().getStatusCode());
                        Log.d(TAG, "STO CARICANDO");
                        moviesResource.setLoading(true);
                        moviesMutableLiveData.postValue(moviesResource);

                        int page = ricercaViewModel.getPage() + 1;
                        ricercaViewModel.setPage(page);

                        ricercaViewModel.searchMore();
                    }
                }
            }
        });

        ricercaViewModel.doSearch(query).observe(getViewLifecycleOwner(), resource -> {

            if(resource != null && resource.getData() != null){

                if(ricercaViewModel.getFiltered() == null)
                    moviesAdapter.setData(resource.getData());
                else
                    moviesAdapter.setData(ricercaViewModel.getFiltered());

                currentMovies = resource.getData();

                if(currentMovies.size() < 20)
                    setCanLoad(false);
                else
                    setCanLoad(true);

                Log.d(TAG, "CurrentListSize: "+resource.getData().size());

                if (!resource.isLoading() /*&& canLoad*/) {
                    Log.d(TAG, "STA CARICANDO");
                    ricercaViewModel.setLoading(false);
                    if (resource.getData() != null) {
                        ricercaViewModel.setCurrentResults(resource.getData().size());
                    }
                }
            }

        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main2, menu);
        ricercaViewModel.initFilters(menu, this);
        Refresh refreshOperation = new Refresh(menu, this);
        refreshOperation.implementRefresh(1);
        super.onCreateOptionsMenu(menu, inflater);
    }


    private List<Movie> getMovies() {

        Resource<List<Movie>> moviesResource = ricercaViewModel.doSearch(query).getValue();

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
        ricercaRV.setAdapter(moviesAdapter);
        moviesAdapter.notifyDataSetChanged();
    }

    public void setCanLoad(boolean canLoad) {
        ricercaViewModel.setCanLoad(canLoad);
        ricercaViewModel.setFiltered(null);
    }

    public void saveFiltered(List <Movie> filtered){
        ricercaViewModel.setFiltered(filtered);
    }
}