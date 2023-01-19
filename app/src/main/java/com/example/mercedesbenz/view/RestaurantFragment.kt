package com.example.mercedesbenz.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mercedesbenz.databinding.RestaurantsFragmentLayoutBinding
import com.example.mercedesbenz.model.UIState
import com.example.mercedesbenz.model.rest.model.PresentationData
import com.example.mercedesbenz.model.rest.model.RestaurantItem
import com.example.mercedesbenz.model.rest.model.RestaurantResponse
import com.example.mercedesbenz.view.adapter.RestaurantsAdapter
import com.example.mercedesbenz.viewmodel.RestaurantViewModel
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "RestaurantFragment"

@AndroidEntryPoint
class RestaurantFragment: Fragment() {

    private val viewModel: RestaurantViewModel by viewModels()
    private val adapter: RestaurantsAdapter by lazy{
        RestaurantsAdapter(emptyList()) {
            navigateDetailsScreen(it)
        }
    }
    private val binding: RestaurantsFragmentLayoutBinding by lazy {
        RestaurantsFragmentLayoutBinding.inflate(layoutInflater)
    }

    private fun navigateDetailsScreen(dataItem: PresentationData) {

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        initViews()
        viewModel.data.observe(viewLifecycleOwner){
            updateState(it)
        }
        // todo get location services
        viewModel.getRestaurantsByLocation(18.012815,-92.9534317)
        return binding.root
    }

    private fun updateState(state: UIState) {
        when (state){
            is UIState.Empty -> {
                Log.d(TAG, "updateState: Empty") }
            is UIState.Error -> {
                Log.d(TAG, "updateState: Error")}
            is UIState.Success -> { updateAdapter(state.data) }
            is UIState.Loading -> {
                Log.d(TAG, "updateState: Loading")}
        }
    }

    private fun updateAdapter(data: List<PresentationData>) {
        adapter.updateDataSet(data)
    }

    private fun initViews() {
        binding.restaurantList.apply {
            adapter = this@RestaurantFragment.adapter
            layoutManager = LinearLayoutManager(context)
        }
    }
}