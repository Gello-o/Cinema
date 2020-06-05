package com.example.cinemhub;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.MotionEvent;
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
    private RatingBar ratingBar;
    private Button button, editRatingButton, deleteButton;
    private UserRatingDB userRatingDB;
    private AlertDialog.Builder alertDialog;

    float submittedRating;
    public String id, commento;
    boolean app = true;



    //costructor allow findview and Toast
    public UserOperation(Activity activity,Context context){
        this.activity = activity;
        this.context = context;
    }

    public void eseguiUser(String _id) {
        // cancellaDb();
        id = _id;


        button = this.activity.findViewById(R.id.submit_rating);
        ratingBar = this.activity.findViewById(R.id.ratingBar);
        editText = this.activity.findViewById(R.id.user_overview);
        showVote = this.activity.findViewById(R.id.show_vote);
        warning = this.activity.findViewById(R.id.warning);
        comment = this.activity.findViewById(R.id.comment);
        editText = this.activity.findViewById(R.id.user_overview);
        editRatingButton = this.activity.findViewById(R.id.edit_rating);
        usersRating = this.activity.findViewById(R.id.users_rating2);
        deleteButton = this.activity.findViewById(R.id.delete_button);


        writeComment();

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
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
            @Override
            public void onClick(View v) {
                alertDialog = new AlertDialog.Builder(context);

                if (checkUser()){
                    Log.d(TAG,"entrato if");
                    alertDialog.setMessage("Delete the comment?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    deleteUser();
                                    dialog.cancel();
                                    Toast.makeText(context, "Comment Deleted", Toast.LENGTH_SHORT).show();

                                }
                            })

                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert = alertDialog.create();
                    alert.show();
              }else{
                    Log.d(TAG,"entarto nell'else");
                    Toast.makeText(context, "There is no comment to delete", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void saveUser(String _id){
        userRatingDB = new UserRatingDB();

        String name = editText.getText().toString();
        userRatingDB.setMovie_id(Integer.parseInt(_id));
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
        UserRatingDB user;
        user = FavoriteDB.getInstance().dbInterface().getUserInfo(Integer.parseInt(id));
        if(user != null){
            return true;
        }else {
            return false;
        }
    }

    private void getUsereInfo(){
        if(checkUser()){
            UserRatingDB user;
            user = FavoriteDB.getInstance().dbInterface().getUserInfo(Integer.parseInt(id));
            commento = user.getOverview();
            submittedRating = user.getRating();
        }
    }

    private void writeComment(){
        if(checkUser()){//==true)
            getUsereInfo();
            usersRating.setText((int) submittedRating + " / 10");
            comment.setText(commento);
        }else{
            usersRating.setText("0 / 10");
            comment.setText("");
        }
    }

    //per cancellare tutta la table solo per debug
    private void cancellaDb() {
        List<UserRatingDB> line = FavoriteDB.getInstance().dbInterface().getUserOverview();
        FavoriteDB.getInstance().clearAllTables();
        Log.d(TAG, "db size: " + line.size());
    }

    private void deleteUser(){
        UserRatingDB user;
        user = FavoriteDB.getInstance().dbInterface().getUserInfo(Integer.parseInt(id));
        if(user != null){
            FavoriteDB.getInstance().dbInterface().deleteUser(user);
            ratingBar.setRating(0);
            usersRating.setText("0 / 10");
            comment.setText("");
        }
    }

}
