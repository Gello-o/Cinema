package com.example.cinemhub.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cinemhub.ActivityDetail;
import com.example.cinemhub.R;
import com.example.cinemhub.model.Movie;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder>{

    private Context context;
    private List<Movie> movieList;

    public MoviesAdapter(Context context, List<Movie> movieList) {
        this.context = context;
        this.movieList = movieList;
    }

    @Override
    public MoviesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i){
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_card, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MoviesAdapter.MyViewHolder viewHolder, int i){
        viewHolder.title.setText(movieList.get(i).getOriginal_title());
        String vote = Double.toString(movieList.get(i).getVote_average());
        viewHolder.userrating.setText(vote);

        Glide.with(context)
                .load(movieList.get(i).getPosterPath())
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(viewHolder.thumbnail);


    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView userrating;
        public TextView title;
        public ImageView thumbnail;

        public MyViewHolder(View view){
            super(view);
            title = view.findViewById(R.id.movieTitle);
            userrating = view.findViewById(R.id.usersRating);
            thumbnail = view.findViewById(R.id.thumbnail);

            view.setOnClickListener(new View.OnClickListener() {
                Movie clickedDataItem;

                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                         clickedDataItem = movieList.get(pos);
                         Intent intent = new Intent(context, ActivityDetail.class);
                         intent.putExtra("original_title", movieList.get(pos).getOriginal_title());
                         intent.putExtra("poster_path", movieList.get(pos).getPosterPath());
                         intent.putExtra("overview", movieList.get(pos).getOverview());
                         intent.putExtra("released", movieList.get(pos).getRelease_date());
                         intent.putExtra("vote_average", Double.toString(movieList.get(pos).getVote_average()));
                         intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                         context.startActivity(intent);
                         Toast.makeText(v.getContext(), "you clicked " + clickedDataItem.getOriginal_title(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }
}
