package com.example.filmscollection

import com.google.gson.annotations.SerializedName
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {
    @GET("/")
    suspend fun searchMovies(@Query("s") name: String, @Query("apikey") apiKey: String): search
}

var retrofit = Retrofit.Builder()
    .baseUrl("https://www.omdbapi.com/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

var restMoviesAPI = retrofit.create(MovieApiService::class.java)

data class search(@SerializedName("Search") val movies: List<Movie>)