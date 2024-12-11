package com.example.filmscollection

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.filmscollection.databinding.MovieItemBinding
import com.squareup.picasso.Picasso

class FavoritesAdapter(
    private var favoriteMovies: MutableSet<Movie>,
    private val onLikeClick: (Movie, Int) -> Unit
) : RecyclerView.Adapter<FavoritesAdapter.MovieViewHolder>() {

    // Преобразуем множество в список для работы с позициями
    private val favoriteMoviesList = favoriteMovies.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = favoriteMoviesList[position]

        holder.binding.movieTitle.text = movie.title
        holder.binding.movieYear.text = movie.year
        Picasso.get().load(movie.poster).into(holder.binding.moviePoster)

        holder.binding.favoriteButton.setImageResource(R.drawable.favorite)

        // Устанавливаем слушатель для кнопки лайка
        holder.binding.favoriteButton.setOnClickListener {
            onLikeClick(movie, position)
        }
    }

    override fun getItemCount(): Int = favoriteMovies.size

    fun removeMovie(movie: Movie) {
        movie.isFavorite = true
        favoriteMovies.remove(movie)
        favoriteMoviesList.clear()
        favoriteMoviesList.addAll(favoriteMovies)
        notifyDataSetChanged()
    }

    class MovieViewHolder(val binding: MovieItemBinding) : RecyclerView.ViewHolder(binding.root) {
    }
}
