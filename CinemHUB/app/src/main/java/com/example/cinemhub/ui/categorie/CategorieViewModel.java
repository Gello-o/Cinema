package com.example.cinemhub.ui.categorie;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CategorieViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CategorieViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Questa è la pagina delle categorie");
    }

    public LiveData<String> getText() {
        return mText;
    }
}