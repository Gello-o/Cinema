package com.example.cinemhub.dataFavorite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;


public class DbManagerFavorite {

    private DbHelperFavorite dbhelper;

    public DbManagerFavorite(Context ctx)
    {
        dbhelper=new DbHelperFavorite(ctx);
    }

    public void addFavorite(String id, String title, String userRating, String posterPath, String plotSynopsis) {

        SQLiteDatabase db = dbhelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DbFavorite.FavoritesEntry.COLUMN_MOVIEID , id);
        values.put(DbFavorite.FavoritesEntry.COLUMN_TITLE , title);
        values.put(DbFavorite.FavoritesEntry.COLUMN_USERRATING , userRating);
        values.put(DbFavorite.FavoritesEntry.COLUMN_POSTER_PATH , posterPath);
        values.put(DbFavorite.FavoritesEntry.COLUMN_PLOT_SYNOPSIS , plotSynopsis);

        try {
            db.insert(DbFavorite.FavoritesEntry.TABLE_NAME, null, values);
        }
        catch (SQLiteException exeptionFavorite) {
            System.out.println("Inserimento Favorite Non Riuscito");
        }
    }

    public void deleteFavorite(long id) {
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        db.delete(DbFavorite.FavoritesEntry.TABLE_NAME, DbFavorite.FavoritesEntry.COLUMN_MOVIEID + "=" + id, null);
    }

    public Cursor getInformationFavorite()
    {
        Cursor cursor = null;
        try {
            SQLiteDatabase db=dbhelper.getReadableDatabase();
            cursor=db.query(DbFavorite.FavoritesEntry.TABLE_NAME, null, null, null, null, null, null, null);
        }
        catch(SQLiteException sqle) {
            System.out.println("Info Non Disponibili Favorite");
        }
        return cursor;
    }
}
