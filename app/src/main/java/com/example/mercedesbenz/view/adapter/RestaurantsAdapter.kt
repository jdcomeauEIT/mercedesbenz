package com.example.mercedesbenz.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mercedesbenz.R
import com.example.mercedesbenz.databinding.RestaurantItemLayoutBinding
import com.example.mercedesbenz.databinding.RestaurantsItemLayoutEmptyBinding
import com.example.mercedesbenz.model.rest.model.PresentationData
import com.squareup.picasso.Picasso

class RestaurantsAdapter(
    private var dataSet: List<PresentationData>,
    private val openDetails: (PresentationData) -> Unit):
        RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    class RestaurantViewHolder(private val binding: RestaurantItemLayoutBinding):
            RecyclerView.ViewHolder(binding.root){

        fun onBind(dataItem: PresentationData,
                           openDetails: (PresentationData) -> Unit){
                    binding.apply {
                        restaurantName.text = dataItem.nameRestaurant
                        restaurantPrice.text = dataItem.price
                        restaurantDistance.text = dataItem.distance.toString()
                        restaurantRating.text = dataItem.ratingRestaurant.toString()
                        Picasso.get().load(dataItem.imageURLRestaurant).into(restaurantImage)
                        root.setOnClickListener { openDetails(dataItem) }
                    }
                }
            }

    class RestaurantEmptyViewHolder(private val binding: RestaurantsItemLayoutEmptyBinding)
        : RecyclerView.ViewHolder(binding.root){

            fun onBind(){
                binding.emptyState.text = binding.root.context.getString(R.string.empty_state_message)
            }
    }

    override fun getItemViewType(position: Int): Int {
        return if (dataSet.isEmpty())
            ViewHolderType.ERROR_TYPE.ordinal
        else
            ViewHolderType.SUCCESS_TYPE.ordinal
    }

    enum class ViewHolderType{
        ERROR_TYPE, SUCCESS_TYPE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        when(viewType) {
            ViewHolderType.SUCCESS_TYPE.ordinal ->
            RestaurantViewHolder(
                RestaurantItemLayoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                )
            )
            else -> RestaurantEmptyViewHolder(
                RestaurantsItemLayoutEmptyBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                )
            )
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is RestaurantViewHolder -> holder.onBind(dataSet[position], openDetails)
            is RestaurantEmptyViewHolder -> holder.onBind()
        }
    }

    override fun getItemCount() = dataSet.size

    fun updateDataSet(newDataSet: List<PresentationData>){
        dataSet = newDataSet
        notifyItemRangeInserted(0, dataSet.size)
    }

}