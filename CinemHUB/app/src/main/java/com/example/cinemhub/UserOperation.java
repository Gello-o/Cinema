package com.example.cinemhub;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.example.cinemhub.model.Favorite;
import com.example.cinemhub.model.FavoriteDB;
import com.example.cinemhub.model.UserInfo;

import java.util.List;

public class UserOperation {
    private static final String TAG = "UserOperation";

    public Activity activity;
    private Context context;

    private TextView showVote, warning, comment, userRating;
    private EditText editComment;
    private RatingBar ratingBar;

    private int voteToSubmit, oldVote;
    private String id, commento;

    //costructor allow findview and Toast
    public UserOperation(Activity activity,Context context){
        this.activity = activity;
        this.context = context;
    }

    //////////
    void eseguiUser(String id) {
        // cancellaDb();
        this.id = id;

        Button submit = this.activity.findViewById(R.id.submit_rating);
        ratingBar = this.activity.findViewById(R.id.ratingBar);
        editComment = this.activity.findViewById(R.id.user_overview);
        showVote = this.activity.findViewById(R.id.show_vote);
        warning = this.activity.findViewById(R.id.warning);
        comment = this.activity.findViewById(R.id.comment);
        Button editRatingButton = this.activity.findViewById(R.id.edit_rating);
        userRating = this.activity.findViewById(R.id.users_rating2);
        Button deleteButton = this.activity.findViewById(R.id.delete_button);

        if(checkUser()) {
            UserInfo user;
            user = FavoriteDB.getInstance().dbInterface().getUserInfo(Integer.parseInt(id));
            commento = user.getOverview();
            comment.setText(commento);
            oldVote = (int) user.getRating();
            userRating.setText((int) oldVote + " / 10");
        }else{
            commento = "";
            comment.setText(commento);
            oldVote = 0;
            userRating.setText("0 / 10");
        }

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                voteToSubmit = (int) rating;
                showVote.setText("your Rating: " + voteToSubmit);

                Log.d(TAG, "vecchio " + oldVote);
                Log.d(TAG, "nuovo " + voteToSubmit);

                if(oldVote != 0 && voteToSubmit != 0 && oldVote != voteToSubmit) {
                    if(warning.getText().toString().equals(""))
                        warning.setText(R.string.warning_vote);
                    else{
                        if(!warning.getText().toString().equals(R.string.warning_vote))
                            warning.setText(R.string.warning_vote_comment);
                    }
                }
                else{

                    if(!warning.getText().toString().contains("comment"))
                        warning.setText("");
                    else{
                        if(!editComment.getText().toString().equals(commento))
                            warning.setText(R.string.warning_comment);
                    }

                }

            }
        });

        editComment.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                        Log.d(TAG, "vecchio commento " + commento);

                        Log.d(TAG, "nuovo commento" + editComment.getText().toString());

                        if(s.length() == 0){

                            if(voteToSubmit != 0 && oldVote != 0 && voteToSubmit != oldVote)
                                warning.setText(R.string.warning_vote);
                            else
                                warning.setText("");

                        }
                    }

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void afterTextChanged(Editable s) {

                        Log.d(TAG, "vecchio commento " + commento);

                        Log.d(TAG, "nuovo commento " + editComment.getText().toString());

                        if(s.length() != 0){

                            String commentoEditato = editComment.getText().toString();
                            String warningText = warning.getText().toString();

                            if(!commento.equals("") &&
                                    !commentoEditato.equals("") &&
                                        !commentoEditato.equals(commento) &&
                                            !commentoEditato.equals("Write There:")
                            ){

                                if(warningText.equals(""))
                                    warning.setText(R.string.warning_comment);
                                else{
                                    if(!warning.getText().toString().contains("comment"))
                                        warning.setText(R.string.warning_vote_comment);
                                }
                            }
                            else{
                                if(warningText.contains("vote") && warningText.contains("comment")){

                                    if(commentoEditato.equals(commento))
                                        warning.setText("");
                                    else
                                        warning.setText(R.string.warning_comment);

                                }
                                else if(warningText.contains("vote")){
                                    warning.setText(R.string.warning_vote);
                                }

                            }

                        }


                    }
                }
        );

        editComment.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                editComment.setFocusableInTouchMode(true);
                editComment.setFocusable(true);

                if (editComment.getText().toString().equals("Write There:"))
                    editComment.setText("");

                editComment.setTextColor(ContextCompat.getColor(context, R.color.textColor));

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                warning.setText("");
                if (editComment.getText().toString().equals("Write There:"))
                    editComment.setText("");

                saveOrUpdateUser();
            }

        });

        editRatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                warning.setText("");
                if(!commento.equals("")){
                    editComment.setText(commento);
                    ratingBar.setRating(oldVote);
                }
                else
                    Toast.makeText(context, "Nothing to edit", Toast.LENGTH_SHORT).show();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                warning.setText("");
                if(checkUser()) {
                    UserInfo userInfo = FavoriteDB.getInstance().dbInterface().getUserInfo(Integer.parseInt(id));
                    FavoriteDB.getInstance().dbInterface().deleteUser(userInfo);
                    commento = "";
                    oldVote = 0;
                    comment.setText("");
                    userRating.setText("0 / 10");
                }
                else
                    Toast.makeText(context, "Leave a comment first", Toast.LENGTH_SHORT).show();

            }
        });
    }
    ///////////////

    private void showInfo() {
        List<UserInfo> lineUser = FavoriteDB.getInstance().dbInterface().getUserOverview();
        for(UserInfo userRating: lineUser){
            Log.d(TAG,"Database: " +
                    " ID: " + userRating.getMovieId() +
                    " Rating: " + userRating.getRating() +
                    " Overview: " + userRating.getOverview());
        }
    }

    private boolean checkUser(){
        UserInfo user;
        user = FavoriteDB.getInstance().dbInterface().getUserInfo(Integer.parseInt(id));
        return user != null;
    }


    private void saveOrUpdateUser(){
        UserInfo userInfo = new UserInfo();
        userInfo.setMovieId(Integer.parseInt(id));

        if(voteToSubmit != 0) {
            userInfo.setRating(voteToSubmit);
            userRating.setText(voteToSubmit + " / 10");
            oldVote = voteToSubmit;
        }
        else
            userInfo.setRating(oldVote);

        if(editComment.getText().toString().equals(""))
            userInfo.setOverview(commento);
        else {
            String newComment = editComment.getText().toString();
            userInfo.setOverview(newComment);
            commento = newComment;
            comment.setText(newComment);
        }

        if(checkUser())
            FavoriteDB.getInstance().dbInterface().updateUserRating(userInfo);
        else
            FavoriteDB.getInstance().dbInterface().addUserRating(userInfo);

        warning.setText("");
        editComment.setText("");
        ratingBar.setRating(0);
        showInfo();
    }


}