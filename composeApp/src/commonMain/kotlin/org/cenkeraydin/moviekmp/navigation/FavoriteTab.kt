package org.cenkeraydin.moviekmp.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import moviekmp.composeapp.generated.resources.Res
import moviekmp.composeapp.generated.resources.favorites
import org.cenkeraydin.moviekmp.presentation.FavoriteScreen
import org.jetbrains.compose.resources.stringResource

object FavoritesTab : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val title = stringResource(Res.string.favorites)
            val icon = rememberVectorPainter(Icons.Default.Favorite)
            return TabOptions(
                index = 1u,
                title = title,
                icon = icon
            )
        }

    @Composable
    override fun Content() {
        FavoriteScreen()
    }
}