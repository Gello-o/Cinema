package com.example.cinemhub.ui.preferiti;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PreferitiViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PreferitiViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Questa è la pagina dei preferiti");
    }

    public LiveData<String> getText() {
        return mText;
    }
}