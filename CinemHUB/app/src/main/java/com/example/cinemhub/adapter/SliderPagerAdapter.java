package com.example.cinemhub.adapter;

import android.content.Context;

/*
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
*/

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

/*
import com.blogapp.aws.movieuitemplate.R;
import com.blogapp.aws.movieuitemplate.models.Slide;
 */
import com.bumptech.glide.Glide;
import com.example.cinemhub.ActivityDetail;
import com.example.cinemhub.R;
import com.example.cinemhub.model.Movie;
import com.example.cinemhub.model.Slide;

import java.util.List;

public class SliderPagerAdapter extends PagerAdapter {

    private static final String TAG = "SliderPagerAdapter";
    private Context mContext ;
    private List<Movie> mList ;
    private final String base_image_Url = "https://image.tmdb.org/t/p/w500";


    public SliderPagerAdapter(Context mContext, List<Movie> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {


        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View slideLayout = inflater.inflate(R.layout.slide_item,null);

        ImageView slideImg = slideLayout.findViewById(R.id.slide_img);
        TextView slideText = slideLayout.findViewById(R.id.slide_title);

        if(mList.get(position) != null){
            Glide.with(mContext)
                    .load(base_image_Url+mList.get(position).getPosterPath())
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(slideImg);
            slideText.setText(mList.get(position).getTitle());
        }
        else {
            slideImg.setImageResource(R.drawable.ic_launcher_background);
        }

        slideImg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Movie clickedDataItem;

                Log.d(TAG, "Clicked");
                if(position != RecyclerView.NO_POSITION){
                    clickedDataItem = mList.get(position);
                    Intent intent = new Intent(mContext, ActivityDetail.class);
                    intent.putExtra("original_title", mList.get(position).getOriginalTitle());
                    intent.putExtra("poster_path", mList.get(position).getPosterPath());
                    intent.putExtra("overview", mList.get(position).getOverview());
                    intent.putExtra("release_date", mList.get(position).getReleaseDate());
                    intent.putExtra("id", Integer.toString(mList.get(position).getId()));
                    intent.putExtra("vote_average", Double.toString(mList.get(position).getVoteAverage()));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                    Toast.makeText(v.getContext(), "you clicked " + clickedDataItem.getOriginalTitle(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        container.addView(slideLayout);


        return slideLayout;

    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

}