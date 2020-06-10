package com.example.cinemhub.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MovieDao {
    public final String TAG = "DbInterface";

    @Insert
    public void addFavorite(Favorite favorite);

    @Query("select * from Favorite")
    public List<Favorite> getFavorite();

    @Delete
    public void deleteFavorite(Favorite dbToDelete);

    @Update
    public void changeDb(Favorite nextDb);

    @Insert
    public void addUserRating(UserRatingDB userRatingDB);

    @Query("select * from UserRatingDB")
    public List<UserRatingDB> getUserOverview();

    @Query("select * from UserRatingDB where movie_id = :id")
    public UserRatingDB getUserInfo(int id);

    @Update
    public void updateUserRating(UserRatingDB nextDb);

    @Delete
    public void deleteUser(UserRatingDB dbToDelete);
}