package com.example.filmlist.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.bumptech.glide.integration.ktx.Placeholder
import com.example.filmlist.R
import com.example.filmlist.ui.theme.SoftSilver
import com.example.filmlist.ui.theme.TheaterBlack

data class DataPoint(
    val title: String,
    val description: String
)

@Composable
fun DataPointComponent(dataPoint: DataPoint) {
    Column {
        Text(
            text = dataPoint.title,
            fontSize = 23.sp,
            fontWeight = FontWeight.Bold,
            color = SoftSilver
        )
        Text(
            text = dataPoint.description ,
            fontSize = 20.sp,
            color = Color.White
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun TitleComponent(title: String, url: String) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.BottomCenter
    ) {
        GlideImage(
            model = url,
            contentDescription = "Backdrop",
            modifier = Modifier.fillMaxWidth(),
            loading = placeholder(R.drawable.placeholder_backdrop)
        )
        Box(
            modifier = Modifier
                .requiredHeight(200.dp)
                .fillMaxWidth()
                .graphicsLayer { alpha = 1f } // Adjust transparency
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            TheaterBlack // Bottom gradient color
                        )
                    )
                )
        )
        Text(
            text = title,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
            color = SoftSilver,


        )
    }
}

@Composable
fun TitleDataPointComponent(date: String, runtime: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = date,
            color = Color.White,
            fontSize = 20.sp


        )
        Spacer(modifier = Modifier.width(32.dp))
        Text(
            text = "$runtime mins",
            color = Color.White,
            fontSize = 20.sp

        )

    }
}

@Preview
@Composable
fun TitleComponentPreview() {
    val title: String = "Coffee and Cigarettes"
    TitleComponent(title, "https://image.tmdb.org/t/p/original/ndGeSVCT1yLl1Mbcvx0yxJPcx6p.jpg")
}

@Preview
@Composable
fun TitleDataPointComponentPreview() {
    TitleDataPointComponent("2004-03-12",97)
}

@Preview
@Composable
fun DataPointComponentPreview() {
    val dataPoint = DataPoint("Title","Halo")
    DataPointComponent(dataPoint)
}