package com.example.filmscollection

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filmscollection.databinding.ActivityMainBinding
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: RecyclerAdapter
    var favorites = mutableSetOf<Movie>()

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // Создаем адаптер один раз
        adapter = RecyclerAdapter(emptyList(), favorites){ movie, position ->
            toggleFavorite(movie, position)
        }

        binding.rvMovies.adapter = adapter
        binding.rvMovies.layoutManager = LinearLayoutManager(this)

        binding.btnSearch.setOnClickListener {
            hideKeyboard()
            println(favorites)
            val searchQuery = binding.etSearchQuery.text.toString()
            if (searchQuery.isNotEmpty()) {
                searchMovies(searchQuery)

            } else {
                Toast.makeText(this, "Введите название фильма", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun hideKeyboard() {
        currentFocus?.let { view ->
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
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
                Toast.makeText(this@MainActivity, "Ошибка загрузки данных", Toast.LENGTH_SHORT).show()
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
}

