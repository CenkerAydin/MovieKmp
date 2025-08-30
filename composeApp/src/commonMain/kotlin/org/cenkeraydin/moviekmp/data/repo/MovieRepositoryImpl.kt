package org.cenkeraydin.moviekmp.data.repo

import org.cenkeraydin.moviekmp.data.api.MovieApi
import org.cenkeraydin.moviekmp.data.model.Movie
import org.cenkeraydin.moviekmp.data.model.MovieDetailResponse
import org.cenkeraydin.moviekmp.data.model.VideoResponse

class MovieRepositoryImpl(private val movieApi: MovieApi) : MovieRepository {
    override suspend fun fetchMovies(page: Int): List<Movie> {
        return movieApi.getDiscoverMovies(page)
    }

    override suspend fun getMovieDetail(movieId: Int): MovieDetailResponse {
        return movieApi.getMovieDetail(movieId)
    }

    override suspend fun searchMovies(query: String, page: Int): List<Movie> {
        return movieApi.searchMovies(query, page)
    }

    override suspend fun getMovieVideos(movieId: Int): VideoResponse {
        return movieApi.getMovieVideos(movieId)
    }


}
