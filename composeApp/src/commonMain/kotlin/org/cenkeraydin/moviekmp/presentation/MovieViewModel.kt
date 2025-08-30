package org.cenkeraydin.moviekmp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.cenkeraydin.moviekmp.data.model.FavoriteMovie
import org.cenkeraydin.moviekmp.data.model.Movie
import org.cenkeraydin.moviekmp.data.repo.FavoriteMovieRepository
import org.cenkeraydin.moviekmp.data.repo.MovieRepository
import org.cenkeraydin.moviekmp.util.FavoriteEventManager
import kotlin.collections.plus

class MovieViewModel(
    private val repository: MovieRepository,
    val favoriteRepository: FavoriteMovieRepository
) : ViewModel() {

    private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    val movies: StateFlow<List<Movie>> = _movies

    private var currentPage = 1
    private var isLoading = false
    private var searchPage = 1
    private var isSearching = false
    private val _favoriteStates = MutableStateFlow<Map<Int, Boolean>>(emptyMap())
    val favoriteStates: StateFlow<Map<Int, Boolean>> = _favoriteStates

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()


    init {
        loadMovies()
        loadFavoriteStates()
        observeFavoriteEvents()
    }

    private fun observeFavoriteEvents() {
        viewModelScope.launch {
            FavoriteEventManager.favoriteEvents.collect { event ->
                _favoriteStates.value = _favoriteStates.value + (event.movieId to event.isFavorite)
            }
        }
    }


    private fun loadFavoriteStates() {
        viewModelScope.launch {
            val favorites = favoriteRepository.getAllFavorites() // Room/SQLDelight’den gelen favoriler
            _favoriteStates.value = favorites.associate { it.id to true }
        }
    }

    fun toggleFavorite(movie: Movie) {
        viewModelScope.launch(Dispatchers.IO) {
            val exists = favoriteRepository.isFavorite(movie.id)
            if (exists) {
                favoriteRepository.removeFavorite(movie.id)
                FavoriteEventManager.emitFavoriteChanged(movie.id, false)
            } else {
                favoriteRepository.addFavorite(
                    FavoriteMovie(
                        id = movie.id,
                        title = movie.title,
                        posterPath = movie.posterPath,
                        voteAverage = movie.voteAverage,
                        releaseDate = movie.releaseDate
                    )
                )
                FavoriteEventManager.emitFavoriteChanged(movie.id, true)
            }
        }
    }


    fun loadMovies(reset: Boolean = false) {
        if (isLoading) return
        viewModelScope.launch {
            isLoading = true
            if (reset) {
                currentPage = 1
                _movies.value = emptyList()
            }
            val data = repository.fetchMovies(currentPage)
            _movies.value = _movies.value + data // append
            currentPage++
            isLoading = false
        }
    }
    fun searchMovies(query: String, reset: Boolean = true) {
        viewModelScope.launch {
            if (reset) {
                _searchQuery.value = query
                searchPage = 1
                _movies.value = emptyList()
            }

            if (query.isBlank()) {
                loadMovies(reset = true)
                return@launch
            }

            if (isSearching) return@launch
            isSearching = true

            val results = repository.searchMovies(query, searchPage)
            _movies.value = _movies.value + results
            searchPage++

            isSearching = false
        }
    }
}