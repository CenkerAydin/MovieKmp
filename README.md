# 🎬 CineScope (KMP Edition)

CineScope is a **Kotlin Multiplatform (KMP)** application for discovering movies and exploring detailed information from TMDB.  
The project runs seamlessly on **Android, iOS, and Desktop**, sharing business logic across platforms while keeping native UI experiences.

---

## ✨ Features
- 🔍 Search for movies with detailed filters
- 📄 View movie details (overview, release date, rating, etc.)
- ⭐ Mark favorites for quick access
- 📱 Works across **Android, iOS, and Desktop**
- 🚀 Built with **Kotlin Multiplatform** for shared logic

---

## 🛠️ Tech Stack
- **Kotlin Multiplatform** (shared codebase)
- **Jetpack Compose Multiplatform** (UI for Android & Desktop)
- **SwiftUI** (UI for iOS)
- **Ktor Client** (networking)
- **SQLDelight** (local database)
- **Coroutines & Flow** (async & reactive programming)
- **MVVM + Clean Architecture** (scalable architecture)

---

## 🔑 TMDB API Key

This project uses the [TMDB API](https://www.themoviedb.org/documentation/api) for fetching movie data.  
To run the project, you need to add your TMDB API key in a `local.properties` file:

```properties
TMDB_API_KEY=your_api_key_here
