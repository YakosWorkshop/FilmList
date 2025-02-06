package com.example.filmlist.viewmodels

import androidx.compose.material3.ListItem
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmlist.components.MovieListItem
import com.example.filmlist.repositories.MovieRepository
import com.example.filmlist.screens.MovieSearchViewState
import com.example.network.ApiOperation
import com.example.network.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private var movieRepository: MovieRepository
): ViewModel() {
    var query by mutableStateOf("")
    private val _state = MutableStateFlow<MovieSearchViewState>(MovieSearchViewState.Success(
        emptyList()))
    val state: StateFlow<MovieSearchViewState> = _state

    fun searchMovies() {
        if (query.isBlank()) return
        _state.value = MovieSearchViewState.Loading
        viewModelScope.launch {
            movieRepository.fetchMovieSearch(query)
                .onSuccess { movieList ->
                    _state.value = MovieSearchViewState.Success(movieList)
                }.onFailure { exception ->
                    _state.value = MovieSearchViewState.Error(exception.message ?: "Unknown error")
                }
        }
    }
}