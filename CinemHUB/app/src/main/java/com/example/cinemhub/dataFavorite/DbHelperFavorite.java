package com.example.cinemhub.dataFavorite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class DbHelperFavorite extends SQLiteOpenHelper {

    public static final String FAVORITE ="FAVORITE";

    public DbHelperFavorite(Context context) {
        super(context, FAVORITE, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //Favorite
        final String FAVORITE_DB = "CREATE TABLE TABLE" + DbFavorite.FavoritesEntry.TABLE_NAME + " (" +
                DbFavorite.FavoritesEntry._ID+ "INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DbFavorite.FavoritesEntry.COLUMN_MOVIEID + "INTEGER, " +
                DbFavorite.FavoritesEntry.COLUMN_TITLE + "TEXT NOT NULL, " +
                DbFavorite.FavoritesEntry.COLUMN_USERRATING + "REAL NOT NULL, " +
                DbFavorite.FavoritesEntry.COLUMN_POSTER_PATH + "TEXT NOT NULL, " +
                DbFavorite.FavoritesEntry.COLUMN_PLOT_SYNOPSIS + "TEXT NOT NULL, " +
                ");";

        db.execSQL(FAVORITE_DB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DbFavorite.FavoritesEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

}
