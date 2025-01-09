package com.example.network

import com.example.network.model.Movie
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

class KtorClient {
    private val client = HttpClient(OkHttp) {
        defaultRequest { url("https://api.themoviedb.org/3/") }

        install(Logging) {
            logger = Logger.SIMPLE
        }
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }

    private val apiKey = BuildConfig.TMDB_API_KEY

    suspend fun getMovieById(id: Int): Movie {
        val movie: Movie = client.get("movie/$id") {
            parameter("api_key", apiKey)
        }.body()
        return movie
    }

    suspend fun getMovieByTitle(title: String): List<Movie> {
        val response: MovieResponse = client.get("search/movie") {
            parameter("api_key", apiKey)
            parameter("query", title)
        }.body()
        return response.results
    }
}

@Serializable
data class MovieResponse (
    val results: List<Movie>
)



