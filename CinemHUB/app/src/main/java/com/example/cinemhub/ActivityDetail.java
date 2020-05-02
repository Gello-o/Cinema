package com.example.cinemhub;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cinemhub.api.Client;
import com.example.cinemhub.api.Service;
import com.example.cinemhub.model.MoviesPersistentData;
import com.example.cinemhub.model.Trailer;
import com.example.cinemhub.model.TrailerResponse;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityDetail extends AppCompatActivity {
    private static final String TAG = "ActivityDetail";
    private TextView nameOfMovie, plotSynopsis, userRating, releaseDate;
    private ImageView imageView;
    private WebView webView;
    private final String API_KEY = "740ef79d64b588653371072cdee99a0f";
    private List<Trailer> trailerList;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "creating ActivityDetail");
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = findViewById(R.id.toolbar_activity_detail);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        initCollapsingToolbar();

        imageView = findViewById(R.id.image_activity_detail);
        nameOfMovie = findViewById(R.id.title);
        plotSynopsis = findViewById(R.id.plotsynopsis);
        userRating = findViewById(R.id.usersRating);
        releaseDate = findViewById(R.id.releaseDate);
        webView = findViewById(R.id.videoWebView);

        Log.d(TAG, "Receiving intent");
        Intent intent = getIntent();

        if(intent.hasExtra("original_title")){

            String thumbnail = intent.getExtras().getString("poster_path");
            if(thumbnail == null){
                Log.d(TAG, "immagine nulla");
            }
            String movieName = intent.getExtras().getString("original_title");
            String synopsis = intent.getExtras().getString("overview");
            String rating = intent.getExtras().getString("vote_average");
            String release = intent.getExtras().getString("release_date");
            String id = intent.getExtras().getString("id");

            Glide.with(this)
                    .load(thumbnail)
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(imageView);

            nameOfMovie.setText(movieName);
            plotSynopsis.setText(synopsis);
            userRating.setText(rating);
            releaseDate.setText(release);



            //Qua bisogna passare il trailer. Penso che è d'obbligo farlo qui perché non ci sono altri punti nel codice
            //Dove possiamo passare al metodo getMovieTrailer l'id del film che serve per fare la chiamata all'api.




            /*String key;
            MutableLiveData<HashSet<Trailer>> trailer = MoviesPersistentData.getInstance().getTrailer(Integer.parseInt(id));
            if(trailer ==null) System.out.println("NULLPTR");
            else key = trailer.getClass().getKey();*/


            Service apiService = Client.getClient().create(Service.class);
            Call<TrailerResponse> call;
            call = apiService.getMovieTrailer(Integer.parseInt(id), API_KEY);

            call.enqueue(new Callback<TrailerResponse>() {
                @Override
                public void onResponse(@NonNull Call<TrailerResponse> call, @NonNull Response<TrailerResponse> response){
                    List<Trailer> trailers = response.body().getTrailers();
                    //Gli diamo il primo trailer.
                    String key = trailers.get(0).getKey();


                    //La stringa che si andrà a formare da mettere nella webview di content detail
                    String frameVideo = "<html><body><iframe src=\"https://www.youtube.com/embed/";
                    String link2 = key+"\" frameborder=\"0\" allowfullscreen></iframe></body></html>";
                    String link3 = frameVideo+link2;

                    webView.setWebViewClient(new WebViewClient() {
                        @Override
                        public boolean shouldOverrideUrlLoading(WebView view, String url) {
                            return false;
                        }
                    });
                    //Mettiamo tutto nella webview
                    WebSettings webSettings = webView.getSettings();
                    webSettings.setJavaScriptEnabled(true);
                    webView.loadData(link3, "text/html", "utf-8");


                    //Molte cose son da cancellare, ma le lascio così confonde di più le idee.
                    HashSet<Trailer> trailersSet = new HashSet<>();
                    trailersSet.addAll(trailers);

                    if(trailersSet.isEmpty())
                        Log.d(TAG, "trailerSet NULL");
                }

                @Override
                public void onFailure(@NonNull Call<TrailerResponse> call, @NonNull Throwable t) {
                    if (t.getMessage() != null)
                        Log.d("Error", t.getMessage());
                    else
                        Log.d("Error", "qualcosa è andato storto");
                }
            });
        }





        else
            Toast.makeText(this, "no api data", Toast.LENGTH_SHORT).show();



        Log.d(TAG, "end of the intent");
    }

    public void initCollapsingToolbar(){
        Log.d(TAG, "initializing CollapsingToolbar");
        final CollapsingToolbarLayout collapsingToolbarLayout;
        collapsingToolbarLayout = findViewById(R.id.ctoolbar_activity_detail);
        collapsingToolbarLayout.setTitle(" ");
        AppBarLayout appBarLayout;
        appBarLayout = findViewById(R.id.app_bar_activity_detail);
        appBarLayout.setExpanded(true);

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShown = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if(scrollRange==-1){
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if(scrollRange + verticalOffset == 0){
                    collapsingToolbarLayout.setTitle(getString(R.string.movie_details));
                    isShown = true;
                }else if(isShown){
                    collapsingToolbarLayout.setTitle(" ");
                    isShown = false;
                }
            }
        });
    }

}
