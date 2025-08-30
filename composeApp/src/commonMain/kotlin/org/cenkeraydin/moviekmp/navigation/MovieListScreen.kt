package org.cenkeraydin.moviekmp.navigation

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import org.cenkeraydin.moviekmp.presentation.MovieScreen

object MovieListScreen : Screen {
    @Composable
    override fun Content() {
        MovieScreen()
    }
}