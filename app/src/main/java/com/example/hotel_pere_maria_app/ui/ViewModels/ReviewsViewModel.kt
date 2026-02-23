package com.example.hotel_pere_maria_app.ui.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hotel_pere_maria_app.ui.Models.Review
import com.example.hotel_pere_maria_app.ui.Models.ReviewRepository
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ReviewsViewModel : ViewModel() {

    val userReviews: StateFlow<List<Review>> = ReviewRepository.userReviews
    val isLoading: StateFlow<Boolean> = ReviewRepository.isLoading
    val error: StateFlow<String?> = ReviewRepository.error

    fun loadUserReviews() {
        viewModelScope.launch { ReviewRepository.fetchUserReviews() }
    }

    fun deleteReview(review: Review) {
        viewModelScope.launch { ReviewRepository.deleteReview(review.review_id, review.room_id) }
    }
}
