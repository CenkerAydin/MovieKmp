package org.cenkeraydin.moviekmp.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieResponse(
    val results: List<Movie>
)

@Serializable
data class Movie(
    val id: Int,
    val title: String,
    @SerialName("genre_ids")val genreIds: List<Int>,
    @SerialName("vote_average") val voteAverage: Double,
    @SerialName("release_date") val releaseDate: String,
    @SerialName("poster_path") val posterPath: String?
)