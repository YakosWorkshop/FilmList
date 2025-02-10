package com.example.filmlist.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.filmlist.components.LoadingState
import com.example.filmlist.components.MovieListItem
import com.example.filmlist.ui.theme.CinemaGold
import com.example.filmlist.ui.theme.TheaterBlack
import com.example.filmlist.viewmodels.MovieDetailsViewModel


@Composable
fun MovieListScreen(
    movieId: Int,
    viewModel: MovieDetailsViewModel = hiltViewModel(),
    onMovieSelected: (Int) -> Unit,
    onAddClicked: (Int) -> Unit
) {
    LaunchedEffect(key1 = Unit, block = {
        viewModel.fetchMovie(movieId)
    })

    val state by viewModel.stateFlow.collectAsState()

    
    LazyColumn (
        modifier = Modifier
            .background(TheaterBlack)
            .fillMaxSize(),
        contentPadding = PaddingValues(all = 16.dp),
        horizontalAlignment =  Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp),

    ) {
        item { Spacer(modifier = Modifier.height(64.dp))}
        when (val viewState = state) {
            MovieDetailsViewState.Loading ->  item{LoadingState()}
            is MovieDetailsViewState.Error -> {
                item {
                    Text(
                        text = viewState.message,
                        color = androidx.compose.ui.graphics.Color.Red,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
            is MovieDetailsViewState.Success -> {
                item {
                    MovieListItem(
                        modifier = Modifier,
                        movie = viewState.movie,
                        onClick = { onMovieSelected(movieId) }
                    )
                }
                item {
                    MovieListItem(
                        modifier = Modifier,
                        movie = viewState.movie,
                        onClick = { onMovieSelected(movieId) }
                    )
                }
            }
        }

        item { ExtendedFloatingActionButton(
            onClick = {onAddClicked(movieId)},
            containerColor = CinemaGold

        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Add",
                tint = Color.White
            )
        }}


    }
}