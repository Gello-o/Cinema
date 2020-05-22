package com.example.cinemhub.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Favorite.class}, version = 1)
public abstract class FavoriteDB extends RoomDatabase {
    public final String TAG = "DbStructure";
    private static FavoriteDB favoriteDB;
    private static Context mContext;

    public FavoriteDB(){}

    public static FavoriteDB getInstance(Context context){
        mContext = context;
        if(favoriteDB == null){
            synchronized (FavoriteDB.class){
                favoriteDB = Room.databaseBuilder(context, FavoriteDB.class,"Favorite")
                        .allowMainThreadQueries()
                        .build();
            }
        }
        return favoriteDB;
    }

    public static FavoriteDB getInstance(){
        if(mContext == null)
            return null;
        if(favoriteDB == null){
            synchronized (FavoriteDB.class){
                favoriteDB = Room.databaseBuilder(mContext, FavoriteDB.class,"Favorite").allowMainThreadQueries().build();
            }
        }
        return favoriteDB;
    }

    public abstract MovieDao dbInterface();
}