package org.cenkeraydin.moviekmp.data.local

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import org.cenkeraydin.moviekmp.FavoriteMovieDatabase

actual class DatabaseDriverFactory(private val context: Context) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(FavoriteMovieDatabase.Schema, context, "movie_favorite.db")
    }
}