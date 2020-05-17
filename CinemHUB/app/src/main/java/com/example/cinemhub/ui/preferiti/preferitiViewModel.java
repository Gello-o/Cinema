package com.example.cinemhub.ui.preferiti;

import android.net.DnsResolver;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cinemhub.model.Movie;

import java.util.List;

public class PreferitiViewModel extends ViewModel {

    private MutableLiveData<List<Movie>> mText;

    public MutableLiveData<List<Movie>> queryRoom() {
        if(mText == null) {
            mText = new MutableLiveData<>();

            //qua dentro ci sarà la query che recupera i preferiti e li salvi in mtext

        }
        return mText;
    }
}