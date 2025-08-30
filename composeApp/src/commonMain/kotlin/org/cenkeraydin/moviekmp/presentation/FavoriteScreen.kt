package org.cenkeraydin.moviekmp.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.cenkeraydin.moviekmp.components.MovieCard
import org.cenkeraydin.moviekmp.util.Constants
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun FavoriteScreen(
    navigator: Navigator = LocalNavigator.currentOrThrow
) {
    val viewModel: FavoriteViewModel = koinViewModel()
    val favorites by viewModel.favorites.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadFavorites()
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(top = 16.dp)
    ) {
        Text(
            text = "Favorite Movies",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(horizontal = 16.dp),
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(8.dp))

        if (favorites.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No favorite movies yet",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Gray
                )
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(favorites) { favorite ->
                    MovieCard(
                        title = favorite.title,
                        year = favorite.releaseDate,
                        genre = "",
                        imageUrl = "${Constants.MOVIE_URL}${favorite.posterPath}",
                        rating = favorite.voteAverage,
                        isFavorite = true,
                        onFavoriteClick = {
                            viewModel.removeFavorite(favorite.id)
                        },
                    )                }
            }
        }
    }
}