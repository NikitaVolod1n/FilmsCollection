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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Создаем адаптер один раз
        adapter = RecyclerAdapter(emptyList())
        binding.rvMovies.adapter = adapter
        binding.rvMovies.layoutManager = LinearLayoutManager(this)

        binding.btnSearch.setOnClickListener {
            this.currentFocus?.let { view ->
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                imm?.hideSoftInputFromWindow(view.windowToken, 0)
            }
            val searchQuery = binding.etSearchQuery.text.toString()  // Преобразуем в строку
            if (searchQuery.isNotEmpty()) {
                lifecycleScope.launch {
                    try {
                        val response = restMoviesAPI.searchMovies(searchQuery, "a30e87f4")  // Используйте свой ключ API
                        val movies = response.movies

                        // Обновляем список фильмов в адаптере
                        adapter.updateMovies(movies)

                    } catch (e: Exception) {
                        // Обработка ошибок
                        Log.e("Error", "Error fetching movies", e)
                    }
                }
            }
        }
    }
}

