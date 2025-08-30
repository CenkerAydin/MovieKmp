package org.cenkeraydin.moviekmp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.cenkeraydin.moviekmp.data.model.FavoriteMovie
import org.cenkeraydin.moviekmp.data.repo.FavoriteMovieRepository
import org.cenkeraydin.moviekmp.util.FavoriteEventManager

class FavoriteViewModel(private val repository: FavoriteMovieRepository) : ViewModel() {

    private val _favorites = MutableStateFlow<List<FavoriteMovie>>(emptyList())
    val favorites: StateFlow<List<FavoriteMovie>> = _favorites

    fun loadFavorites() {
        viewModelScope.launch(Dispatchers.IO) {
            val list = repository.getAllFavorites()
            _favorites.value = list
        }
    }

    fun addFavorite(movie: FavoriteMovie) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addFavorite(movie)
            loadFavorites()
        }
    }

    fun removeFavorite(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.removeFavorite(id)
            loadFavorites()
            FavoriteEventManager.emitFavoriteChanged(id, false) // Event gönder
        }
    }

}
