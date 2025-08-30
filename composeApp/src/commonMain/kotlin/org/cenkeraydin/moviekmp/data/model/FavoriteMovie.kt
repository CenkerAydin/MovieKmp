package org.cenkeraydin.moviekmp.data.model

data class FavoriteMovie(
    val id: Int,
    val title: String,
    val posterPath: String?,
    val releaseDate: String,
    val voteAverage: Double
)