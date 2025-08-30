package org.cenkeraydin.moviekmp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.cenkeraydin.moviekmp.data.model.FavoriteMovie
import org.cenkeraydin.moviekmp.data.model.MovieDetailResponse
import org.cenkeraydin.moviekmp.data.repo.FavoriteMovieRepository
import org.cenkeraydin.moviekmp.data.repo.MovieRepository
import org.cenkeraydin.moviekmp.util.FavoriteEventManager
import org.cenkeraydin.moviekmp.util.UIState

class MovieDetailViewModel(
    private val repository: MovieRepository,
    private val favoriteRepository: FavoriteMovieRepository
) : ViewModel() {

    private val _movieDetail = MutableStateFlow<UIState<MovieDetailResponse>>(UIState.Loading)
    val movieDetail: StateFlow<UIState<MovieDetailResponse>> = _movieDetail

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite

    private val _trailerKey = MutableStateFlow<String?>(null)
    val trailerKey: StateFlow<String?> = _trailerKey

    fun fetchMovieDetail(movieId: Int) {
        viewModelScope.launch {
            _movieDetail.value = UIState.Loading
            try {
                val movieDetailResult = repository.getMovieDetail(movieId)
                _movieDetail.value = UIState.Success(movieDetailResult)
                _isFavorite.value = favoriteRepository.isFavorite(movieId)

                val videosResponse = repository.getMovieVideos(movieId)
                val trailer = videosResponse.results.firstOrNull { it.site == "YouTube" && it.type == "Trailer" }
                _trailerKey.value = trailer?.key
            } catch (e: Exception) {
                _movieDetail.value = UIState.Error(e.message)
            }
        }
    }

    fun toggleFavorite(movieDetail: MovieDetailResponse) {
        viewModelScope.launch(Dispatchers.IO) {
            val exists = favoriteRepository.isFavorite(movieDetail.id)
            if (exists) {
                favoriteRepository.removeFavorite(movieDetail.id)
                _isFavorite.value = false
                FavoriteEventManager.emitFavoriteChanged(movieDetail.id, false)
            } else {
                favoriteRepository.addFavorite(
                    FavoriteMovie(
                        id = movieDetail.id,
                        title = movieDetail.title,
                        posterPath = movieDetail.posterPath,
                        voteAverage = movieDetail.voteAverage,
                        releaseDate = movieDetail.releaseDate
                    )
                )
                _isFavorite.value = true
                FavoriteEventManager.emitFavoriteChanged(movieDetail.id, true)
            }
        }
    }

    fun clearData() {
        _movieDetail.value = UIState.Loading
        _isFavorite.value = false
    }
}
