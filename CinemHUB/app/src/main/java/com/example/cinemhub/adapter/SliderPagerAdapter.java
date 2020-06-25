package com.example.cinemhub.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import com.bumptech.glide.Glide;
import com.example.cinemhub.ActivityDetail;
import com.example.cinemhub.R;
import com.example.cinemhub.model.Movie;
import com.example.cinemhub.utils.Constants;
import java.util.List;

/*
Adapter per oggetti di tipo Movie: il suo ruolo è quello di gestire la visualizzazione dei film
all'interno dello Slider nella HomePage.
Nel metodo instantiateItem viene impostato un onClickListener che avvia ActivityDetail,
ovvero la classe che contiene tutte le informazioni inerenti al film cliccato.
*/

public class SliderPagerAdapter extends PagerAdapter {

    private static final String TAG = "SliderPagerAdapter";
    private Context mContext ;
    private List<Movie> mList ;


    public SliderPagerAdapter(Context mContext, List<Movie> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {


        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View slideLayout = inflater.inflate(R.layout.slide_item,null);

        ImageView slideImg = slideLayout.findViewById(R.id.slide_img);
       //TextView slideText = slideLayout.findViewById(R.id.slide_title);

        if(mList.get(position) != null && mList.get(position).getBackDropPath()!= null){
            Glide.with(mContext)
                    .load(Constants.BASE_IMAGE_URL+mList.get(position).getBackDropPath())
                    .placeholder(R.drawable.image_not_found_detail)
                    .into(slideImg);
       //     slideText.setText(mList.get(position).getTitle());
        }
        else {
            slideImg.setImageResource(R.drawable.image_not_found_detail);
        }

        slideImg.setOnClickListener(v -> {
            Movie clickedDataItem;

            Log.d(TAG, "Clicked");
            if(position != RecyclerView.NO_POSITION){
                clickedDataItem = mList.get(position);
                Intent intent = new Intent(mContext, ActivityDetail.class);
                intent.putExtra("original_title", mList.get(position).getOriginalTitle());
                intent.putExtra("title", mList.get(position).getTitle());
                intent.putExtra("poster_path", mList.get(position).getPosterPath());
                intent.putExtra("overview", mList.get(position).getOverview());
                intent.putExtra("release_date", mList.get(position).getReleaseDate());
                intent.putExtra("id", Integer.toString(mList.get(position).getId()));
                intent.putExtra("vote_average", Double.toString(mList.get(position).getVoteAverage()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
                Toast.makeText(v.getContext(), "you clicked " + clickedDataItem.getOriginalTitle(), Toast.LENGTH_SHORT).show();
            }
        });

        container.addView(slideLayout);

        return slideLayout;

    }

    @Override
    public int getCount() {
        if(mList != null)
            return mList.size();
        else
            return 0;
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