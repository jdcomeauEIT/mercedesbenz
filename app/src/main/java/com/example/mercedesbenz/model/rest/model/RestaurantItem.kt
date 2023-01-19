package com.example.mercedesbenz.model.rest.model


data class RestaurantItem(
    val id: String,
    val name: String,
    val price: String? = "",
    val image_url: String,
    val rating: Float,
    val distance: Float,
    val display_phone: String,
    val location: Location
)