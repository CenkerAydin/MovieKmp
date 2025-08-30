package org.cenkeraydin.moviekmp.util

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object FavoriteEventManager {
    private val _favoriteEvents = MutableSharedFlow<FavoriteEvent>()
    val favoriteEvents: SharedFlow<FavoriteEvent> = _favoriteEvents.asSharedFlow()

    suspend fun emitFavoriteChanged(movieId: Int, isFavorite: Boolean) {
        _favoriteEvents.emit(FavoriteEvent(movieId, isFavorite))
    }
}

data class FavoriteEvent(val movieId: Int, val isFavorite: Boolean)