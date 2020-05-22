package com.example.cinemhub.ui.preferiti;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
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

import com.example.cinemhub.MainActivity;
import com.example.cinemhub.R;
import com.example.cinemhub.adapter.MoviesAdapter;
import com.example.cinemhub.model.Favorite;
import com.example.cinemhub.model.FavoriteDB;
import com.example.cinemhub.model.Movie;
import com.example.cinemhub.ui.piu_visti.PiuVistiViewModel;

import java.util.ArrayList;
import java.util.List;

public class PreferitiFragment extends Fragment {

    private static final String TAG = "PreferitiFragment";
    RecyclerView preferitiRV;
    MoviesAdapter moviesAdapter;
    List<Favorite> favoriteList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_preferiti, container, false);
        preferitiRV = root.findViewById(R.id.recycler_view_preferiti);

        favoriteList = FavoriteDB.getInstance().dbInterface().getFavorite();

        if(favoriteList == null)
            Log.d(TAG, "fav uguale null");

        initMoviesRV(queryFavoriteDB());

        return root;
    }

    public void initMoviesRV(List<Movie> lista){
        moviesAdapter = new MoviesAdapter(getActivity(), lista);
        if(moviesAdapter == null)
            Log.d(TAG, "adapter null");
        else {
            if (moviesAdapter.getMovieList() == null)
                Log.d(TAG, "lista null");
            if (moviesAdapter.getContext() == null)
                Log.d(TAG, "contesto null");
        }
        RecyclerView.LayoutManager layoutManager;
        if(getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            layoutManager = new GridLayoutManager(getActivity(), 3);
        else
            layoutManager = new GridLayoutManager(getActivity(), 4);
        preferitiRV.setLayoutManager(layoutManager);
        preferitiRV.setAdapter(moviesAdapter);
        preferitiRV.setItemAnimator(new DefaultItemAnimator());
        moviesAdapter.notifyDataSetChanged();
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
                Log.d(TAG, ""+id);
                title = fav.getTitle();
                Log.d(TAG, ""+title);
                userRating = Double.parseDouble(fav.getUserRating());
                Log.d(TAG, ""+userRating);
                posterPath = fav.getPosterPath();
                Log.d(TAG, ""+posterPath);
                plotSynopsis = fav.getPlotSynopsys();
                Log.d(TAG, ""+plotSynopsis.substring(0, 8));
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
            Log.d(TAG, "CIAO BELO");
        }
        return lista;
    }

}