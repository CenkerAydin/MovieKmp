package org.cenkeraydin.moviekmp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class VideoResponse(
    val id: Int,
    val results: List<Video>
)

@Serializable
data class Video(
    val id: String,
    val iso_639_1: String,
    val iso_3166_1: String,
    val key: String,
    val name: String,
    val site: String,
    val size: Int,
    val type: String,
    val official: Boolean,
    val published_at: String
)