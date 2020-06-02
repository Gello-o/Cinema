package com.example.cinemhub;

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
import com.example.cinemhub.model.UserRatingDB;

import java.util.List;

public class UserOperation {
    private static final String TAG = "UserOperation";
    public Activity activity;
    private Context context;

    private TextView showVote, warning, comment, usersRating;
    private EditText editText;
    private RatingBar ratingBar, ratingBarUser;
    private Button button, editRatingButton;
    private List<UserRatingDB> lineUser;
    private UserRatingDB userRatingDB;

    float submittedRating;
    boolean recensito;
    public String id, commento;




//costructor allow findview and Toast
    public UserOperation(Activity _activity,Context _context){
        this.activity = _activity;
        this.context = _context;
    }

//////////
    public void eseguiUser(String _id){
        id = _id;

        button = this.activity.findViewById(R.id.submit_rating);
        ratingBar =  this.activity.findViewById(R.id.ratingBar);
        editText =  this.activity.findViewById(R.id.user_overview);
        showVote = this.activity.findViewById(R.id.show_vote);
        warning = this.activity.findViewById(R.id.warning);
        comment = this.activity.findViewById(R.id.comment);
        editText = this.activity.findViewById(R.id.user_overview);
        editRatingButton = this.activity.findViewById(R.id.edit_rating);
        usersRating = this.activity.findViewById(R.id.users_rating2);


        writeComment();
        recensito=checkUser();

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                submittedRating = ratingBar.getRating();
                showVote.setText("your Rating: " + submittedRating);
                Log.d(TAG,"ottenuto il voto: " + ratingBar.getRating());
            }
        });

        if(!editText.getText().toString().equals(commento)) {
            editText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editText.setText("");
                    editText.setFocusableInTouchMode(true);
                    editText.setFocusable(true);
                    editText.setTextColor(ContextCompat.getColor(context, R.color.textColor));
                    if (recensito) {
                        warning.setText("WARNING! you'll override your comment");
                    }
                }
            });
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveUser(id);

                //debug
                Log.d(TAG,userRatingDB.getOverview());
                Log.d(TAG,""+userRatingDB.getRating());
                Log.d(TAG,""+userRatingDB.getMovie_id());
                //
                writeComment();

                if(recensito){
                    if(!commento.equals("") && submittedRating == 0) {
                        Toast.makeText(context, "Comment removed", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(context, "Comment added", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    if(!commento.equals("") && submittedRating == 0){
                        Toast.makeText(context, "Please add a comment or a rating", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(context, "Comment added", Toast.LENGTH_SHORT).show();
                    }
                }

                //azzero tutto
                warning.setText("");
                editText.setText("");
                ratingBar.setRating(0);
            }
        });


        editRatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkUser()) {
                    ratingBar.setRating(submittedRating);

                    editText.setTextColor(ContextCompat.getColor(context, R.color.textColor));
                    warning.setText("WARNING! you'll change your comment");
                    editText.setText("" + commento);
                    editText.hasFocus();
                    editText.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            editText.setFocusableInTouchMode(true);
                            editText.setFocusable(true);
                        }
                    });
                } else {
                    Toast.makeText(context, "Leave a comment first", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void mostraDbUser() {
        lineUser = FavoriteDB.getInstance().dbInterface().getUserOverview();
        for(UserRatingDB userRatingDB : lineUser){
            Log.d(TAG,"Database: " +
                    " ID: " + userRatingDB.getMovie_id() +
                    " Rating: " + userRatingDB.getRating() +
                    " Overview: " + userRatingDB.getOverview());
        }
    }

    private void saveUser(String _id){
        userRatingDB = new UserRatingDB();

        String name = editText.getText().toString();
        userRatingDB.setMovie_id(Integer.parseInt(_id));
        userRatingDB.setRating(submittedRating);
        userRatingDB.setOverview(name);

        if (recensito) {
            FavoriteDB.getInstance().dbInterface().updateUserRating(userRatingDB);
            Log.d(TAG, "update dbuser ok ");

        } else {
            FavoriteDB.getInstance().dbInterface().addUserRating(userRatingDB);
            Log.d(TAG, "added dbuser 1 time ok ");
        }
    }

    private boolean checkUser(){
        UserRatingDB user;
        Log.d(TAG,"entrato nel check");
        user = FavoriteDB.getInstance().dbInterface().getUserInfo(Integer.parseInt(id));
        if(user != null){
            commento = user.getOverview();
            submittedRating = user.getRating();
            return true;
        }else {
            return false;
        }
    }

    private void writeComment(){
        if(checkUser()){//==true)
            usersRating.setText((int) submittedRating + " / 10");
            comment.setText(commento);
        }
    }
}
