package com.example.filmscollection

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.filmscollection.databinding.MovieItemBinding
import com.google.gson.annotations.SerializedName
import com.squareup.picasso.Picasso

class RecyclerAdapter(
    private var movies: List<Movie>,
    private var favorites: Set<Movie>,
    private val onFavoriteClick: (Movie, Int) -> Unit
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

        // Привязываем данные к элементам в layout через binding
        holder.binding.movieTitle.text = movie.title
        holder.binding.movieYear.text = movie.year
        Picasso.get().load(movie.poster).into(holder.binding.moviePoster)

        val favoriteIcon = if (favorites.any { it.imdbID == movie.imdbID }) R.drawable.favorite else R.drawable.un_favorite
        holder.binding.favoriteButton.setImageResource(favoriteIcon)

        // Устанавливаем обработчик клика
        holder.binding.favoriteButton.setOnClickListener {
            movie.isFavorite = !movie.isFavorite
            onFavoriteClick(movie, position)
            notifyItemChanged(position)
        }

    }

    fun updateMovies(newMovies: List<Movie>) {
        movies = newMovies
        notifyDataSetChanged()
    }
}

data class Movie(
    @SerializedName("Title") val title: String,
    @SerializedName("Year")val year: String,
    @SerializedName("Poster")val poster: String,
    @SerializedName("imdbID") val imdbID: String,
    @SerializedName("Type")val type: String,
    var isFavorite: Boolean
)