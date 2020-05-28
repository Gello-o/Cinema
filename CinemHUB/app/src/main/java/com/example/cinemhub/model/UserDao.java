package com.example.cinemhub.model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao  {

    @Insert
    public void addReview(UserInfo userInfo);

    @Query("select * from UserInfo")
    public List<UserInfo> getInfo();

    @Query("select COUNT(commentKey) from UserInfo where UserId = :userId")
    public int countComments(int userId);
}
