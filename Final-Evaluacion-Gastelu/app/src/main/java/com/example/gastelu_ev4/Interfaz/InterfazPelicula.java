package com.example.gastelu_ev4.Interfaz;

import com.example.gastelu_ev4.Modelo.Resultado;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface InterfazPelicula {
    @GET("movie/popular")
    Call<Resultado> obtenerDatos(@Query("api_key") String apiKey, @Query("page") long page);

    @GET("search/movie")
    Call<Resultado> buscarPeliculas(@Query("api_key") String apiKey, @Query("query") String query);
}