package com.example.filmlist.repositories

import com.example.network.ApiOperation
import com.example.network.KtorClient
import com.example.network.model.Movie
import javax.inject.Inject

class MovieRepository @Inject constructor(private val ktorClient: KtorClient){
    suspend fun fetchMovie(movieId: Int): ApiOperation<Movie> {
        return ktorClient.getMovieById(movieId)
    }

    suspend fun  fetchMovieSearch(title: String): ApiOperation<List<Movie>> {
        return ktorClient.getMovieByTitle(title)
    }
}