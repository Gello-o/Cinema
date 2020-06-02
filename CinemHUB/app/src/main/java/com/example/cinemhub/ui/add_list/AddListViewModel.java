package com.example.cinemhub.ui.add_list;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.cinemhub.model.Movie;
import com.example.cinemhub.model.MoviesRepository;
import java.util.List;

public class AddListViewModel extends ViewModel {

    private MutableLiveData<List<Movie>> mText;
    MoviesRepository repo;

    public LiveData<List<Movie>> getText() {
        if(mText == null){
            mText = new MutableLiveData<>();
            repo = MoviesRepository.getInstance();
            for(int i=1; i<3; i++) {
                repo.searchMovie(i, "iron+man", mText);
            }
        }
        return mText;
    }


}