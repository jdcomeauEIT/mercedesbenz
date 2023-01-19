package com.example.mercedesbenz.model.rest.model

data class ReviewItem(
    val text: String,
    val rating: Int,
    val time_created: String,
    val user: ReviewUser
)
