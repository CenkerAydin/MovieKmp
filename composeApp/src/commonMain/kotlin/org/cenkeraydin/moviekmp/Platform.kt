package org.cenkeraydin.moviekmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform