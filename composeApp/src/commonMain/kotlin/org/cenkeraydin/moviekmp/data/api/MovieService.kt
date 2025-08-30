package org.cenkeraydin.moviekmp.data.api

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import org.cenkeraydin.moviekmp.data.model.Movie
import org.cenkeraydin.moviekmp.data.model.MovieDetailResponse
import org.cenkeraydin.moviekmp.data.model.MovieResponse
import org.cenkeraydin.moviekmp.data.model.VideoResponse
import org.cenkeraydin.moviekmp.util.Constants
import org.cenkeraydin.moviekmp.util.Constants.API_KEY
import org.cenkeraydin.moviekmp.util.Constants.BASE_URL
import org.cenkeraydin.moviekmp.util.LanguageManager

class MovieApi(
    private val client: HttpClient,
    private val languageManager: LanguageManager
) {
    private fun currentLanguageIso(): String {
        return languageManager.getCurrentLanguage().iso
    }

    suspend fun getDiscoverMovies(page: Int): List<Movie> {
        val response: HttpResponse = client.get("${BASE_URL}discover/movie") {
            parameter("api_key", API_KEY)
            parameter("language", currentLanguageIso()) // ✅ seçili dil
            parameter("page", page)
        }
        return response.body<MovieResponse>().results
    }

    suspend fun getMovieDetail(movieId: Int): MovieDetailResponse {
        val response: HttpResponse = client.get("${BASE_URL}movie/$movieId") {
            parameter("api_key", API_KEY)
            parameter("language", currentLanguageIso()) // ✅
        }
        return response.body()
    }

    suspend fun searchMovies(query: String, page: Int): List<Movie> {
        val response: HttpResponse = client.get("${BASE_URL}search/movie") {
            parameter("api_key", API_KEY)
            parameter("language", currentLanguageIso()) // ✅
            parameter("query", query)
            parameter("page", page)
        }
        return response.body<MovieResponse>().results
    }

    suspend fun getMovieVideos(movieId: Int): VideoResponse {
        val response: HttpResponse = client.get("${BASE_URL}movie/$movieId/videos") {
            parameter("api_key", API_KEY)
            parameter("language", currentLanguageIso()) // ✅
        }
        return response.body()
    }
}
