package com.example.mercedesbenz.model

import com.example.mercedesbenz.model.rest.ServiceApi
import com.example.mercedesbenz.model.rest.model.PresentationData
import com.example.mercedesbenz.model.rest.model.RestaurantResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class RestaurantRepository @Inject constructor(
    private val service: ServiceApi,
    private val coroutine: CoroutineScope): RepositoryContract {

    private val _restaurants = MutableStateFlow<UIState>(UIState.Empty)
    override val restaurants: StateFlow<UIState>
        get() = _restaurants

    override fun getHotRestaurants(lat: Double, lon: Double) {
        _restaurants.value = UIState.Loading()

        try{
            coroutine.launch {
                val response = service.getBusinessByLocation(lat, lon)
                if (response.isSuccessful){
                    response.body()?.let {
                        _restaurants.value = UIState.Success(
                            mergeReviewData(it)
                        )

                    } ?: kotlin.run {
                        _restaurants.value = UIState.Error(response.message())
                    }
                }else{
                    _restaurants.value = UIState.Error(response.message())
                }
            }
        }catch (ex: Exception){
            _restaurants.value = UIState.Error(ex.message ?: "Unknown Error")
        }

        _restaurants.value = UIState.Loading(false)
    }

    private suspend fun mergeReviewData(restaurantResponse: RestaurantResponse): List<PresentationData>{
        val mergedData = mutableListOf <PresentationData>()
        for(restaurant in restaurantResponse.businesses){
            service.getReviewByID(restaurant.id).body()?.review?.forEach {
                PresentationData(
                    restaurant.id,
                    restaurant.name,
                    restaurant.price,
                    restaurant.image_url,
                    restaurant.rating,
                    restaurant.distance,
                    restaurant.display_phone,
                    restaurant.location,
                    it.text,
                    it.rating,
                    it.time_created,
                    it.user.image_url,
                    it.user.name
                ).also { mergedData.add(it) }
            }
        }
        return mergedData
    }

}