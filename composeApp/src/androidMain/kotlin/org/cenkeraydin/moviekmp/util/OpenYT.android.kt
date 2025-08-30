package org.cenkeraydin.moviekmp.util

// androidMain/src/androidMain/kotlin/com/example/yourapp/utils/PlatformOpener.kt

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat
import io.kamel.core.applicationContext
import androidx.core.net.toUri
import org.cenkeraydin.moviekmp.appContext

// androidMain/src/androidMain/kotlin/com/example/yourapp/utils/PlatformOpener.kt

actual fun openYouTubeVideo(videoId: String) {
    val androidContext =appContext
    val appIntent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:$videoId"))
    appIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) // ✅ Bu bayrak eklendi

    val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=$videoId"))
    webIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) // ✅ Bu bayrak eklendi

    try {
        ContextCompat.startActivity(androidContext, appIntent, null)
    } catch (ex: Exception) {
        ContextCompat.startActivity(androidContext, webIntent, null)
    }
}