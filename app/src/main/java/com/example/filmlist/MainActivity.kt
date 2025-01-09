package com.example.filmlist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.filmlist.ui.theme.FilmListTheme
import com.example.network.KtorClient
import com.example.network.model.Movie
import com.example.network.TestFile

class MainActivity : ComponentActivity() {
    private val ktorClient = KtorClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var movie by remember {
                mutableStateOf<Movie?>(null)
            }
            var movieList by remember {
                mutableStateOf<List<Movie>?>(null)
            }
            LaunchedEffect(key1=Unit, block={
                movie = ktorClient.getMovieById(68540)
                movieList = ktorClient.getMovieByTitle("Harry Potter")
            })

            FilmListTheme {
                Scaffold(modifier=Modifier.fillMaxSize()) { innerPadding ->
                    Column {
                        Greeting(
                            name = "Android",
                            modifier = Modifier.padding(innerPadding)
                        )
                        Text(text=movie?.getPosterUrl("w92")?: "No Image Url")
                        TestFile()
                        //Text(text=movie?.title ?: "No title")
                        //Text(text=movie?.date ?: "No date")
                        //Text(text=movie?.overview ?: "No overview")
                        //Text(text=movie?.popularity.toString() ?: "No popularity")



                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FilmListTheme {
        Greeting("Android")
    }
}