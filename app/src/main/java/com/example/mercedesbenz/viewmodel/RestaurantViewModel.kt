package com.example.mercedesbenz.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mercedesbenz.model.RepositoryContract
import com.example.mercedesbenz.model.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RestaurantViewModel @Inject constructor(
    private val repository: RepositoryContract,
    private val coroutineScope: CoroutineScope): ViewModel() {

    private val _data = MutableLiveData<UIState>()
    val data: LiveData<UIState>
    get() = _data

        fun getRestaurantsByLocation(lat: Double, lon: Double){
            coroutineScope.launch {
                repository.restaurants
                    .onEach {
                        _data.value = it
                    }
                    .catch { it.printStackTrace() }
                    .collect()
            }
        }
}