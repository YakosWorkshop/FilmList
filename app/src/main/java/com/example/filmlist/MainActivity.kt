package com.example.filmlist

import android.os.Bundle
import android.provider.ContactsContract
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.filmlist.components.MovieListItem
import com.example.filmlist.screens.MovieDetailsScreen
import com.example.filmlist.screens.MovieListScreen
import com.example.filmlist.screens.MovieSearchScreen
import com.example.filmlist.ui.theme.FilmListTheme
import com.example.network.KtorClient
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var ktorClient: KtorClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val navController = rememberNavController()

            FilmListTheme {
                Scaffold(modifier=Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(navController = navController, startDestination = "start_screen") {
                        composable(
                            route = "movie_details/{movieId}",
                            arguments = listOf(navArgument("movieId") {type = NavType.IntType})
                        )
                        {   backStackEntry ->
                            val movieId: Int = backStackEntry.arguments?.getInt("movieId") ?: -1
                            MovieDetailsScreen(
                                movieId = movieId
                            ) {
                                navController.navigate("list_screen/$it")
                            }
                        }
                        composable(
                            route = "list_screen/{movieId}",
                            arguments = listOf(navArgument("movieId") {type = NavType.IntType})
                        ) { backStackEntry ->
                            val movieId: Int = backStackEntry.arguments?.getInt("movieId") ?: -1
                            MovieListScreen(
                                movieId = movieId,
                                onAddClicked = { navController.navigate(route = "search_screen") },
                                onMovieSelected = { navController.navigate(route = "movie_details/$it") },
                            )
                        }
                        composable(
                            route = "search_screen") {
                            MovieSearchScreen()

                        }
                        composable(
                            route = "start_screen") {
                            ReturnTestScreen(movieId = 939243)
                            {
                                navController.navigate("list_screen/$it")
                            }
                        }

                    }

                }
            }
        }
    }
}

@Composable
fun ReturnTestScreen(movieId: Int, onNavigateToList: (Int) -> Unit) {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {

        Text(
            text = "Movie Title: $movieId",
            fontSize = 28.sp
        )
        Button(
            shape = ButtonDefaults.shape,
            onClick = { onNavigateToList(movieId) }
        ) { }

    }
}
