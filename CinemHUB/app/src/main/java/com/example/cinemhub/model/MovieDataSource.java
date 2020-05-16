package com.example.cinemhub.model;

import androidx.annotation.NonNull;
import androidx.paging.ItemKeyedDataSource;

import java.util.List;

public class MovieDataSource extends ItemKeyedDataSource<Integer, Movie>{

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Movie> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Movie> callback) {

    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Movie> callback) {

    }

    @NonNull
    @Override
    public Integer getKey(@NonNull Movie item) {
        return null;
    }
}
