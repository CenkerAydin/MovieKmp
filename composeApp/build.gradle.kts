import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
    alias(libs.plugins.kotlinxSerialization)
    alias(libs.plugins.sqlDelight)
    id("com.github.gmazzo.buildconfig") version "5.6.7"

}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
            linkerOpts("-lsqlite3")

        }
    }
    
    jvm()



    sourceSets {
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)

            // Koin Android
            implementation(libs.koin.core)
            implementation(libs.koin.androidx.compose)
            implementation(libs.koin.compose.viewModel)

            implementation(libs.ktor.client.android)
            implementation(libs.sqldelight.android)

        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)

            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)

            implementation(libs.koin.core)

            implementation(libs.bundles.ktor)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.kotlin.serialization)
            implementation(libs.koin.compose.viewModel)
            // Koin Compose
            implementation(libs.koin.compose)
            implementation(libs.kamel.image.default)

            implementation(libs.coil.compose)
            implementation(libs.coil.network.ktor)

            implementation(libs.material.icons.extended)
            implementation(libs.voyager.navigator)
            implementation(libs.voyager.tab.navigator)
            implementation(libs.voyager.koin)
            implementation(libs.sqldelight.coroutines)


        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutinesSwing)
            implementation(libs.ktor.client.cio)
            implementation(libs.koin.core)
            implementation(libs.ktor.client.java)
            implementation(libs.sqldelight.jvm)


        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin) // ← Darwin engine
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.kotlin.serialization)
            implementation(libs.sqldelight.ios)

        }
    }

}
buildConfig {
    packageName("org.cenkeraydin.moviekmp") // commonMain içinde erişeceğin package
    buildConfigField(
        "STRING",
        "TMDB_API_KEY",
        "\"${project.findProperty("TMDB_API_KEY") ?: ""}\""
    )
}

android {
    namespace = "org.cenkeraydin.moviekmp"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "org.cenkeraydin.moviekmp"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}

compose.desktop {
    application {
        mainClass = "org.cenkeraydin.moviekmp.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "org.cenkeraydin.moviekmp"
            packageVersion = "1.0.0"
        }
    }
}

sqldelight {
    databases {
        create("FavoriteMovieDatabase") {
            packageName.set("org.cenkeraydin.moviekmp")
        }
    }
}