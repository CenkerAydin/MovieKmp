package org.cenkeraydin.moviekmp.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key.Companion.R
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.cenkeraydin.moviekmp.components.MovieDetailCard
import org.cenkeraydin.moviekmp.data.model.MovieDetailResponse
import org.cenkeraydin.moviekmp.util.UIState
import org.cenkeraydin.moviekmp.util.openYouTubeVideo
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
data class MovieDetailScreen(val movieId: Int) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel: MovieDetailViewModel = koinViewModel()
        val movieDetailState by viewModel.movieDetail.collectAsState()
        val isFavorite by viewModel.isFavorite.collectAsState() // ✅ doğru state
        val trailerKey by viewModel.trailerKey.collectAsState()
        LaunchedEffect(movieId) {
            viewModel.clearData()
            viewModel.fetchMovieDetail(movieId)
        }

        when (movieDetailState) {
            is UIState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is UIState.Success -> {
                val movieDetail = (movieDetailState as UIState.Success<MovieDetailResponse>).data

                MovieDetailCard(
                    movieTitle = movieDetail.title,
                    year = movieDetail.releaseDate,
                    runtime = movieDetail.runTime,
                    rating = movieDetail.voteAverage,
                    description = movieDetail.overview,
                    posterPath = movieDetail.posterPath,
                    isFavorite = isFavorite, // ✅ boolean direkt buraya
                    onHeartClick = {
                        viewModel.toggleFavorite(movieDetail) // ✅ tetikleme
                    },
                    onPersonClick = { personId ->
                        // navigator.push(PersonDetailScreen(personId))
                    },
                    onBackClick = {
                        navigator.pop()
                    },
                    onPlayClick = {
                        trailerKey?.let { key ->
                            openYouTubeVideo(key)
                        }
                    }
                )
            }

            is UIState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surface),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = Modifier
                            .padding(24.dp)
                            .background(
                                color = MaterialTheme.colorScheme.errorContainer,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.ErrorOutline,
                            contentDescription = "Hata",
                            tint = MaterialTheme.colorScheme.error,
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Wrong",
                            color = MaterialTheme.colorScheme.error,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}
