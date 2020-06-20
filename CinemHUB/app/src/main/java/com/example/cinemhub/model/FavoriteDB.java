package com.example.cinemhub.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

/*
oggetto database che ha due istanze: la prima per memorizzare oggetti Favorite,
la seconda per memorizzare il rating dell'utente
 */

@Database(entities = {Favorite.class, UserInfo.class}, version = 3)
public abstract class FavoriteDB extends RoomDatabase {
    public final String TAG = "DbStructure";
    private static FavoriteDB favoriteDB;
    private static FavoriteDB userRatingDB;
    private static Context mContext;

    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("DROP TABLE Favorite");

            database.execSQL("CREATE TABLE `Favorite1` (`id` INTEGER, "
                    + "`title` TEXT, `rating` DOUBLE, `posterPath` TEXT, `plot` TEXT, `originalTitle` TEXT, `releaseDate` TEXT, `voteCount` TEXT, `genreId` TEXT, PRIMARY KEY(`id`))");

        }
    };

    private static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("DROP TABLE UserRatingDB");

            database.execSQL("CREATE TABLE `UserInfo` (`id` INTEGER, "
                    + "`rating` FLOAT, `overview` TEXT, PRIMARY KEY(`id`))");
        }
    };


    public FavoriteDB(){}

    public static FavoriteDB getInstance(Context context){
        mContext = context;
        if(favoriteDB == null){
            synchronized (FavoriteDB.class){
                favoriteDB = Room.databaseBuilder(context, FavoriteDB.class,"Favorite1")
                        .addMigrations(MIGRATION_1_2)
                        .addMigrations(MIGRATION_2_3)
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
                favoriteDB = Room.databaseBuilder(mContext, FavoriteDB.class,"Favorite1")
                        .addMigrations(MIGRATION_1_2)
                        .addMigrations(MIGRATION_2_3)
                        .allowMainThreadQueries()
                        .build();
            }
        }
        return favoriteDB;
    }

    public static FavoriteDB getInstanceUser(){
        if(mContext == null)
            return null;
        if(userRatingDB == null){
            synchronized (FavoriteDB.class){
                favoriteDB = Room.databaseBuilder(mContext, FavoriteDB.class,"UserInfo")
                        .addMigrations(MIGRATION_1_2)
                        .addMigrations(MIGRATION_2_3)
                        .allowMainThreadQueries()
                        .build();
            }
        }
        return userRatingDB;
    }

    public abstract MovieDao dbInterface();
}