package com.example.filmlist.model

import androidx.annotation.DrawableRes

data class Movie(
    val title: String,
    val year: Int,
    val rating: String,
    val description: String,
    val seen: Boolean,
    @DrawableRes val imageId: Int
)


