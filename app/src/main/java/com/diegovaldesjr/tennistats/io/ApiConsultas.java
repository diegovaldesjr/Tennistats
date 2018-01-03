package com.diegovaldesjr.tennistats.io;

/**
 * Created by diego on 29/12/2017.
 */

public class ApiConsultas {

/*
    Crear jugador

    Call<JugadorResponse> call = TennisApiAdapter.getApiService().createJugador("Bearer "+user.getString("access_token", ""), jugador);

        call.enqueue(new Callback<JugadorResponse>() {
            @Override
            public void onResponse(Call<JugadorResponse> call, Response<JugadorResponse> response) {
                if(response.isSuccessful()){
                    finish();
                }
            }

            @Override
            public void onFailure(Call<JugadorResponse> call, Throwable t) {
                Toast.makeText(CrearJugadorActivity.this, t.getMessage(),
                        Toast.LENGTH_SHORT).show();
                Log.d("Error", "Error Respuesta en JSON: " +t.getMessage());
            }
        });

    Consultar todos los partidos
    Call<ArrayList<PartidoResponse>> call = TennisApiAdapter.getApiService().getPartidos("Bearer "+user.getString("access_token", ""));

        call.enqueue(new Callback<ArrayList<PartidoResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<PartidoResponse>> call, Response<ArrayList<PartidoResponse>> response) {
                if(response.isSuccessful()){
                    ArrayList<PartidoResponse> data = response.body();

                    for(int i=0; i<data.size(); i++){
                        PartidoResponse row = data.get(i);

                        try {
                            SimpleDateFormat parseador = new SimpleDateFormat("yyyy-MM-dd");
                            Date fecha = parseador.parse(row.getFecha());

                            partidos.add(new Partido(
                                    Integer.parseInt(row.getIdPartido()),
                                    Integer.parseInt(row.getJugador().getIdJugador()),
                                    row.getJugador().getNombre()+" "+row.getJugador().getApellido(),
                                    fecha,
                                    row.getCategoria(),
                                    row.getSets()
                            ));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    actualizarRecyclerView(view, partidos);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<PartidoResponse>> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage().toString(),
                        Toast.LENGTH_SHORT).show();
                Log.d("Error", "Error Respuesta en JSON: " +t.getMessage());
            }
        });

    Consultar jugadores
    Call<ArrayList<JugadorResponse>> call = TennisApiAdapter.getApiService().getJugadores("Bearer "+user.getString("access_token", ""));

        call.enqueue(new Callback<ArrayList<JugadorResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<JugadorResponse>> call, Response<ArrayList<JugadorResponse>> response) {
                if(response.isSuccessful()){
                    ArrayList<JugadorResponse> data = response.body();

                    for(int i=0; i<data.size(); i++){
                        JugadorResponse row = data.get(i);
                        jugadores.add(new Jugador(
                                Integer.parseInt(row.getIdJugador()),
                                row.getNombre(),
                                row.getApellido()
                        ));
                    }
                    actualizarRecyclerView(view, jugadores);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<JugadorResponse>> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage().toString(),
                        Toast.LENGTH_SHORT).show();
                Log.d("Error", "Error Respuesta en JSON: " +t.getMessage());
            }
        });

    Crear Partido
    Call<PartidoResponse> call = TennisApiAdapter.getApiService().createPartido("Bearer "+user.getString("access_token", ""), partido);

            call.enqueue(new Callback<PartidoResponse>() {
                @Override
                public void onResponse(Call<PartidoResponse> call, Response<PartidoResponse> response) {
                    if(response.isSuccessful()){
                        avanzarCancha(response.body());
                        //finish();
                    }
                }

                @Override
                public void onFailure(Call<PartidoResponse> call, Throwable t) {
                    Toast.makeText(CrearPartidoActivity.this, t.getMessage(),
                            Toast.LENGTH_SHORT).show();
                    Log.d("Error", "Error Respuesta en JSON: " +t.getMessage());
                }
            });
 */

}
