package org.cenkeraydin.moviekmp.data.local

import app.cash.sqldelight.db.SqlDriver
import org.cenkeraydin.moviekmp.FavoriteMovieDatabase
import org.cenkeraydin.moviekmp.data.model.FavoriteMovie

expect class DatabaseDriverFactory {
    fun createDriver(): SqlDriver
}

class LocalDatabase(
    databaseDriverFactory: DatabaseDriverFactory
) {
    private val database = FavoriteMovieDatabase(
        databaseDriverFactory.createDriver()
    )
    private val query = database.favoriteMovieDatabaseQueries

    fun getAllFavorites(): List<FavoriteMovie> {
        return query.getAllFavoriteMovies().executeAsList().map {
            FavoriteMovie(
                id = it.id.toInt(),
                title = it.title,
                posterPath = it.posterPath,
                releaseDate = it.releaseDate,
                voteAverage = it.voteAverage,
            )
        }
    }

    fun getFavoriteById(id: Int): FavoriteMovie? {
        return query.getFavoriteMovieById(id.toLong())
            .executeAsOneOrNull()
            ?.let {
                FavoriteMovie(
                    id = it.id.toInt(),
                    title = it.title,
                    posterPath = it.posterPath,
                    releaseDate = it.releaseDate,
                    voteAverage = it.voteAverage,
                )
            }
    }

    fun insertFavorite(favorite: FavoriteMovie) {
        query.insertFavoriteMovie(
            id = favorite.id.toLong(),
            title = favorite.title,
            posterPath = favorite.posterPath,
            releaseDate = favorite.releaseDate,
            voteAverage = favorite.voteAverage
        )
    }

    fun deleteFavorite(id: Int) {
        query.deleteFavoriteMovie(id.toLong())
    }

    fun isFavorite(id: Int): Boolean {
        return getFavoriteById(id) != null
    }
}
