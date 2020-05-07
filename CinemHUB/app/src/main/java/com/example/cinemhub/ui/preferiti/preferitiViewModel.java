package com.example.cinemhub.ui.preferiti;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PreferitiViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PreferitiViewModel() {

    }

    public LiveData<String> getText() {
        return mText;
    }
}