package org.cenkeraydin.moviekmp.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailResponse(
    val id: Int,
    val title: String,
    val overview: String,
    @SerialName("poster_path")val posterPath: String,
    @SerialName("backdrop_path") val backdropPath: String,
    @SerialName("release_date" )val releaseDate: String,
    @SerialName("runtime")val runTime: Int,
    @SerialName("vote_average")val voteAverage: Double,
)