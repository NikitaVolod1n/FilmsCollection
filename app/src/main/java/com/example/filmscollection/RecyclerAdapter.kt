package com.example.filmscollection

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.filmscollection.databinding.MovieItemBinding
import com.google.gson.annotations.SerializedName
import com.squareup.picasso.Picasso

class RecyclerAdapter(
    private var movies: List<Movie>,
): RecyclerView.Adapter<RecyclerAdapter.MovieViewHolder>() {


    class MovieViewHolder(val binding: MovieItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        // Инфлейтим layout с использованием View Binding
        val binding = MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    @SuppressLint("CheckResult")
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]

        // Устанавливаем данные через binding
        holder.binding.movieTitle.text = movie.title
        holder.binding.movieYear.text = movie.year
        Picasso.get().load(movie.poster).into(holder.binding.moviePoster)
    }

    fun updateMovies(newMovies: List<Movie>) {
        movies = newMovies
        notifyDataSetChanged()
    }
}

data class Movie(
    @SerializedName("Title") val title: String,
    @SerializedName("Year")val year: String,
    @SerializedName("Poster")val poster: String
)