package com.example.filmlist.screens


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.filmlist.components.LoadingState
import com.example.filmlist.viewmodels.SearchViewModel
import com.example.network.ApiOperation
import com.example.network.model.Movie

sealed interface MovieSearchViewState {
    object Loading : MovieSearchViewState
    data class Error(val message: String) : MovieSearchViewState
    data class Success(val movies: List<Movie>) : MovieSearchViewState
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieSearchScreen(viewModel: SearchViewModel = hiltViewModel()) {
    var active by remember { mutableStateOf(false) }
    val state by viewModel.state.collectAsState()

    Column (
        modifier = Modifier.fillMaxSize()
    ) {
        SearchBar(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            query = viewModel.query,
            onQueryChange = {viewModel.query = it},
            onSearch = {
                viewModel.searchMovies()
                active = false
                       },
            active = active,
            onActiveChange = {active = it},
            placeholder = {Text("Search movies...")}

        ) {}

        when (val viewState = state) {
            MovieSearchViewState.Loading -> {
                LoadingState()
            }

            is MovieSearchViewState.Success -> {

                val movieList = viewState.movies
                movieList.forEach { movie ->
                    Text(
                        text = movie.title,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }

            is MovieSearchViewState.Error -> {
                Text(
                    text = viewState.message,
                    color = androidx.compose.ui.graphics.Color.Red,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }

}

