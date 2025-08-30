package org.cenkeraydin.moviekmp.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.cenkeraydin.moviekmp.components.ModernSearchBar
import org.cenkeraydin.moviekmp.components.MovieCard
import org.cenkeraydin.moviekmp.util.Constants
import org.koin.compose.viewmodel.koinViewModel
@Composable
fun MovieScreen(
    navigator: Navigator = LocalNavigator.currentOrThrow
) {
    val viewModel: MovieViewModel = koinViewModel()
    val movies by viewModel.movies.collectAsState()
    val favoriteStates by viewModel.favoriteStates.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val listState = rememberLazyListState()

    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo }
            .collect { layoutInfo ->
                val totalItems = layoutInfo.totalItemsCount
                val lastVisible = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0

                if (lastVisible >= totalItems - 1 && totalItems > 0) {
                    if (searchQuery.isBlank()) {
                        viewModel.loadMovies(reset = false)
                    } else {
                        viewModel.searchMovies(searchQuery, reset = false)
                    }

                }
            }
    } 

    Column(
        modifier = Modifier.fillMaxSize().padding(top = 16.dp)
    ) {

        ModernSearchBar(
            query = searchQuery,
            placeholder = "Search for a movie...",
            onQueryChange = { viewModel.searchMovies(it) },
            onSearch = { viewModel.searchMovies(it) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            state = listState,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(movies) { movie ->
                val isFavorite = favoriteStates[movie.id] ?: false


                MovieCard(
                    title = movie.title,
                    year = movie.releaseDate,
                    genre = "",
                    imageUrl = "${Constants.MOVIE_URL}${movie.posterPath}",
                    rating = movie.voteAverage,
                    isFavorite = isFavorite,
                    onFavoriteClick = { viewModel.toggleFavorite(movie)},

                    onClick = {
                        println("Movie clicked: ${movie.id}")
                        navigator.push(MovieDetailScreen(movieId = movie.id))
                    },

                )
            }

        }
    }
}
