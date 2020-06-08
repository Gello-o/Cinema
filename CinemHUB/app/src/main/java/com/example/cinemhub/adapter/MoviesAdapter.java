package com.example.cinemhub.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.RawRes;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.cinemhub.ActivityDetail;
import com.example.cinemhub.R;
import com.example.cinemhub.model.Movie;
import com.example.cinemhub.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;
    private List<Movie> movieList;
    LayoutInflater layoutInflater;

    private static final int ARTICLE_VIEW_TYPE = 0;
    private static final int LOADING_VIEW_TYPE = 1;

    private static final String TAG = "MoviesAdapter";

    public MoviesAdapter(Context context, List<Movie> movieList) {
        this.context = context;
        this.movieList = movieList;
        layoutInflater  = layoutInflater.from(context);
    }

    @Override
    @NonNull
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i){
        Log.d(TAG, "onCreateViewHolder called");
        if(i == ARTICLE_VIEW_TYPE) {
            View view = layoutInflater.inflate(R.layout.movie_card, viewGroup, false);
            return new MoviesViewHolder(view);
        }
        else{
            View view = layoutInflater.inflate(R.layout.loading_item, viewGroup, false);
            return new LoadingMoviesViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i){
        Log.d(TAG, "onBindViewHolder called");

        if(movieList.isEmpty())
            Log.d(TAG, "movieList nulla");

        if(viewHolder instanceof MoviesViewHolder) {
            ((MoviesViewHolder) viewHolder).bind(movieList.get(i));
        }
        else if(viewHolder instanceof LoadingMoviesViewHolder){
            ((LoadingMoviesViewHolder) viewHolder).progressBarLoadingMovie.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        if(movieList != null)
            return movieList.size();
        else
            return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if(movieList.get(position) == null)
            return LOADING_VIEW_TYPE;
        else
            return ARTICLE_VIEW_TYPE;
    }

    public void setData(List<Movie> movies) {
        if (movies != null && !movies.isEmpty()) {
            this.movieList = movies;
            notifyDataSetChanged();
        }
        else
            this.movieList = new ArrayList<>();
    }

    public List<Movie> getData() {
        return movieList;
    }

    public class MoviesViewHolder extends RecyclerView.ViewHolder{
        ImageView thumbnail;


        public MoviesViewHolder(View view){
            super(view);
            Log.d(TAG, "creating viewHolder for recyclerView");
            thumbnail = view.findViewById(R.id.card_view_thumbnail);

            view.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
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
                }
            });

        }

        public void bind(Movie m){
            if (m.getPosterPath() == null) {
                thumbnail.setImageResource(R.drawable.image_not_found);
            } else {
                Glide.with(context)
                        .load(Constants.BASE_IMAGE_URL + m.getPosterPath())
                        .dontAnimate()
                        .into(thumbnail);
            }
        }
    }

    static class LoadingMoviesViewHolder extends RecyclerView.ViewHolder {

        ProgressBar progressBarLoadingMovie;

        LoadingMoviesViewHolder(View view) {
            super(view);
            progressBarLoadingMovie = view.findViewById(R.id.progressBarLoadingMovie1);
        }
    }

}