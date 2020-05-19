package com.example.cinemhub.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.cinemhub.model.Movie;

import java.util.List;

@Dao
public interface DbInterface {
    public final String TAG = "DbInterface";

    @Insert
    public void addFavorite(Favorite favorite);

    @Query("select * from Favorite")
    public List<Favorite> getFavorite();

    //cancella una riga tramite Fvaorite.setID()
    @Delete
    public void deleteFavorite(Favorite dbToDelete);

    @Update
    public void changeDb(Favorite nextDb);
}
