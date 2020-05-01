package com.example.cinemhub.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.cinemhub.MainActivity;
import com.example.cinemhub.R;
import com.example.cinemhub.adapter.MoviesAdapter;
import com.example.cinemhub.adapter.SliderPagerAdapter;
import com.example.cinemhub.model.Movie;
import com.example.cinemhub.model.Slide;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL;


public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";
    private HomeViewModel homeViewModel;
    private RecyclerView popularRV;
    private MoviesAdapter adapter;
    private List<Slide> lstSlides;
    private ViewPager sliderpager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        popularRV = root.findViewById(R.id.recycler_view_popular);

        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        homeViewModel.getPopolari().observe(getViewLifecycleOwner(), new Observer<HashSet<Movie>>() {
            @Override
            public void onChanged(@Nullable HashSet<Movie> moviesSet) {
                if(moviesSet.isEmpty())
                    Log.d(TAG, "moviesSet nullo");
                Log.d(TAG, "contesto " + getContext());
                Log.d(TAG, "activity " + getActivity());
                adapter.notifyDataSetChanged();
            }
        });

        initRecyclerView();


        sliderpager = root.findViewById(R.id.slider_pager);


        lstSlides = new ArrayList<>();
        lstSlides.add(new Slide(R.drawable.tolo, "tolo tolo"));
        lstSlides.add(new Slide(R.drawable.future, "back to the future"));
        lstSlides.add(new Slide(R.drawable.tolo, "tolo tolo"));
        lstSlides.add(new Slide(R.drawable.future, "back to the future"));


        SliderPagerAdapter adapter = new SliderPagerAdapter(getContext(), lstSlides);
        sliderpager.setAdapter(adapter);

        return root;
    }

    public void initRecyclerView (){
        HashSet<Movie> set = homeViewModel.getPopolari().getValue();
        ArrayList<Movie> list = new ArrayList<>();
        list.addAll(set);
        adapter = new MoviesAdapter(getContext(), list);
        if(adapter == null)
            Log.d(TAG, "adapter null");
        if(popularRV == null)
            Log.d(TAG, "RECYCLER null");
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        popularRV.setLayoutManager(layoutManager);
        popularRV.setAdapter(adapter);
    }

}

