package com.example.filmscollection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filmscollection.databinding.FragmentFavoritesBinding

class FavoritesFragment(val favoriteMovies: MutableSet<Movie>) : Fragment() {

    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var adapter: FavoritesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = FavoritesAdapter(favoriteMovies) { movie, position ->
            toggleFavorite(movie, position)
        }

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun toggleFavorite(movie: Movie, position: Int) {
        movie.isFavorite = false
        adapter.removeMovie(movie)
        adapter.notifyDataSetChanged()
    }

    fun updateFavorites(newFavorites: List<Movie>) {
        favoriteMovies.clear()
        favoriteMovies.addAll(newFavorites)
        adapter.notifyDataSetChanged()
    }
}
