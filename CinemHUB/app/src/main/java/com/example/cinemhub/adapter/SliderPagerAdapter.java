package com.example.cinemhub.adapter;

import android.content.Context;

/*
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
*/

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
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
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.cinemhub.ActivityDetail;
import com.example.cinemhub.R;
import com.example.cinemhub.model.Movie;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownServiceException;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SliderPagerAdapter extends PagerAdapter {

    private Context mContext ;
    private LayoutInflater layoutInflater;
    private List<Movie> mList ;
    private ImageView imageView;

    public SliderPagerAdapter(Context mContext) { //List<Slide>
        this.mContext = mContext;
    }

    public SliderPagerAdapter(Context mContext, List<Movie> mList) { //List<Slide>
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {


        layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View slideLayout = layoutInflater.inflate(R.layout.slide_item,null);
        View viewLayout = layoutInflater.inflate(R.layout.fragment_home,null);


        ImageView slideImg = slideLayout.findViewById(R.id.slide_img);
        ViewPager viewPager = viewLayout.findViewById(R.id.slider_pager);
        //TextView slideText = slideLayout.findViewById(R.id.slide_title);
        slideImg.setImageBitmap(mList.get(position).getImage());
        //slideText.setText(mList.get(position).getTitle());


        slideLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                Snackbar.make(slideLayout, "image" + (position + 1) , Snackbar.LENGTH_LONG).show();
                System.out.println("Cliccato dai");
                */


                Movie clickedDataItem;
               //int position = getItemPosition(position);

                System.out.println("Cliccato dai");


                Intent i = new Intent();
                i.setClass(mContext, ActivityDetail.class);

                /*
                i.putExtra("original_title", mList.get(position).getOriginalTitle());
                i.putExtra("poster_path", mList.get(position).getPosterPath());
                i.putExtra("overview", mList.get(position).getOverview());
                i.putExtra("release_date", mList.get(position).getReleaseDate());
                i.putExtra("id", Integer.toString(mList.get(position).getId()));
                i.putExtra("vote_average", Double.toString(mList.get(position).getVoteAverage()));
                i.addFlags(i.FLAG_ACTIVITY_NEW_TASK);

                */
                mContext.startActivity(i);

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