package com.example.filmlist.screens

import android.graphics.drawable.Icon
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil3.compose.AsyncImage
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.filmlist.components.DataPoint
import com.example.filmlist.components.DataPointComponent
import com.example.filmlist.components.LoadingState
import com.example.filmlist.components.TitleComponent
import com.example.filmlist.components.TitleDataPointComponent
import com.example.filmlist.ui.theme.SlateGray
import com.example.filmlist.ui.theme.TheaterBlack
import com.example.filmlist.viewmodels.MovieDetailsViewModel
import com.example.network.ApiOperation
import com.example.network.KtorClient
import com.example.network.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface MovieDetailsViewState {
    object Loading:MovieDetailsViewState
    data class Error(val message: String): MovieDetailsViewState
    data class Success(
        val movie: Movie,
        val movieDataPoints: List<DataPoint>
    ): MovieDetailsViewState
}

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailsScreen(
    movieId: Int,
    viewModel: MovieDetailsViewModel = hiltViewModel(),
    onReturnClicked: (Int) -> Unit
) {
    LaunchedEffect(key1 = Unit, block = {
        viewModel.fetchMovie(movieId)
    })

    val state by viewModel.stateFlow.collectAsState()

    Column(
        modifier = Modifier
            .background(TheaterBlack)
            .fillMaxSize()

    ) {
        TopAppBar(
            colors = TopAppBarColors(
                containerColor = TheaterBlack,
                navigationIconContentColor = Color.White,
                titleContentColor = Color.White,
                scrolledContainerColor = Color.White,
                actionIconContentColor = Color.White,
            ),
            title = { Text("Detail") },
            navigationIcon = {
                IconButton(onClick = { onReturnClicked(movieId)}) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Return"
                    )
                }
            }
        )

        when (val viewState = state) {
            MovieDetailsViewState.Loading -> LoadingState()
            is MovieDetailsViewState.Error -> {
                TODO()
            }
            is MovieDetailsViewState.Success -> {
                viewState.movie.let { TitleComponent(it.title, it.getBackDropUrl("original")) }

                Spacer(modifier = Modifier.height(10.dp))

                viewState.movie.let {TitleDataPointComponent(it.date, it.runtime)}

                LazyColumn (
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(all = 10.dp)
                ) {

                    items(viewState.movieDataPoints) {
                        Spacer(modifier = Modifier.height(24.dp))
                        DataPointComponent(dataPoint = it)
                    }
                }
            }
        }
    }
}


