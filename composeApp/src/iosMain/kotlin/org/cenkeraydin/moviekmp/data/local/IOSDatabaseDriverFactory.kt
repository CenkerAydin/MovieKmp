package org.cenkeraydin.moviekmp.data.local

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import org.cenkeraydin.moviekmp.FavoriteMovieDatabase

actual class DatabaseDriverFactory() {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(
            FavoriteMovieDatabase.Schema,
            "movie_favorite.db"
        )
    }
}