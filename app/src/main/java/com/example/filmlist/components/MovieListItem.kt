package com.example.filmlist.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.example.filmlist.R
import com.example.filmlist.ui.theme.PopcornYellow
import com.example.filmlist.ui.theme.SoftSilver
import com.example.network.model.Movie

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MovieListItem(
    modifier: Modifier,
    movie: Movie,
    onClick: () -> Unit
    ) {
    Row (
        modifier = modifier
            .border(
                width = 1.dp,
                brush = Brush.horizontalGradient(listOf(Color.Transparent, PopcornYellow)),
                shape = RoundedCornerShape(12.dp)
            )
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ){
        GlideImage(
            model = movie.getPosterUrl(size = "original"),
            modifier = modifier
                .requiredHeight(100.dp),
            contentDescription = movie.title,
            loading = placeholder(R.drawable.placeholder_poster)
        )
        Text(
            text = movie.title,
            color = SoftSilver,
            fontWeight = FontWeight.Bold,
            fontSize = 26.sp,
            lineHeight = 26.sp,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp)
        )
    }
}

@Preview
@Composable
fun MovieListItemPreview() {
    val sonic = Movie()
    sonic.title = "Sonic The Hedgehog 3"
    MovieListItem(
        modifier = Modifier,
        movie = sonic
    ) { }
}
