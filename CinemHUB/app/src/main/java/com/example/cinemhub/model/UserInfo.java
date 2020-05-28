package com.example.cinemhub.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;


@Entity(tableName = "UserInfo", primaryKeys = {"CommentKey","MovieId", "UserId"})
public class  UserInfo{
    public static final String TAG = "UserInfo";

    @NonNull
    @ColumnInfo(name = "UserId")
    int userId;

    @NonNull
    @ColumnInfo(name = "MovieId")
    int movieId;

    @ColumnInfo(name = "CommentKey")
    int commentKey;

    @ColumnInfo(name = "Comment")
    String comment;

    @ColumnInfo(name = "Vote")
    String vote;

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCommentKey() {
        return commentKey;
    }

    public void setCommentKey(int commentKey) {
        this.commentKey = commentKey;
    }
}