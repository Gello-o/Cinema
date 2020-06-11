package com.example.cinemhub.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import java.util.List;

@Dao
public interface MovieDao {
    String TAG = "DbInterface";

    @Insert
    void addFavorite(Favorite favorite);

    @Query("select * from Favorite")
    List<Favorite> getFavorite();

    //cancella una riga tramite Fvaorite.setID()
    @Delete
    void deleteFavorite(Favorite dbToDelete);

    @Insert
    void addUserRating(UserInfo info);

    @Query("select * from UserInfo")
    List<UserInfo> getUserOverview();

    @Query("select * from UserInfo where movieId = :id")
    UserInfo getUserInfo(int id);

    @Update
    void updateUserRating(UserInfo nextDb);

    @Delete
    void deleteUser(UserInfo userToDelete);
}