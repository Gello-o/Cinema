package com.example.cinemhub.model;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Favorite.class}, version = 2)
public abstract class FavoriteDB extends RoomDatabase {
    public final String TAG = "DbStructure";
    private static FavoriteDB favoriteDB;
    private static Context mContext;

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("DROP TABLE Favorite");

            database.execSQL("CREATE TABLE `Favorite1` (`id` INTEGER, "
                    + "`title` TEXT, `rating` DOUBLE, `posterPath` TEXT, `plot` TEXT, `originalTitle` TEXT, `releaseDate` TEXT, `voteCount` TEXT, `genreId` TEXT, PRIMARY KEY(`id`))");

        }
    };

    public FavoriteDB(){}

    public static FavoriteDB getInstance(Context context){
        mContext = context;
        if(favoriteDB == null){
            synchronized (FavoriteDB.class){
                favoriteDB = Room.databaseBuilder(context, FavoriteDB.class,"Favorite1")
                        .addMigrations(MIGRATION_1_2)
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