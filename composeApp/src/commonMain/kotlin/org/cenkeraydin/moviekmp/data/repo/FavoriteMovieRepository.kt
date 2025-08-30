package org.cenkeraydin.moviekmp.data.repo

import org.cenkeraydin.moviekmp.data.local.LocalDatabase
import org.cenkeraydin.moviekmp.data.model.FavoriteMovie

class FavoriteMovieRepository(private val localDatabase: LocalDatabase) {

    // Tüm favorileri al
    fun getAllFavorites(): List<FavoriteMovie> {
        return localDatabase.getAllFavorites()
    }

    // Tek favoriyi al
    fun getFavoriteById(id: Int): FavoriteMovie? {
        return localDatabase.getFavoriteById(id)
    }

    // Favori ekle
    fun addFavorite(movie: FavoriteMovie) {
        localDatabase.insertFavorite(movie)
    }

    // Favori sil
    fun removeFavorite(id: Int) {
        localDatabase.deleteFavorite(id)
    }

    fun isFavorite(id: Int): Boolean {
        return localDatabase.getFavoriteById(id) != null
    }
}
