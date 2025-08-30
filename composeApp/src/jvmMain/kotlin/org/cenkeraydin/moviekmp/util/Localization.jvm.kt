package org.cenkeraydin.moviekmp.util

// jvmMain/Localization.kt
import java.util.*
import java.util.prefs.Preferences

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual object LocalizationFactory {
    actual fun create(): Localization = Localization()
}

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class Localization {
    private val prefs = Preferences.userNodeForPackage(Localization::class.java)

    actual fun applyLanguage(iso: String) {
        try {
            // Locale'i ayarla
            val locale = Locale.forLanguageTag(iso)
            Locale.setDefault(locale)

            // System properties'i güncelle
            System.setProperty("user.language", locale.language)
            System.setProperty("user.country", locale.country)

            // Tercihi kaydet
            prefs.put("selected_language", iso)
            prefs.flush()

            // ResourceBundle cache'ini temizle (immediate update için)
            ResourceBundle.clearCache()

        } catch (e: Exception) {
            println("Error applying language: ${e.message}")
            // Fallback to system default
            Locale.setDefault(Locale.getDefault())
        }
    }

    // Kayıtlı dili yükle (opsiyonel - LanguageManager'da kullanılabilir)
    fun getStoredLanguage(): String? {
        return try {
            val storedLang = prefs.get("selected_language", null)
            storedLang
        } catch (e: Exception) {
            null
        }
    }

    // Uygulama başlatıldığında kayıtlı dili otomatik yükle
    fun initializeStoredLanguage() {
        getStoredLanguage()?.let { storedIso ->
            applyLanguage(storedIso)
        }
    }
}