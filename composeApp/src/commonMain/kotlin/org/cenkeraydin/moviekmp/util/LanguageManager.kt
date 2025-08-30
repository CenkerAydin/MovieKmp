package org.cenkeraydin.moviekmp.util

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class LanguageManager {
    private val _currentLanguage = MutableStateFlow(Language.English)
    val currentLanguage: StateFlow<Language> = _currentLanguage.asStateFlow()

    private val localization = LocalizationFactory.create()

    fun changeLanguage(language: Language) {
        _currentLanguage.value = language
        localization.applyLanguage(language.iso)
        // Burada SharedPreferences veya DataStore ile kaydetmeniz önerilir
    }

    fun getCurrentLanguage(): Language = _currentLanguage.value
}