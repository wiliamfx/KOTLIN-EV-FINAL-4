package com.example.gastelu_ev4;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class DetallePeliculaActivity extends AppCompatActivity {
    private ImageView imgPelicula;
    private TextView txtIndice, txtNombre, txtFecha, txtDescripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_pelicula);

        imgPelicula = findViewById(R.id.imgPelicula);
        txtIndice = findViewById(R.id.txtIndice);
        txtNombre = findViewById(R.id.txtNombre);
        txtFecha = findViewById(R.id.txtFecha);
        txtDescripcion = findViewById(R.id.txtDescripcion);


        Intent intent = getIntent();
        Long id = intent.getLongExtra("ID", -1);
        String nombre = intent.getStringExtra("NOMBRE");
        String fecha = intent.getStringExtra("FECHA");
        String descripcion = intent.getStringExtra("DESCRIPCION");
        String posterPath = intent.getStringExtra("POSTER_PATH");


        txtIndice.setText("INDICE: " + id);
        txtFecha.setText("Fecha de Lanzamiento: " + fecha);
        txtNombre.setText(nombre);
        txtDescripcion.setText(descripcion);


        String urlImagen = "https://image.tmdb.org/t/p/original/";
        Glide.with(this).load(urlImagen + posterPath).into(imgPelicula);
    }
}