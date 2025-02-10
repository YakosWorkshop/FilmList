package com.example.filmlist.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarColors
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.filmlist.components.LoadingState
import com.example.filmlist.components.MovieGridItem
import com.example.filmlist.components.MovieListItem
import com.example.filmlist.ui.theme.SoftSilver
import com.example.filmlist.ui.theme.TheaterBlack
import com.example.filmlist.viewmodels.SearchViewModel
import com.example.network.model.Movie

sealed interface MovieSearchViewState {
    object Loading : MovieSearchViewState
    data class Error(val message: String) : MovieSearchViewState
    data class Success(val movies: List<Movie>) : MovieSearchViewState
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieSearchScreen(viewModel: SearchViewModel = hiltViewModel(), onMovieClick: (Movie) -> Unit) {
    var active by remember { mutableStateOf(false) }
    val state by viewModel.state.collectAsState()

    Column (
        modifier = Modifier.fillMaxSize().background(TheaterBlack)
    ) {
        SearchBar(
            modifier = Modifier.fillMaxWidth().padding(16.dp).background(Color.Transparent),
            query = viewModel.query,
            onQueryChange = {viewModel.query = it},
            onSearch = {
                viewModel.searchMovies()
                active = false
                       },
            active = active,
            onActiveChange = {active = it},
            placeholder = {Text("Search movies...", color = SoftSilver)},
            colors = SearchBarDefaults.colors(
                containerColor = TheaterBlack,
                inputFieldColors = TextFieldDefaults.colors(
                    focusedTextColor = SoftSilver,
                    unfocusedTextColor = SoftSilver,
                    cursorColor = SoftSilver
                )
            )

        ) {
            when (val viewState = state) {
                MovieSearchViewState.Loading -> {
                    LoadingState()
                }

                is MovieSearchViewState.Success -> {

                    val movieList = viewState.movies

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier.fillMaxWidth().padding(8.dp).background(TheaterBlack)
                    ) {
                        items(movieList) { movie ->
                            MovieGridItem(
                                modifier =  Modifier.padding(8.dp),
                                movie = movie,
                                onClick = { onMovieClick(movie)}
                            )
                        }
                    }
                    movieList.forEach { movie ->

                        Text(
                            text = movie.title,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }

                is MovieSearchViewState.Error -> {
                    Box (
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = viewState.message,
                            color = Color.Red,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        }
    }
}
