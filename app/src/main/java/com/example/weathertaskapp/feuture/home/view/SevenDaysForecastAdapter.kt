package com.example.weathertaskapp.feuture.home.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weathertaskapp.R
import com.example.weathertaskapp.domain.mapper.DayWiseForecast

class SevenDaysForecastAdapter(private val forecastData: MutableList<DayWiseForecast>) :
    RecyclerView.Adapter<SevenDaysForecastAdapter.ForecastViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_seven_days_forecast, parent, false)
        return ForecastViewHolder(view)
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        val currentItem = forecastData[position]
        holder.bind(currentItem)
    }

    override fun getItemCount() = forecastData.size

    fun addAlliItems(items: List<DayWiseForecast>){
        forecastData.clear()
        forecastData.addAll(items)
        notifyDataSetChanged()
    }

    class ForecastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvDay: TextView = itemView.findViewById(R.id.tvDay)
        private val tvDayState: TextView = itemView.findViewById(R.id.tvDayState)
        private val tvMaxMinTemp: TextView = itemView.findViewById(R.id.tvHighestLowestDegree)
        private val ivWeatherType: ImageView = itemView.findViewById(R.id.ivWeatherState)

        fun bind(forecastItem: DayWiseForecast) {
            tvDay.text = forecastItem.day
            val temperature =
                "${forecastItem.forecast.maxTemperature}/${forecastItem.forecast.minTemperature}${forecastItem.forecast.dailyUnits.temperature_2m_max}"
            tvMaxMinTemp.text = temperature
            tvDayState.text = forecastItem.forecast.weatherType.weatherDesc
            // Set image based on weather code
            ivWeatherType.setImageResource(forecastItem.forecast.weatherType.iconRes)
        }
    }
}