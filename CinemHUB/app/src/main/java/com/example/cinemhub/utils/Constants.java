package com.example.cinemhub.utils;

import java.util.ArrayList;
import java.util.List;

public class Constants {

    public static final String BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w500";
    public static final String BASE_URL = "Http://api.themoviedb.org";
    public static final String API_KEY = "740ef79d64b588653371072cdee99a0f";
    public static final String YT_API_KEY = "AIzaSyC95r_3BNU_BxvSUE7ZyXKrar3dc127rVk";
    public static final String EXTRA_TITLE = "videotitle";
    public static final String EXTRA_VIDEOID = "videoid";
    public static final int RECOVERY_DIALOG_REQUEST = 1;
    public static final String LINGUA = "en-US";


    //ID associati ai generi di film presenti nella API di TMDB
    public static final Integer ACTION = 28;
    public static final Integer ADVENTURE = 12;
    public static final Integer ANIMATION = 16;
    public static final Integer COMEDY = 35;
    public static final Integer CRIME = 80;
    public static final Integer DOCUMENTARY = 99;
    public static final Integer DRAMA = 18;
    public static final Integer FAMILY = 10751;
    public static final Integer FANTASY = 14;
    public static final Integer HISTORY = 36;
    public static final Integer HORROR = 27;
    public static final Integer MUSIC = 10402;
    public static final Integer MYSTERY = 9648;
    public static final Integer ROMANCE = 10749;
    public static final Integer SCIFI = 878;
    public static final Integer THRILLER = 53;
    public static final Integer TVMOVIE = 10770;
    public static final Integer WAR = 10752;
    public static final Integer WESTERN = 37;

    //Costruzione della lista dei generi che andrà passata alla classe FilterHandler.
    public static List<String> setGenre() {
        List<String> listVote = new ArrayList<>();
        listVote.add("");
        listVote.add("Action");
        listVote.add("Adventure");
        listVote.add("Animation");
        listVote.add("Comedy");
        listVote.add("Crime");
        listVote.add("Documentary");
        listVote.add("Drama");
        listVote.add("Family");
        listVote.add("Fantasy");
        listVote.add("History");
        listVote.add("Horror");
        listVote.add("Music");
        listVote.add("Mystery");
        listVote.add("Romance");
        listVote.add("SciFi");
        listVote.add("Thriller");
        listVote.add("Tv-Movie");
        listVote.add("War");
        listVote.add("Western");

        return listVote;
    }



}
