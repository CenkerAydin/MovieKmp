package org.cenkeraydin.moviekmp.di

import io.ktor.client.HttpClient
import org.cenkeraydin.moviekmp.data.api.MovieApi
import org.cenkeraydin.moviekmp.data.local.DatabaseDriverFactory
import org.cenkeraydin.moviekmp.data.local.LocalDatabase
import org.cenkeraydin.moviekmp.data.repo.FavoriteMovieRepository
import org.cenkeraydin.moviekmp.data.repo.MovieRepository
import org.cenkeraydin.moviekmp.data.repo.MovieRepositoryImpl
import org.cenkeraydin.moviekmp.presentation.FavoriteViewModel
import org.cenkeraydin.moviekmp.presentation.MovieDetailViewModel
import org.cenkeraydin.moviekmp.presentation.MovieViewModel
import org.cenkeraydin.moviekmp.util.LanguageManager
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module


expect fun provideHttpClient(): HttpClient

expect fun provideDatabaseDriverFactory(): DatabaseDriverFactory

val provideDatabaseModule = module {
    single { provideDatabaseDriverFactory() }       // DatabaseDriverFactory
    single { LocalDatabase(get()) }                 // LocalDatabase
    single<FavoriteMovieRepository> { FavoriteMovieRepository(get()) } // Interface belirtilmişse
}

val provideHttpClientModule = module {
    single { provideHttpClient() }
}

val provideMovieApiModule = module {
    single { MovieApi(get(),get()) }
}

val languageManager = module {
    single { LanguageManager() }
}

val provideMovieRepositoryModule = module {
    single<MovieRepository> { MovieRepositoryImpl(get()) }
}

val provideMovieViewModelModule = module {
    viewModel {
        MovieViewModel(
            repository = get<MovieRepository>(),
            favoriteRepository = inject<FavoriteMovieRepository>().value
        )
    }
}

val provideMovieDetailViewModule = module {
    viewModel {
        MovieDetailViewModel(get(),get())
    }
}
val provideFavoriteViewModelModule = module {
    viewModel { FavoriteViewModel(get()) }
}


fun appModule() = listOf(
    provideHttpClientModule,
    provideDatabaseModule,
    provideMovieApiModule,
    provideMovieRepositoryModule,
    provideMovieViewModelModule,
    provideMovieDetailViewModule,
    provideFavoriteViewModelModule,
    languageManager
)