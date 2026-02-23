package com.example.hotel_pere_maria_app.ui.Views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hotel_pere_maria_app.ui.Models.Review
import com.example.hotel_pere_maria_app.ui.ViewModels.ReviewsViewModel

@Composable
fun ReviewsScreen(viewModel: ReviewsViewModel = viewModel()) {
        val userReviews by viewModel.userReviews.collectAsState()
        val isLoading by viewModel.isLoading.collectAsState()
        val error by viewModel.error.collectAsState()

        LaunchedEffect(Unit) { viewModel.loadUserReviews() }

        Column(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
        ) {
                Text(
                        text = "Mis Reseñas",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))

                if (isLoading && userReviews.isEmpty()) {
                        CircularProgressIndicator()
                } else if (error != null && userReviews.isEmpty()) {
                        Text(text = "Error: $error", color = MaterialTheme.colorScheme.error)
                } else if (userReviews.isEmpty()) {
                        Text(
                                text = "No has dejado ninguna reseña aún.",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(top = 32.dp)
                        )
                } else {
                        LazyColumn(
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier.fillMaxWidth()
                        ) {
                                items(userReviews) { review ->
                                        UserReviewCard(
                                                review = review,
                                                onDeleteClick = { viewModel.deleteReview(review) }
                                        )
                                }
                        }
                }
        }
}

@Composable
fun UserReviewCard(review: Review, onDeleteClick: () -> Unit) {
        Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
                Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                        ) {
                                Text(
                                        text = "Habitación: ${review.room_id}",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold
                                )
                                IconButton(onClick = onDeleteClick) {
                                        Icon(
                                                imageVector = Icons.Default.Delete,
                                                contentDescription = "Eliminar Reseña",
                                                tint = MaterialTheme.colorScheme.error
                                        )
                                }
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                                repeat(5) { i ->
                                        Icon(
                                                imageVector = Icons.Default.Star,
                                                contentDescription = null,
                                                tint =
                                                        if (i < review.rating)
                                                                MaterialTheme.colorScheme.primary
                                                        else
                                                                MaterialTheme.colorScheme
                                                                        .onSurfaceVariant.copy(
                                                                        alpha = 0.3f
                                                                ),
                                                modifier = Modifier.size(16.dp)
                                        )
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                        text = review.createdAt?.substringBefore("T") ?: "",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = review.comment, style = MaterialTheme.typography.bodyMedium)
                }
        }
}
