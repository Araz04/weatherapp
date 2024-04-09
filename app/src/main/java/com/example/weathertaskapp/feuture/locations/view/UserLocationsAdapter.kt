package com.example.weathertaskapp.feuture.locations.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weathertaskapp.R
import com.example.weathertaskapp.domain.models.entity.SavedLocationWeatherOverview

class UserLocationsAdapter(
    private val userLocations: MutableList<SavedLocationWeatherOverview>,
    private val context: Context,
    private val listener: OnItemClickListener
) :
    RecyclerView.Adapter<UserLocationsAdapter.LocationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.rv_item_user_location, parent, false)
        return LocationViewHolder(view)
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        val currentItem = userLocations[position]
        holder.bind(currentItem)
    }

    override fun getItemCount() = userLocations.size

    fun addAllItems(items: List<SavedLocationWeatherOverview>) {
        userLocations.clear()
        userLocations.addAll(items)
        notifyDataSetChanged()
    }

    inner class LocationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvCityName: TextView = itemView.findViewById(R.id.tvCityName)
        private val tvCountryState: TextView = itemView.findViewById(R.id.tvCountryState)
        private val tvPrecipitation: TextView = itemView.findViewById(R.id.tvPrecipitation)
        private val tvWindSpeed: TextView = itemView.findViewById(R.id.tvWindSpeed)
        private val tvTemperature: TextView = itemView.findViewById(R.id.tvTemperature)
        private val ivWeatherType: ImageView = itemView.findViewById(R.id.ivWeatherType)
        private val icRemove: ImageView = itemView.findViewById(R.id.ivRemovePlace)

        init {
            icRemove.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = userLocations[position]
                    listener?.onRemoveClick(position, item, itemView)
                }
            }
        }

        fun bind(place: SavedLocationWeatherOverview) {
            tvCityName.text = place.location.city
            tvCountryState.text = context.getString(
                R.string.country_state_string_resource,
                place.location.country,
                place.location.state
            )
            tvTemperature.text =  place.forecast.hourlyUnits.temperature_180m
            tvTemperature.text = context.getString(
                R.string.two_string_concat_text,
                place.forecast?.temperature ?: "",
                place.forecast?.hourlyUnits?.temperature_180m ?: ""
            )
            // Set image based on weather code
            tvPrecipitation.text = context.getString(
                R.string.two_string_concat_text,
                place.forecast?.precipitation ?: "",
                place.forecast.hourlyUnits.precipitation ?: ""
            )
            tvWindSpeed.text = context.getString(
                R.string.two_string_concat_text,
                place.forecast?.wind ?: "",
                place.forecast?.hourlyUnits?.windspeed_180m ?: ""
            )
            ivWeatherType.setImageResource(place.forecast.weatherType.iconRes)

        }
    }

    interface OnItemClickListener {
        fun onRemoveClick(position: Int, place: SavedLocationWeatherOverview, view: View)
    }
}
