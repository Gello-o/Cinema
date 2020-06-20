package com.example.cinemhub.api;

import com.example.cinemhub.utils.Constants;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/*l'oggetto Client utilizza la libreria Retrofit per immagazzinare una pagina di film in formato JSON
e convertirla in codice Java*/

public class Client {

    private static Retrofit retrofit = null;

    public static Retrofit getClient(){
        if(retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
