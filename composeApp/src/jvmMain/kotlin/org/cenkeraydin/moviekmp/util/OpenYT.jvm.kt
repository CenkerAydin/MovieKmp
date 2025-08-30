package org.cenkeraydin.moviekmp.util

// desktopMain/src/desktopMain/kotlin/com/example/yourapp/utils/PlatformOpener.kt

import java.awt.Desktop
import java.net.URI

actual fun openYouTubeVideo(videoId: String) {
    val url = "https://www.youtube.com/watch?v=$videoId"

    val desktop = if (Desktop.isDesktopSupported()) Desktop.getDesktop() else null
    if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
        try {
            val uri = URI(url)
            desktop.browse(uri)
        } catch (e: Exception) {
            println("YouTube videosu açılamadı: ${e.message}")
        }
    } else {
        println("Web tarayıcısı desteklenmiyor.")
    }
}