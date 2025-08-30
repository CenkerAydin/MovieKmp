package org.cenkeraydin.moviekmp.data.local

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.JdbcDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import org.cenkeraydin.moviekmp.FavoriteMovieDatabase


actual class DatabaseDriverFactory() {
    actual fun createDriver(): SqlDriver {
        val driver: SqlDriver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        FavoriteMovieDatabase.Schema.create(driver)
        return driver    }
}
