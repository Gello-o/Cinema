package com.example.cinemhub.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.example.cinemhub.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class RetrieveFeedTask extends AsyncTask<String, Void, Bitmap[]> {

    private Exception exception;

    public Bitmap[] doInBackground(String... imageUrl) {
        //try {
        URL[] url = new URL[imageUrl.length];
        try {
            for(int i=0; i<imageUrl.length; ++i)
                url[i] = new URL(imageUrl[i]);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        if(url == null) System.out.println("Null Pointer Exception1");
        HttpURLConnection[] connection  = new HttpURLConnection[imageUrl.length];
        try {
            assert url != null;
            for(int i=0; i<imageUrl.length; ++i)
                connection[i] = (HttpURLConnection) url[i].openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

        InputStream[] is = new InputStream[imageUrl.length];
        try {
            assert connection != null;
            for(int i=0; i<imageUrl.length; ++i)
                is[i] = connection[i].getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Bitmap[] img = new Bitmap[imageUrl.length];
        for(int i=0; i<imageUrl.length; ++i)
            img[i] = BitmapFactory.decodeStream(is[i]);

        return img.clone();
        /*} catch (Exception e) {
            this.exception = e;
            System.out.println("Null Pointer Exception");
            return null;
        }*/
    }
}