package com.example.mercedesbenz.model

import kotlinx.coroutines.flow.StateFlow

interface RepositoryContract {
    fun getHotRestaurants(lat: Double, lon: Double)
    val restaurants: StateFlow<UIState>
}