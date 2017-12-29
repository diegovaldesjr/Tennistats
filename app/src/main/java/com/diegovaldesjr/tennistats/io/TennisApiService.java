package com.diegovaldesjr.tennistats.io;

import com.diegovaldesjr.tennistats.io.response.JugadorResponse;
import com.diegovaldesjr.tennistats.io.response.LoginResponse;
import com.diegovaldesjr.tennistats.io.response.PartidoResponse;
import com.diegovaldesjr.tennistats.io.response.RegistrarResponse;
import com.diegovaldesjr.tennistats.model.Jugador;
import com.diegovaldesjr.tennistats.io.body.LoginBody;
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

    @POST("login")
    Call<LoginResponse> getLogin(@Body LoginBody loginBody);

    @POST("register")
    Call<RegistrarResponse> registrar(@Body LoginBody loginBody);

    @GET("api/partidos/{id}")
    Call<PartidoResponse> getPartido(@Path("id") int id, @Header("Authorization") String token);

    @GET("partidos")
    Call<ArrayList<PartidoResponse>> getPartidos(@Header("Authorization") String token);

    @POST("partidos")
    Call<PartidoResponse> createPartido(@Header("Authorization") String token, @Body CrearPartidoActivity.PartidoBody partido);

    @GET("jugadores/{id}")
    Call<JugadorResponse> getJugador(@Path("id") int id, @Header("Authorization") String token);

    @GET("jugadores")
    Call<ArrayList<JugadorResponse>> getJugadores(@Header("Authorization") String token);

    @POST("jugadores")
    Call<JugadorResponse> createJugador(@Header("Authorization") String token, @Body Jugador jugador);

}
