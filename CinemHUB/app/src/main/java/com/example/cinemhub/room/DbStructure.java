package com.example.cinemhub.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;

@Database(entities = {Favorite.class}, version = 1)
public abstract class DbStructure extends RoomDatabase {
    public final String TAG = "DbStructure";

    public abstract DbInterface dbInterface();
}