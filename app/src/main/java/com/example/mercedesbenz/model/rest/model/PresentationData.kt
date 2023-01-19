package com.example.mercedesbenz.model.rest.model

data class PresentationData(
    val id: String,
    val nameRestaurant: String,
    val price: String? = "",
    val imageURLRestaurant: String,
    val ratingRestaurant: Float,
    val distance: Float,
    val display_phone: String,
    val location: Location,
    val text: String,
    val ratingReview: Int,
    val time_created: String,
    val imageURLReview: String,
    val nameUser: String
)
