package com.example.cinemhub.ui.add_list;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AddListViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AddListViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Questa è la pagina dei film aggiunti alla tua lista");
    }

    public LiveData<String> getText() {
        return mText;
    }
}