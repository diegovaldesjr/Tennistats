package com.diegovaldesjr.tennistats.io;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by diego on 25/11/2017.
 */

public class TennisApiAdapter {

    private static TennisApiService API_SERVICE;

    public static TennisApiService getApiService() {

        // Creamos un interceptor y le indicamos el log level a usar
        //Ver datos de la peticion realizada
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        // Asociamos el interceptor a las peticiones
        //Respuesta del servidor
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        //String baseUrl = "http://25.41.117.103:8000/";
        String baseUrl = "http://192.168.110.107:8000/";

        if (API_SERVICE == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build()) // <-- usamos el log level
                    .build();
            API_SERVICE = retrofit.create(TennisApiService.class);
        }

        return API_SERVICE;
    }
}
