package com.example.cinemhub.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.cinemhub.model.Favorite;

import java.util.List;

@Dao
public interface MovieDao {
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
