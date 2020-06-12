package com.example.cinemhub;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.example.cinemhub.model.FavoriteDB;
import com.example.cinemhub.model.UserInfo;

import java.util.List;

public class UserOperation {
    private static final String TAG = "UserOperation";
    public Activity activity;
    private Context context;

    private TextView showVote, warning, comment, usersRating;
    private EditText editText;
    private RatingBar ratingBar;

    private float submittedRating;
    private String id, commento;
    private boolean app = true;

    //costructor allow findview and Toast
    public UserOperation(Activity activity,Context context){
        this.activity = activity;
        this.context = context;
    }

    //////////
    void eseguiUser(String _id) {
        // cancellaDb();
        id = _id;

        Button button = this.activity.findViewById(R.id.submit_rating);
        ratingBar = this.activity.findViewById(R.id.ratingBar);
        editText = this.activity.findViewById(R.id.user_overview);
        showVote = this.activity.findViewById(R.id.show_vote);
        warning = this.activity.findViewById(R.id.warning);
        comment = this.activity.findViewById(R.id.comment);
        editText = this.activity.findViewById(R.id.user_overview);
        Button editRatingButton = this.activity.findViewById(R.id.edit_rating);
        usersRating = this.activity.findViewById(R.id.users_rating2);
        Button deleteButton = this.activity.findViewById(R.id.delete_button);

        writeComment();

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                submittedRating = ratingBar.getRating();
                showVote.setText("your Rating: " + submittedRating);
                Log.d(TAG,"app: " + app);
                if(checkUser()) {
                    if (app && submittedRating != 0) {
                        warning.setText("WARNING! you'll override your comment");
                    }
                }
            }
        });

        if (!editText.getText().toString().equals(commento)) {
            editText.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onClick(View v) {
                    editText.setFocusableInTouchMode(true);
                    editText.setFocusable(true);

                    if (editText.getText().toString().equals("Write There:")) {
                        editText.setText("");
                    }
                    editText.setTextColor(ContextCompat.getColor(context, R.color.textColor));
                    if (checkUser()) {
                        warning.setText("WARNING! you'll override your comment");
                    }
                }
            });
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (editText.getText().toString().equals("Write There:")) {
                    editText.setText("");
                }

                saveUser(id);
                writeComment();

                if (checkUser()) {
                    if (commento.equals("") && submittedRating == 0) {
                        Toast.makeText(context, "empty comment", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Comment added", Toast.LENGTH_SHORT).show();
                    }
                }

                //azzero tutto
                warning.setText("");
                editText.setText("");
                ratingBar.setRating(0);
                submittedRating = 0;
                app = true;
            }
        });


        editRatingButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                app = false;
                if (checkUser()) {
                    getUsereInfo();
                    ratingBar.setRating(submittedRating);

                    editText.setFocusableInTouchMode(true);
                    editText.setFocusable(true);
                    editText.setTextColor(ContextCompat.getColor(context, R.color.textColor));
                    editText.setText("" + commento);
                    // editText.hasFocus();

                    if (commento.equals("") && submittedRating == 0) {
                        Toast.makeText(context, "Leave a comment first", Toast.LENGTH_SHORT).show();
                    }else   {
                        warning.setText("WARNING! you'll change your comment");
                    }
                } else {
                    Toast.makeText(context, "Leave a comment first", Toast.LENGTH_SHORT).show();
                }
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                UserInfo info;
                info = FavoriteDB.getInstance().dbInterface().getUserInfo(Integer.parseInt(id));
                if(info != null){
                    FavoriteDB.getInstance().dbInterface().deleteUser(info);
                    ratingBar.setRating(0);
                    usersRating.setText("0 / 10");
                    comment.setText("");
                    Toast.makeText(context, "Comment Deleted", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context, "There is no comment to delete", Toast.LENGTH_SHORT).show();
                }
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

    private void saveUser(String id){
        UserInfo userRatingDB = new UserInfo();

        String name = editText.getText().toString();
        userRatingDB.setMovieId(Integer.parseInt(id));
        userRatingDB.setRating(submittedRating);
        userRatingDB.setOverview(name);
        Log.d(TAG, "Memorizzato nel DB");

        if (checkUser()) {
            FavoriteDB.getInstance().dbInterface().updateUserRating(userRatingDB);
            Log.d(TAG, "update dbuser ok ");

        } else {
            FavoriteDB.getInstance().dbInterface().addUserRating(userRatingDB);
            Log.d(TAG, "added dbuser 1 time ok ");
        }
    }

    private boolean checkUser(){
        UserInfo user;
        user = FavoriteDB.getInstance().dbInterface().getUserInfo(Integer.parseInt(id));
        if(user != null)
            return true;
        return false;
    }

    private void getUsereInfo(){
        if(checkUser()){
            UserInfo user;
            user = FavoriteDB.getInstance().dbInterface().getUserInfo(Integer.parseInt(id));
            commento = user.getOverview();
            submittedRating = user.getRating();
        }
    }

    @SuppressLint("SetTextI18n")
    private void writeComment(){
        if(checkUser()){//==true)
            getUsereInfo();
            usersRating.setText(submittedRating + " / 10");
            comment.setText(commento);
        }else{
            usersRating.setText("0 / 10");
            comment.setText("");
        }
    }

    private void cancellaDb() {
        List<UserInfo> line = FavoriteDB.getInstance().dbInterface().getUserOverview();
        FavoriteDB.getInstance().clearAllTables();
        Log.d(TAG, "db size: " + line.size());
    }

}
