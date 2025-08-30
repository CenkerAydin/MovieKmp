package org.cenkeraydin.moviekmp.data.repo

import org.cenkeraydin.moviekmp.data.model.Movie
import org.cenkeraydin.moviekmp.data.model.MovieDetailResponse
import org.cenkeraydin.moviekmp.data.model.VideoResponse

interface MovieRepository {
    suspend fun fetchMovies(page: Int): List<Movie>

    suspend fun getMovieDetail(movieId: Int) : MovieDetailResponse

    suspend fun searchMovies(query: String, page: Int ): List<Movie>

    suspend fun getMovieVideos(movieId: Int): VideoResponse

}
