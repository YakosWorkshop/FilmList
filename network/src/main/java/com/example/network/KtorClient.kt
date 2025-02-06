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
import kotlin.Exception

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
    private val movieCache = mutableMapOf<Int, Movie>()

    suspend fun getMovieById(id: Int): ApiOperation<Movie>{
        movieCache[id]?.let { return ApiOperation.Success(it) }
        return safeApiCall {
            client.get("movie/$id") {
                parameter("api_key", apiKey)
            }.body<Movie>()
                .also { movieCache[id] = it }
        }
    }

    suspend fun getMovieByTitle(title: String): ApiOperation<List<Movie>> {
        return safeApiCall {
            val response: MovieResponse = client.get("search/movie") {
                parameter("api_key", apiKey)
                parameter("query", title)
            }.body()
            response.results
        }
    }

    private inline fun <T> safeApiCall(apiCall: () -> T): ApiOperation<T> {
        return try {
            ApiOperation.Success(data = apiCall())
        } catch (e: Exception) {
            ApiOperation.Failure(exception = e)
        }
    }
}

sealed interface ApiOperation<T> {
    data class Success<T>(val data: T): ApiOperation<T>
    data class Failure<T>(val exception: Exception): ApiOperation<T>

    fun onSuccess(block: (T) -> Unit): ApiOperation<T> {
        if (this is Success) block(data)
        return this
    }

    fun onFailure(block: (Exception) -> Unit): ApiOperation<T> {
        if (this is Failure) block(exception)
        return this
    }
}

@Serializable
data class MovieResponse (
    val results: List<Movie>
)



