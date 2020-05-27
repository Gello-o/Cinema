package com.example.cinemhub;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.example.cinemhub.utils.Constants;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class VideoPlayer extends YouTubeBaseActivity {
    private final static String TAG = "VideoPlayer";
    private YouTubePlayerView playerView;
    private YouTubePlayer.OnInitializedListener initializedListener;
    private Context context = this;

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
                    youTubePlayer.cueVideo(key);
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
                    youTubePlayer.cueVideo("W4hTJybfU7s");
                }

                @Override
                public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                    Log.d(TAG,"fail no key");
                }
            };
        }
        playerView.initialize(Constants.YT_API_KEY, initializedListener);

    }


}