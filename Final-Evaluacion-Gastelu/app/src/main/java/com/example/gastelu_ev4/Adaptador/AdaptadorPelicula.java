package com.example.gastelu_ev4.Adaptador;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gastelu_ev4.DetallePeliculaActivity;
import com.example.gastelu_ev4.Modelo.Pelicula;
import com.example.gastelu_ev4.R;

import java.util.ArrayList;


public class AdaptadorPelicula extends RecyclerView.Adapter<AdaptadorPelicula.ViewHolder> {
    private ArrayList<Pelicula> informacion;
    private Context context;

    public AdaptadorPelicula(Context context) {
        this.informacion = new ArrayList<>();
        this.context = context;
    }

    public void agregarRegistros(ArrayList<Pelicula> peliculas) {

        informacion.addAll(peliculas);
        notifyDataSetChanged();

    }
    public void reemplazarRegistros(ArrayList<Pelicula> peliculas) {
        informacion.clear(); // Usar este en búsqueda
        informacion.addAll(peliculas);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.elemento, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pelicula pelicula = informacion.get(position);
        holder.indice.setText(String.valueOf(pelicula.getId()));
        holder.nombre.setText(pelicula.getOriginal_title());

        String urlImagen = "https://image.tmdb.org/t/p/original/";
        Glide.with(context).load(urlImagen + pelicula.getPoster_path()).centerCrop().into(holder.pelicula);

        // Set OnClickListener for the image
        holder.pelicula.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetallePeliculaActivity.class);
            intent.putExtra("ID", pelicula.getId());
            intent.putExtra("NOMBRE", pelicula.getOriginal_title());
            intent.putExtra("FECHA", pelicula.getRelease_date());
            intent.putExtra("DESCRIPCION", pelicula.getOverview());
            intent.putExtra("POSTER_PATH", pelicula.getPoster_path());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return informacion.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView indice, nombre;
        public ImageView pelicula;

        public ViewHolder(View itemView) {
            super(itemView);
            indice = itemView.findViewById(R.id.txtIndice);
            nombre = itemView.findViewById(R.id.txtNombre);
            pelicula = itemView.findViewById(R.id.imgPelicula);
        }
    }
}