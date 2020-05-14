package com.example.cinemhub.dataFavorite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.example.cinemhub.model.Movie;


public class DbManagerFavorite {
    private final String TAG = "DbManagerFavorite";

    private DbHelperFavorite dbhelper;

    public DbManagerFavorite(Context ctx)
    {
        dbhelper=new DbHelperFavorite(ctx);
    }

    public void addFavorite(Movie movie) {

        SQLiteDatabase db = dbhelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DbFavorite.FavoritesEntry.COLUMN_MOVIEID , movie.getId());
        values.put(DbFavorite.FavoritesEntry.COLUMN_TITLE , movie.getTitle());
        values.put(DbFavorite.FavoritesEntry.COLUMN_USERRATING , movie.getVoteAverage());
        values.put(DbFavorite.FavoritesEntry.COLUMN_POSTER_PATH , movie.getPosterPath());
        values.put(DbFavorite.FavoritesEntry.COLUMN_PLOT_SYNOPSIS , movie.getOverview());

        try {
            db.insert(DbFavorite.FavoritesEntry.TABLE_NAME, null, values);
            Log.d(TAG,"Inserimento Favorite Riuscito: " + movie.getId());

        }
        catch (SQLiteException exeptionFavorite) {
            Log.d(TAG,"Inserimento Favorite Non Riuscito");
        }
    }

    public void deleteFavorite(long id) {
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        db.delete(DbFavorite.FavoritesEntry.TABLE_NAME, DbFavorite.FavoritesEntry.COLUMN_MOVIEID + "=" + id, null);
        Log.d(TAG,"Film eliminato" );
    }

    public Cursor getInformationFavorite()
    {
        Cursor cursor = null;
        try {
            SQLiteDatabase db=dbhelper.getReadableDatabase();
            cursor=db.query(DbFavorite.FavoritesEntry.TABLE_NAME,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null);
        }
        catch(SQLiteException sqle) {
           Log.d(TAG,"Info del film Non Disponibili");
        }
        return cursor;
    }
}
