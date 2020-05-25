package com.example.cinemhub;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class VideoPlayer extends YouTubeBaseActivity {
    private final static String TAG = "VideoPlayer";
    private YouTubePlayerView playerView;
    private YouTubePlayer.OnInitializedListener initializedListener;
    private Context context = this;
    private final String YT_API_KEY = "AIzaSyC95r_3BNU_BxvSUE7ZyXKrar3dc127rVk";
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        //setContentView(R.layout.youtube_layout);
        playerView = findViewById(R.id.player);

        Intent intent = getIntent();
        if(intent.hasExtra("key")){
            String key = intent.getExtras().getString("key");
            Log.d(TAG, "key: " + key);

            initializedListener = new YouTubePlayer.OnInitializedListener() {
                @Override
                public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                    youTubePlayer.loadVideo(key);
                    Log.d(TAG,"success");
                }

                @Override
                public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                    Log.d(TAG,"fail");
                }
            };
        }
        else{
            initializedListener = new YouTubePlayer.OnInitializedListener() {
                @Override
                public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                    Log.d(TAG,"success no key");
                    youTubePlayer.loadVideo("https://www.youtube.com/embed/W4hTJybfU7s");
                }

                @Override
                public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                    Log.d(TAG,"fail no key");
                }
            };
        }
        playerView.initialize(YT_API_KEY, initializedListener);

    }


}