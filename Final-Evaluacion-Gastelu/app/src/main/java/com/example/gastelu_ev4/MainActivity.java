package com.example.gastelu_ev4;

import android.os.Bundle;
import android.util.Log;

import android.widget.Button;
import android.widget.EditText;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gastelu_ev4.Adaptador.AdaptadorPelicula;
import com.example.gastelu_ev4.Interfaz.InterfazPelicula;
import com.example.gastelu_ev4.Modelo.Pelicula;
import com.example.gastelu_ev4.Modelo.Resultado;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private AdaptadorPelicula adaptadorPelicula;
    private EditText editTextSearch;
    private Button buttonSearch;
    private Retrofit rfit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        recyclerView = findViewById(R.id.listado);
        editTextSearch = findViewById(R.id.txtbuscar);
        buttonSearch = findViewById(R.id.btnbuscar);

        adaptadorPelicula = new AdaptadorPelicula(this);
        GridLayoutManager grid = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(grid);
        recyclerView.setAdapter(adaptadorPelicula);


        rfit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        cargarTodasLasPeliculas();


        buttonSearch.setOnClickListener(v -> {
            String query = editTextSearch.getText().toString().trim(); // Elimina espacios en blanco
            if (!query.isEmpty()) {
                buscarPeliculas(query);
            } else {
                Log.d("MainActivity", "El campo de búsqueda está vacío.");
            }
        });
    }

    private void cargarTodasLasPeliculas() {
        int currentPage = 1;
        cargarPeliculas(currentPage);
    }

    private void cargarPeliculas(int page) {
        String apiKey = "7be72508776961f3948639fbd796bccd";
        InterfazPelicula s = rfit.create(InterfazPelicula.class);
        Call<Resultado> respuesta = s.obtenerDatos(apiKey, page);

        respuesta.enqueue(new Callback<Resultado>() {
            @Override
            public void onResponse(Call<Resultado> call, Response<Resultado> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ArrayList<Pelicula> peliculas = response.body().getResults();
                    adaptadorPelicula.agregarRegistros(peliculas);




                    if (page < 500) {
                        cargarPeliculas(page + 1);
                    }
                } else {

                    Log.d("MainActivity", "Error en la respuesta: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Resultado> call, Throwable t) {

                Log.d("MainActivity", "Fallo en la solicitud: " + t.getMessage());
            }
        });
    }

    private void buscarPeliculas(String query) {
        String apiKey = "7be72508776961f3948639fbd796bccd";
        InterfazPelicula s = rfit.create(InterfazPelicula.class);


        Call<Resultado> respuesta = s.buscarPeliculas(apiKey, query);

        respuesta.enqueue(new Callback<Resultado>() {
            @Override
            public void onResponse(Call<Resultado> call, Response<Resultado> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ArrayList<Pelicula> peliculas = response.body().getResults();
                    adaptadorPelicula.reemplazarRegistros(peliculas); // Para limpiar y reemplazar

                } else {

                    Log.d("MainActivity", "Error en la respuesta: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Resultado> call, Throwable t) {

                Log.d("MainActivity", "Fallo en la solicitud: " + t.getMessage());
            }
        });
    }
}