package com.example.filmlist.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.Placeholder
import com.example.filmlist.ui.theme.PopcornYellow


private val defaultModifier = Modifier
    .fillMaxSize()
    .padding(all = 128.dp)




@Composable
fun LoadingState(modifier: Modifier = defaultModifier){
    CircularProgressIndicator(
        modifier = modifier,
        color = PopcornYellow
    )
}

