package com.example.cinemhub.model;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Favorite.class}, version = 1)
public abstract class FavoriteDB extends RoomDatabase {
    public final String TAG = "DbStructure";

    public abstract MovieDao dbInterface();
}
