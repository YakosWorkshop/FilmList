package com.example.filmlist.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmlist.components.DataPoint
import com.example.filmlist.screens.MovieDetailsViewState
import com.example.filmlist.repositories.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val movieRepository: MovieRepository
): ViewModel() {
    private val _internalStorageFlow = MutableStateFlow<MovieDetailsViewState>(
        value = MovieDetailsViewState.Loading
    )
    val stateFlow = _internalStorageFlow.asStateFlow()

    fun fetchMovie(movieId: Int) = viewModelScope.launch {
        _internalStorageFlow.update { return@update MovieDetailsViewState.Loading }
        movieRepository.fetchMovie(movieId).onSuccess { movie ->
            val dataPoints = buildList {
                add(DataPoint("Overview", movie.overview))
                add(DataPoint("Score", movie.score.toString()))
            }
            _internalStorageFlow.update {
                return@update MovieDetailsViewState.Success(
                    movie = movie,
                    movieDataPoints = dataPoints
                )
            }

        }.onFailure { exception ->
            _internalStorageFlow.update {
                return@update MovieDetailsViewState.Error(
                    message = exception.message ?: "Unkown error occurred"
                )
            }
        }
    }
}
