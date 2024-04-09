package com.example.weathertaskapp.feuture.locations.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weathertaskapp.R
import com.example.weathertaskapp.domain.models.entity.UserLocation

class SuggestedLocationsAdapter (private val locations: MutableList<UserLocation>, private val context: Context, private val listener: OnItemClickListener) :
RecyclerView.Adapter<SuggestedLocationsAdapter.LocationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_suggested_locations, parent, false)
        return LocationViewHolder(view)
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        val currentItem = locations[position]
        holder.bind(currentItem)
    }

    override fun getItemCount() = locations.size

    fun addAllItems(items: List<UserLocation>) {
        locations.clear()
        locations.addAll(items)
        notifyItemRangeInserted(locations.size - items.size, items.size)
    }

    fun removeAllItems() {
        locations.clear()
        notifyItemRangeRemoved(0, locations.size);
    }

    inner class LocationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvCityName: TextView = itemView.findViewById(R.id.tvLocationDetails)
        private val tvLat: TextView = itemView.findViewById(R.id.tvLat)
        private val tvLong: TextView = itemView.findViewById(R.id.tvLong)

        init {
            itemView.setOnClickListener {
                listener.onItemClicked(locations[layoutPosition], itemView)
            }
        }

        fun bind(location: UserLocation) {
            tvCityName.text = context.getString(
                R.string.country_state_string_resource,
                location.city,
                location.state
            )
            tvLat.text = context.getString(R.string.latitude_value, location.lat.toString())
            tvLong.text = context.getString(R.string.longitude_value, location.long.toString())
        }
    }

    interface OnItemClickListener {
        fun onItemClicked(location: UserLocation, itemView: View)
    }
}