package org.cenkeraydin.moviekmp

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator

import org.cenkeraydin.moviekmp.di.appModule
import org.cenkeraydin.moviekmp.navigation.FavoritesTab
import org.cenkeraydin.moviekmp.navigation.MoviesTab
import org.cenkeraydin.moviekmp.navigation.SettingsTab
import org.cenkeraydin.moviekmp.util.AppTheme
import org.cenkeraydin.moviekmp.util.Language
import org.cenkeraydin.moviekmp.util.LanguageManager
import org.cenkeraydin.moviekmp.util.LocalizationFactory
import org.cenkeraydin.moviekmp.util.ThemeManager
import org.koin.compose.KoinApplication
val LocalThemeManager = staticCompositionLocalOf<ThemeManager> {
    error("No ThemeManager provided")
}
val LocalAppLanguage = staticCompositionLocalOf<LanguageManager> {
    error("No LanguageManager provided")
}
val LocalizedStrings = staticCompositionLocalOf<Language> {
    error("No Language provided")
}
@Composable
fun App() {
    KoinApplication(application = {
        modules(appModule())
    }) {
        val systemDark = isSystemInDarkTheme()
        val defaultTheme = if (systemDark) AppTheme.Dark else AppTheme.Light
        val themeManager = remember { ThemeManager(defaultTheme) }
        val languageManager = remember { LanguageManager() }

        val currentTheme by themeManager.currentTheme.collectAsState()
        val currentLanguage by languageManager.currentLanguage.collectAsState() // ✅ Dili de observe et

        val colors = if (currentTheme == AppTheme.Dark) darkColorScheme() else lightColorScheme()

        CompositionLocalProvider(
            LocalThemeManager provides themeManager,
            LocalAppLanguage provides languageManager,
            LocalizedStrings provides currentLanguage // 🔑 ekledik
        ) {
            // 🌍 dil değişimini global uygula
            LocalizationWrapper(currentLanguage) {
                MaterialTheme(colorScheme = colors) {
                    TabNavigator(MoviesTab) { tabNavigator ->
                        Scaffold(
                            bottomBar = {
                                NavigationBar {
                                    TabNavigationItem(MoviesTab, tabNavigator)
                                    TabNavigationItem(FavoritesTab, tabNavigator)
                                    TabNavigationItem(SettingsTab, tabNavigator)
                                }
                            }
                        ) { padding ->
                            Box(modifier = Modifier.padding(padding)) {
                                CurrentTab()
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LocalizationWrapper(language: Language, content: @Composable () -> Unit) {
    LaunchedEffect(language) {
        LocalizationFactory.create().applyLanguage(language.iso)
    }
    content()
}

@Composable
private fun RowScope.TabNavigationItem(tab: Tab, tabNavigator: TabNavigator) {
    val current = tabNavigator.current

    NavigationBarItem(
        selected = current.key == tab.key,
        onClick = { tabNavigator.current = tab },
        icon = { Icon(tab.options.icon!!, contentDescription = tab.options.title) },
        label = { Text(tab.options.title) }
    )
}


