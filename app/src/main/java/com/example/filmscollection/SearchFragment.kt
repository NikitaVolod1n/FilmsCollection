package com.example.filmscollection

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filmscollection.databinding.FragmentSearchBinding
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: RecyclerAdapter
    private val favorites = mutableSetOf<Movie>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = RecyclerAdapter(emptyList(), favorites) { movie, position ->
            toggleFavorite(movie, position)
        }

        binding.rvMovies.adapter = adapter
        binding.rvMovies.layoutManager = LinearLayoutManager(requireContext())

        binding.btnSearch.setOnClickListener {
            hideKeyboard()
            println(favorites)
            val searchQuery = binding.etSearchQuery.text.toString()
            if (searchQuery.isNotEmpty()) {
                searchMovies(searchQuery)
            } else {
                Toast.makeText(requireContext(), "Введите название фильма", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun hideKeyboard() {
        requireActivity().currentFocus?.let { view ->
            val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun searchMovies(query: String) {
        lifecycleScope.launch {
            try {
                val response = restMoviesAPI.searchMovies(query, "a30e87f4")
                val movies = response.movies

                movies.forEach { movie ->
                    movie.isFavorite = favorites.contains(movie)
                }
                adapter.updateMovies(movies)
            } catch (e: Exception) {
                Log.e("Error", "Error fetching movies", e)
                Toast.makeText(requireContext(), "Ошибка загрузки данных", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun toggleFavorite(movie: Movie, position: Int) {
        if (movie.isFavorite) {
            favorites.add(movie)
        } else {
            favorites.remove(movie)
        }
        adapter.notifyItemChanged(position)  // Обновляем элемент
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
