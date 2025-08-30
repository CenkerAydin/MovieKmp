package org.cenkeraydin.moviekmp.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter.Companion.tint
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import moviekmp.composeapp.generated.resources.Res
import moviekmp.composeapp.generated.resources.baseline_calendar_today_24
import moviekmp.composeapp.generated.resources.baseline_star_24
import org.jetbrains.compose.resources.painterResource
import kotlin.math.floor


@Composable
fun MovieCard(
    title: String,
    year: String,
    genre: String,
    rating: Double,
    imageUrl: String,
    isFavorite: Boolean = false,
    onFavoriteClick: () -> Unit = {},
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(160.dp)
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E2E)),
        shape = RoundedCornerShape(16.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .width(100.dp)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(12.dp))
                ) {
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = "Movie Poster",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 16.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = title,
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            lineHeight = 20.sp
                        )

                        Spacer(Modifier.height(8.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                painter = painterResource(Res.drawable.baseline_calendar_today_24),
                                contentDescription = "Year",
                                tint = Color(0xFF9CA3AF),
                                modifier = Modifier.size(14.dp)
                            )
                            Text(
                                text = year,
                                color = Color(0xFF9CA3AF),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal
                            )
                        }
                        Spacer(Modifier.height(8.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(2.dp)
                        ) {
                            Icon(
                                painter = painterResource(Res.drawable.baseline_star_24),
                                contentDescription = "Rating",
                                tint = Color(0xFFFFD700),
                                modifier = Modifier.size(20.dp)
                            )
                            val truncatedRating = floor(rating * 10) / 10

                            Text(
                                text = "$truncatedRating | Movie  ",
                                color = Color.White,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal
                            )
                        }

                    }
                }
            }

            // Favorite icon positioned at bottom right
            IconButton(
                onClick = onFavoriteClick,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(12.dp)
                    .size(36.dp)
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Favori",
                    tint = if (isFavorite) Color.Red else Color.Gray
                )
            }
        }
    }
}