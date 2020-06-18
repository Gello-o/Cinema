package com.example.cinemhub.ui.categorie;

import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cinemhub.model.Movie;
import com.example.cinemhub.model.MoviesRepository;
import com.example.cinemhub.ui.nuovi_arrivi.NuoviArriviViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MostraCategoriaViewModel extends ViewModel {
    private MutableLiveData<List<Movie>> pagina;
    private static final String TAG = "MostraCategorieFragment";
    int index = 1;
    int genere;

    private MoviesRepository repo;

    public MutableLiveData<List<Movie>> getGenere(int genere) {
        this.genere = genere;
        if(pagina == null) {
            pagina = new MutableLiveData<>();
            repo = MoviesRepository.getInstance();
        }
        return pagina;
    }


    private class ShowMoreAsyncTask extends AsyncTask<Void, Void, LiveData<List<Movie>>> {

        @Override
        protected LiveData<List<Movie>> doInBackground(Void... voids) {
            Log.d(TAG, "indice " + index);
            repo.getGenres(genere, index, pagina);
            if(index == 10) {
                stopRepeatingTask();
                resetIndex();
            }
            else
                index++;
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

        timer.schedule(timerTask, 0, 900);

    }

    public void stopRepeatingTask(){
        timer.cancel();
        timer.purge();
    }

    public void resetIndex(){
        index = 1;
    }
}