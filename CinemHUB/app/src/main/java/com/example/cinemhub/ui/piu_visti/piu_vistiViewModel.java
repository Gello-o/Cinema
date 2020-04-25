package com.example.cinemhub.ui.piu_visti;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class piu_vistiViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public piu_vistiViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Questa è la pagina dei film più visti");
    }

    public LiveData<String> getText() {
        return mText;
    }
}