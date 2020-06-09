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

    private TextView showVote, warning, comment;
    private EditText editText;
    private RatingBar ratingBar, ratingBarUser;
    private Button button;
    private List<UserInfo> lineUser;
    private UserInfo userRatingDB;

    float submittedRating;
    boolean recensito;
    public String id, commento;

    //costructor allow findview and Toast
    public UserOperation(Activity activity,Context context){
        this.activity = activity;
        this.context = context;
    }

    //////////
    public void eseguiUser(String _id){
        id = _id;

        button = this.activity.findViewById(R.id.submit_rating);
        ratingBar = this.activity.findViewById(R.id.ratingBar);
        ratingBarUser = this.activity.findViewById(R.id.ratingBar_user);
        editText = this.activity.findViewById(R.id.user_overview);
        showVote = this.activity.findViewById(R.id.show_vote);
        warning = this.activity.findViewById(R.id.warning);
        comment = this.activity.findViewById(R.id.comment);
        editText = this.activity.findViewById(R.id.user_overview);

        checkComment();
        recensito=checkUser();

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                submittedRating = ratingBar.getRating();
                showVote.setText("your Rating: " + submittedRating);
                Log.d(TAG,"ottenuto il voto: " + ratingBar.getRating());
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userRatingDB = new UserInfo();

                String name = editText.getText().toString();
                userRatingDB.setMovie_id(Integer.parseInt(_id));
                userRatingDB.setRating(submittedRating);
                userRatingDB.setOverview(name);

                if (recensito) {
                    FavoriteDB.getInstance().dbInterface().updateUserRating(userRatingDB);
                    Log.d(TAG, "update dbuser ok ");
                    checkComment();
                    if(!commento.equals("") && submittedRating == 0) {
//in realtà c'e ma e null
                        Toast.makeText(context, "Commento rimosso correttamente", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(context, "Commento aggiunto correttamente", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    FavoriteDB.getInstance().dbInterface().addUserRating(userRatingDB);
                    Log.d(TAG, "memorizzato in dbUser prima volta");
                    checkComment();
                    Toast.makeText(context, "Commento aggiunto correttamente", Toast.LENGTH_SHORT).show();
                }
//azzero tutto
                warning.setText("");
                editText.setText("");
                ratingBar.setRating(0);
            }
        });
    }

    private void mostraDbUser() {
        lineUser = FavoriteDB.getInstance().dbInterface().getUserOverview();
        for(UserInfo userRatingDB : lineUser){
            Log.d(TAG,"Database: " +
                    " ID: " + userRatingDB.getMovie_id() +
                    " Rating: " + userRatingDB.getRating() +
                    " Overview: " + userRatingDB.getOverview());
        }
    }

    private boolean checkUser(){
        UserInfo user;
        Log.d(TAG,"entrato nel check");
        user = FavoriteDB.getInstance().dbInterface().getUserInfo(Integer.parseInt(id));
        if(user != null){
            commento = user.getOverview();
            submittedRating = user.getRating();
            return true;
        }else
            return false;

    }

    private void checkComment(){
        if(checkUser()){//==true)
            editText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG,"entrato nel onclick edit");
                    editText.setText("");
                    editText.setFocusableInTouchMode(true);
                    editText.setFocusable(true);
                    editText.setTextColor(ContextCompat.getColor(context, R.color.editTextColor));
                    warning.setText("WARNING! you'll override your comment");
                }
            });
            ratingBarUser.setRating(submittedRating);
            comment.setText(commento);
        } else{
            editText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG,"entrato nel onclick edit");
                    editText.setText("");
                    editText.setFocusableInTouchMode(true);
                    editText.setFocusable(true);
                    editText.setTextColor(ContextCompat.getColor(context, R.color.editTextColor));
                }
            });
        }
    }
}