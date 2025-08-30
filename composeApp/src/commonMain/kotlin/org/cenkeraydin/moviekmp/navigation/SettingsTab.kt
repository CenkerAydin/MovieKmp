package org.cenkeraydin.moviekmp.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import moviekmp.composeapp.generated.resources.Res
import moviekmp.composeapp.generated.resources.settings
import org.cenkeraydin.moviekmp.presentation.SettingsScreen
import org.jetbrains.compose.resources.stringResource


object SettingsTab : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val title = stringResource(Res.string.settings)
            val icon = rememberVectorPainter(Icons.Default.Settings)
            return TabOptions(
                index = 0u,
                title = title,
                icon = icon
            )
        }

    @Composable
    override fun Content() {
        SettingsScreen()
    }
}