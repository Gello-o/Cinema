package com.example.cinemhub.ui.preferiti;

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
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.cinemhub.R;
import com.example.cinemhub.ricerca.SearchHandler;
import com.example.cinemhub.adapter.MoviesAdapter;
import com.example.cinemhub.model.Favorite;
import com.example.cinemhub.model.FavoriteDB;
import com.example.cinemhub.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class PreferitiFragment extends Fragment {

    private static final String TAG = "PreferitiFragment";
    RecyclerView preferitiRV;
    MoviesAdapter moviesAdapter;
    List<Favorite> favoriteList;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        GridLayoutManager layoutManager;

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            layoutManager = new GridLayoutManager(getActivity(), 3);
        else
            layoutManager = new GridLayoutManager(getActivity(), 4);

        preferitiRV.setLayoutManager(layoutManager);
        preferitiRV.setItemAnimator(new DefaultItemAnimator());
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_preferiti, container, false);
        preferitiRV = root.findViewById(R.id.recycler_view_preferiti);
        setHasOptionsMenu(true);
        return root;
    }

    public List <Movie> queryFavoriteDB(){
        String title, posterPath, plotSynopsis, originalTitle, releaseDate;
        int id, genre, voteCount;
        Double userRating;
        Movie movie;
        List<Movie> lista = new ArrayList<>();
        Log.d(TAG, ""+favoriteList.size());
        if(!favoriteList.isEmpty()) {

            for (Favorite fav : favoriteList) {
                id = fav.getMovieId();
                Log.d(TAG, "id "+id);
                title = fav.getTitle();
                Log.d(TAG, "title "+title);
                userRating = Double.parseDouble(fav.getUserRating());
                Log.d(TAG, "rating "+userRating);
                posterPath = fav.getPosterPath();
                Log.d(TAG, "posterpath "+posterPath);
                plotSynopsis = fav.getPlotSynopsys();
                Log.d(TAG, "synopsis " + plotSynopsis);
                releaseDate = fav.getReleaseDate();
                originalTitle = fav.getOriginalTitle();
                try {
                    voteCount = Integer.parseInt(fav.getVoteCount());

                }catch (NumberFormatException e){
                    voteCount = 0;
                }
                try {
                    genre = Integer.parseInt(fav.getGenreId());
                }catch (NumberFormatException e){
                    genre = 0;
                }


                movie = new Movie(posterPath, plotSynopsis, releaseDate, id, originalTitle, title, voteCount, userRating, genre);
                lista.add(movie);

                Log.d(TAG, "Database: " +
                        "ID: " + id +
                        "id movie: " + movie.getTitle());
            }
        }
        else{
            Log.d(TAG, "log else");
        }
        return lista;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        SearchHandler searchOperation = new SearchHandler(menu, this);
        searchOperation.implementSearch(1);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public void initMovies(List<Movie> movies){
        moviesAdapter = new MoviesAdapter(getActivity(), movies);
        moviesAdapter.notifyDataSetChanged();
        preferitiRV.setAdapter(moviesAdapter);
    }

    @Override
    public void onPause() {
        super.onPause();
        favoriteList.clear();
    }

    @Override
    public void onStop() {
        super.onStop();
        favoriteList.clear();
    }

    @Override
    public void onResume() {
        super.onResume();
        favoriteList = FavoriteDB.getInstance().dbInterface().getFavorite();
        initMovies(queryFavoriteDB());
    }
}