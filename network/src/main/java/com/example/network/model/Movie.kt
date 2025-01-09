package com.example.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.security.InvalidParameterException

/**
 * Movie data.
 *
 * @property id the id of the movie.
 * @property title the title of the movie.
 * @property date the release date of the movie.
 * @property popularity the popularity of the movie.
 * @property overview the overview of the movie.
 * @property posterPath the poster path of the movie.
 * @property backDropPath the backdrop path of the movie.
 */
@Serializable
data class Movie (
    val id: Int = 0,
    val title: String = "Unknown Title",
    @SerialName("release_date") val date: String = "Unknown Date",
    val popularity: Double = 0.0,
    val overview: String = "No Overview Available",
    @SerialName("poster_path") val posterPath: String? = null,
    @SerialName("backdrop_path") val backDropPath: String? = null

) {
    private val baseImageUrl = "https://image.tmdb.org/t/p/"
    private var imageSizeArray = arrayOf(
        "w92",
        "w154",
        "w185",
        "w342",
        "w500",
        "w780",
        "orignal"
    )

    /**
     * @param size the size of image. Restricted to: "w92", "w154", "w185", "w342", "w500", "w780", "orignal"
     * @return the url of poster image.
     */
    fun getPosterUrl(size: String): String {
        if (imageSizeArray.contains(size)){return baseImageUrl + size + posterPath }
        else { throw InvalidParameterException("Invalid size")
        }
    }

    /**
     * @param size the size of image. Restricted to: "w92", "w154", "w185", "w342", "w500", "w780", "orignal"
     * @return the url of backdrop image.
     */
    fun getBackDropUrl(size: String): String {
        if (imageSizeArray.contains(size)){return baseImageUrl + size + backDropPath }
        else { throw InvalidParameterException("Invalid size")
        }
    }

}

