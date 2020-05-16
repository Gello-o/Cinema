package com.example.cinemhub.ui.nuovi_arrivi;

import android.app.ProgressDialog;
import android.graphics.pdf.PdfRenderer;
import android.os.Build;
import android.text.method.MovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.example.cinemhub.MainActivity;
import com.example.cinemhub.model.Movie;
import com.example.cinemhub.model.MoviesRepository;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NuoviArriviViewModel extends ViewModel {
    private static final String TAG = "NuoviArriviViewModel";
    private MutableLiveData<PagedList<Movie>> pagina;
    private MutableLiveData<PagedList<Movie>> datoPaged;
    MoviesRepository db;
    

    public MutableLiveData<PagedList<Movie>> getProssimeUscite() {
        if(pagina == null) {
            db = MoviesRepository.getInstance();
            pagina = new MutableLiveData<>();
            for(int i = 1; i < 3; i++){
                db.getMovies("upcoming", i, pagina);
            }
        }
        return pagina;
    }

    /*
public class ConcertTimeViewModel extends ViewModel {
    private LiveData<PagedList<Concert>> concertList;
    private DataSource<Date, Concert> mostRecentDataSource;

    public ConcertTimeViewModel(Date firstConcertStartTime) {
        ConcertTimeDataSourceFactory dataSourceFactory =
                new ConcertTimeDataSourceFactory(firstConcertStartTime);
        mostRecentDataSource = dataSourceFactory.create();
        concertList = new LivePagedListBuilder<>(dataSourceFactory, 50)
                .setFetchExecutor(myExecutor)
                .build();
    }

    public void invalidateDataSource() {
        mostRecentDataSource.invalidate();
    }
}
*/
}
