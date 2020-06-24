package com.example.cinemhub.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.cinemhub.ActivityDetail;
import com.example.cinemhub.R;
import com.example.cinemhub.model.Movie;
import com.example.cinemhub.utils.Constants;

import java.util.List;

/*
Adapter per oggetti di tipo Movie: il suo ruolo è quello di gestire la visualizzazione dei film
all'interno della RecyclerView. Ha un onClickListener con un intent che avvia ActivityDetail,
ovvero la classe che contiene tutte le informazioni inerenti al film cliccato
*/

public class MoviesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;
    private List<Movie> movieList;
    private LayoutInflater layoutInflater;

    private static final int MOVIE_VIEW_TYPE = 0;
    private static final int LOADING_VIEW_TYPE = 1;

    private static final String TAG = "MoviesAdapter";

    public MoviesAdapter(Context context, List<Movie> movieList) {
        this.context = context;
        this.movieList = movieList;
        layoutInflater  = LayoutInflater.from(context);
    }

    /*Adatta due differenti viewHolder: uno per i film che stanno per essere caricati (LoadingMoviesViewHolder) e uno per i
    film che sono già stati caricati*/

    @Override
    @NonNull
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType){
        Log.d(TAG, "onCreateViewHolder called");
        if(viewType == MOVIE_VIEW_TYPE) {
            View view = layoutInflater.inflate(R.layout.movie_card, viewGroup, false);
            return new MoviesViewHolder(view);
        }
        else{
            View view = layoutInflater.inflate(R.layout.loading_item, viewGroup, false);
            return new LoadingMoviesViewHolder(view);
        }

    }

    /*Lega il viewHolder al rispettivo layout*/

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int pos){
        Log.d(TAG, "onBindViewHolder called");

        if(viewHolder instanceof MoviesViewHolder) {
            ((MoviesViewHolder)viewHolder).bind(movieList.get(pos));
        }
        else if(viewHolder instanceof LoadingMoviesViewHolder){
            ((LoadingMoviesViewHolder)viewHolder).progressBarLoadingMovie.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        if(movieList != null)
            return movieList.size();
        else
            return 0;
    }

    /*Ci dice se il film è già stato caricato o meno: se non è stato caricato ritorna LOADING_VIEW_TYPE;
    ritorna MOVIE_VIEW_TYPE altrimenti.*/

    @Override
    public int getItemViewType(int position) {
        if(movieList.get(position) == null)
            return LOADING_VIEW_TYPE;
        else
            return MOVIE_VIEW_TYPE;
    }

    public void setData(List<Movie> movies) {
        if (movies != null) {
            this.movieList = movies;
            notifyDataSetChanged();
        }
    }

    /*definizione del viewHolder per gli oggetti Movie di cui abbiamo le informazioni => diversi da null*/

    public class MoviesViewHolder extends RecyclerView.ViewHolder{
        ImageView thumbnail;


        MoviesViewHolder(View view){
            super(view);
            Log.d(TAG, "creating viewHolder for recyclerView");
            thumbnail = view.findViewById(R.id.card_view_thumbnail);

            view.setOnClickListener(v -> {
                Movie clickedDataItem;

                Log.d(TAG, "Clicked");
                int pos = getAdapterPosition();
                if(pos != RecyclerView.NO_POSITION){
                    clickedDataItem = movieList.get(pos);
                    Intent intent = new Intent(context, ActivityDetail.class);
                    intent.putExtra("original_title", movieList.get(pos).getOriginalTitle());
                    intent.putExtra("poster_path", movieList.get(pos).getPosterPath());
                    intent.putExtra("overview", movieList.get(pos).getOverview());
                    intent.putExtra("release_date", movieList.get(pos).getReleaseDate());
                    intent.putExtra("id", Integer.toString(movieList.get(pos).getId()));
                    intent.putExtra("vote_average", Double.toString(movieList.get(pos).getVoteAverage()));
                    intent.putExtra("title", movieList.get(pos).getTitle());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    System.out.println("Lintento" + intent);
                    context.startActivity(intent);
                    Toast.makeText(v.getContext(), "you clicked " + clickedDataItem.getTitle(), Toast.LENGTH_SHORT).show();
                }
            });

        }

        public void bind(Movie m){
            if (m.getPosterPath() == null) {
                thumbnail.setImageResource(R.drawable.image_not_found_card);
            } else {
                Glide.with(context)
                        .load(Constants.BASE_IMAGE_URL + m.getPosterPath())
                        .dontAnimate()
                        .into(thumbnail);
            }
        }
    }

    /*definizione del viewHolder per gli oggetti Movie di cui non abbiamo le informazioni => uguali null*/

    static class LoadingMoviesViewHolder extends RecyclerView.ViewHolder {

        ProgressBar progressBarLoadingMovie;

        LoadingMoviesViewHolder(View view) {
            super(view);
            progressBarLoadingMovie = view.findViewById(R.id.progressBarLoadingMovie1);
        }
    }

}