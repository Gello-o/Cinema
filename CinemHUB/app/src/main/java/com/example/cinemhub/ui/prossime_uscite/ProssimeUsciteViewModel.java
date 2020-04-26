package com.example.cinemhub.ui.prossime_uscite;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProssimeUsciteViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ProssimeUsciteViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Questa è la pagina delle prossime uscite");
    }

    public LiveData<String> getText() {
        return mText;
    }
}