package com.example.cinemhub.ui.nuovi_arrivi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cinemhub.model.Movie;
import com.example.cinemhub.model.MoviesRepository;


import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class NuoviArriviViewModel extends ViewModel {
    private static final String TAG = "NuoviArriviViewModel";
    private MutableLiveData<List<Movie>> pagina;
    boolean cinquePagine = false;
    int index = 1;
    MoviesRepository repo;

    public MutableLiveData<List<Movie>> getProssimeUscite(Context context) {
        repo = MoviesRepository.getInstance();

        if(pagina == null) {
            pagina = new MutableLiveData<>();
            repo = MoviesRepository.getInstance();
            repo.getMovies("upcoming", 1, pagina);
        }
        return pagina;

    }


    private class ShowMoreAsyncTask extends AsyncTask<Void, Void, LiveData<List<Movie>>> {

        @Override
        protected LiveData<List<Movie>> doInBackground(Void... voids) {
            index++;
            Log.d(TAG, "indice " + index);
            repo.getMovies("upcoming", index, pagina);
            return pagina;
        }
    }

    private Timer timer;
    private TimerTask timerTask;

    public void setRepeatingAsyncTask(){
        final Handler handler = new Handler();
        timer = new Timer();
        pagina.setValue(new ArrayList<>());
        Log.d(TAG, "valore indice: " + index);

        timerTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ShowMoreAsyncTask task = new ShowMoreAsyncTask();
                            task.execute();
                        } catch (Exception e) {
                            Log.d(TAG, "eccezione");
                        }
                    }
                });
            }
        };
        timer.schedule(timerTask, 0, 10*1000);
    }

    public void stopRepeatingTask(){
        timer.cancel();
        timer.purge();
    }

    public void nullifyTask(){
        timer = null;
        timerTask = null;
    }

}
