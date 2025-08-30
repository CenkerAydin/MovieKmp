package org.cenkeraydin.moviekmp.util

// iosMain/src/iosMain/kotlin/com/example/yourapp/utils/PlatformOpener.kt

import platform.UIKit.UIApplication
import platform.Foundation.NSURL

actual fun openYouTubeVideo(videoId: String) {
    val appUrl = NSURL(string = "youtube://watch?v=$videoId")
    val webUrl = NSURL(string = "https://www.youtube.com/watch?v=$videoId")

    val sharedApplication = UIApplication.sharedApplication()

    // Use the new open method instead of deprecated openURL
    if (sharedApplication.canOpenURL(appUrl)) {
        sharedApplication.openURL(appUrl, options = emptyMap<Any?, Any?>(), completionHandler = null)
    } else {
        sharedApplication.openURL(webUrl, options = emptyMap<Any?, Any?>(), completionHandler = null)
    }
}