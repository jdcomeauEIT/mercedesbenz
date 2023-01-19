package com.example.mercedesbenz.model.rest

import com.example.mercedesbenz.model.rest.model.RestaurantResponse
import com.example.mercedesbenz.model.rest.model.ReviewResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ServiceApi {
    @GET(ENDPOINT)
    suspend fun getBusinessByLocation(
        @Query(SEARCH_ARG_LAT)
        lat: Double,
        @Query(SEARCH_ARG_LON)
        lon: Double,
        @Query(SEARCH_ARG_LIMIT)
        limit: Int = 20): Response<RestaurantResponse>

    @GET(ENDPOINT_REVIEW)
    suspend fun getReviewByID(
        @Path(RESTAURANT_ID)
        id: String) : Response<ReviewResponse>
}