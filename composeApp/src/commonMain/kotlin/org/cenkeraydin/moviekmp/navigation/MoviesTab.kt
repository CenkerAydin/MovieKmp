package org.cenkeraydin.moviekmp.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Movie
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.input.key.Key.Companion.T
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import moviekmp.composeapp.generated.resources.Res
import moviekmp.composeapp.generated.resources.movies
import org.jetbrains.compose.resources.stringResource

object MoviesTab : Tab {
    override val options: TabOptions
        @Composable
        get() = TabOptions(
            index = 0u,
            title = stringResource(Res.string.movies),
            icon = rememberVectorPainter(Icons.Default.Movie)
        )

    @Composable
    override fun Content() {
        Navigator(MovieListScreen) // movieId parametresi kaldır
    }
}