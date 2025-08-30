package org.cenkeraydin.moviekmp.util

import platform.Foundation.NSUserDefaults

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual object LocalizationFactory {
    actual fun create(): Localization = Localization()
}

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class Localization {
    actual fun applyLanguage(iso: String) {
        NSUserDefaults.standardUserDefaults.setObject(
            listOf(iso), "AppleLanguages"
        )
        // iOS'ta uygulamayı yeniden başlatmak gerekebilir
    }
}