package com.diegovaldesjr.tennistats.io;

import com.diegovaldesjr.tennistats.io.response.JugadorResponse;
import com.diegovaldesjr.tennistats.io.response.LoginResponse;
import com.diegovaldesjr.tennistats.io.response.PartidoResponse;
import com.diegovaldesjr.tennistats.model.Jugador;
import com.diegovaldesjr.tennistats.model.UserCredentials;
import com.diegovaldesjr.tennistats.view.CrearPartidoActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by diego on 25/11/2017.
 */

public interface TennisApiService {

    @POST("oauth/token")
    Call<LoginResponse> getLogin(@Body UserCredentials json);

    @GET("api/partidos/{id}")
    Call<PartidoResponse> getPartido(@Path("id") int id, @Header("Authorization") String token);

    @GET("api/partidos")
    Call<ArrayList<PartidoResponse>> getPartidos(@Header("Authorization") String token);

    @POST("api/partidos")
    Call<PartidoResponse> createPartido(@Header("Authorization") String token, @Body CrearPartidoActivity.PartidoBody partido);

    @GET("api/jugadores/{id}")
    Call<JugadorResponse> getJugador(@Path("id") int id, @Header("Authorization") String token);

    @GET("api/jugadores")
    Call<ArrayList<JugadorResponse>> getJugadores(@Header("Authorization") String token);

    @POST("api/jugadores")
    Call<JugadorResponse> createJugador(@Header("Authorization") String token, @Body Jugador jugador);

}
