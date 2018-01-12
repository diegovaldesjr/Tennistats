package com.diegovaldesjr.tennistats.io;

import com.diegovaldesjr.tennistats.io.body.JugadaBody;
import com.diegovaldesjr.tennistats.io.body.JugadorBody;
import com.diegovaldesjr.tennistats.io.body.PartidoBody;
import com.diegovaldesjr.tennistats.io.body.SaqueBody;
import com.diegovaldesjr.tennistats.io.body.SetBody;
import com.diegovaldesjr.tennistats.io.response.JugadorResponse;
import com.diegovaldesjr.tennistats.io.response.LoginResponse;
import com.diegovaldesjr.tennistats.io.response.PartidoResponse;
import com.diegovaldesjr.tennistats.io.response.RegistrarResponse;
import com.diegovaldesjr.tennistats.io.response.StatusResponse;
import com.diegovaldesjr.tennistats.model.Jugador;
import com.diegovaldesjr.tennistats.io.body.LoginBody;
import com.diegovaldesjr.tennistats.view.CrearPartidoActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by diego on 25/11/2017.
 */

public interface TennisApiService {

    //LOGIN Y REGISTRO
    @POST("login")
    Call<LoginResponse> getLogin(@Body LoginBody loginBody);

    @POST("register")
    Call<RegistrarResponse> registrar(@Body LoginBody loginBody);

    //GETS
    @GET("partidos")
    Call<ArrayList<PartidoResponse>> getPartidos(@Header("Authorization") String token);

    @GET("jugadores")
    Call<ArrayList<JugadorResponse>> getJugadores(@Header("Authorization") String token);

    @GET("partidos/{id}/sets")
    Call<StatusResponse> getSets(@Path("id") int id, @Header("Authorization") String token);

    @GET("jugadas/indice/{idSet}")
    Call<StatusResponse> getIndiceJugadas(@Path("id") int id, @Header("Authorization") String token);

    @GET("saques/indice/{idSet}")
    Call<StatusResponse> getIndiceSaques(@Path("id") int id, @Header("Authorization") String token);

    //POST
    @POST("partidos")
    Call<StatusResponse> createPartido(@Header("Authorization") String token, @Body PartidoBody partido);

    @POST("jugadores")
    Call<StatusResponse> createJugador(@Header("Authorization") String token, @Body JugadorBody jugador);

    @PUT("sets/{id}")
    Call<StatusResponse> actualizarSets(@Path("id") int id, @Header("Authorization") String token, @Body SetBody set);

    @POST("saques")
    Call<StatusResponse> createSaque(@Header("Authorization") String token, @Body SaqueBody saque);

    @POST("jugadores")
    Call<StatusResponse> createJugada(@Header("Authorization") String token, @Body JugadaBody jugada);
    //
    @GET("api/partidos/{id}")
    Call<PartidoResponse> getPartido(@Path("id") int id, @Header("Authorization") String token);

    @GET("jugadores/{id}")
    Call<JugadorResponse> getJugador(@Path("id") int id, @Header("Authorization") String token);

}
