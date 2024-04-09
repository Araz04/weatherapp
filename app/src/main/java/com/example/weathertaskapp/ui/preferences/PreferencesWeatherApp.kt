package com.example.weathertaskapp.ui.preferences

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson

class PreferencesWeatherApp {
    companion object {
        private val PREF_NAME = "weather_app_shared"
        private var PRIVATE_MODE = 0

        private val PREF_TEMP_MODE = "Celsius"
        private val PREF_WINDD_MODE = "ms"
        private val PREF_IS_FIRST = "is_first_time"

        private var instance: PreferencesWeatherApp = PreferencesWeatherApp()
        private lateinit var sharedPreferences: SharedPreferences

        fun getInstance(context: Context): PreferencesWeatherApp {
            sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
            return instance
        }
    }

    fun getTemp()=  sharedPreferences.getInt(PREF_TEMP_MODE,0)
    fun setTemp(index:Int){
        with(sharedPreferences.edit()){
            putInt(PREF_TEMP_MODE,index)
            apply()
        }
    }

    fun getWind() =  sharedPreferences.getInt(PREF_WINDD_MODE,0)

    fun setWind(index: Int){
        with(sharedPreferences.edit()){
            putInt(PREF_WINDD_MODE,index)
            apply()
        }
    }

    fun setFirstTimeDone() {
        with(sharedPreferences.edit()) {
            putBoolean(PREF_IS_FIRST, false)
            apply()
        }
    }
    fun isFirstTime():Boolean {
        return sharedPreferences.getBoolean(PREF_IS_FIRST,true)
    }
}