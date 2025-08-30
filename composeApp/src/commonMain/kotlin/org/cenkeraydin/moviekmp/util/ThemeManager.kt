package org.cenkeraydin.moviekmp.util

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ThemeManager(initialTheme: AppTheme) {
    private val _currentTheme = MutableStateFlow(initialTheme)
    val currentTheme: StateFlow<AppTheme> = _currentTheme.asStateFlow()

    fun toggleTheme() {
        _currentTheme.value = if (_currentTheme.value == AppTheme.Light) AppTheme.Dark else AppTheme.Light
    }
}