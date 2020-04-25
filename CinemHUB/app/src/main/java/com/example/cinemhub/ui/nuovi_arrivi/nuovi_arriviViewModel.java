package com.example.cinemhub.ui.nuovi_arrivi;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class nuovi_arriviViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public nuovi_arriviViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Questa è la pagina dei nuovi arrivi");
    }

    public LiveData<String> getText() {
        return mText;
    }
}