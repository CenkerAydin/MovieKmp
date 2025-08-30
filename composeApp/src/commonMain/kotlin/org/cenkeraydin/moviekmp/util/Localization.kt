package org.cenkeraydin.moviekmp.util

expect object LocalizationFactory {
    fun create(): Localization
}

expect class Localization {
    fun applyLanguage(iso: String)
}
