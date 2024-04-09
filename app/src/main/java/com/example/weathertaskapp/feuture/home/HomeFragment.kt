package com.example.weathertaskapp.feuture.home

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weathertaskapp.R
import com.example.weathertaskapp.common.base.BaseFragment
import com.example.weathertaskapp.databinding.FragmentHomeBinding
import com.example.weathertaskapp.feuture.home.view.SevenDaysForecastAdapter
import com.example.weathertaskapp.ui.states.UserDataState
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalTime


class HomeFragment: BaseFragment() {
    private val readFine= Manifest.permission.ACCESS_FINE_LOCATION
    private val readCorse= Manifest.permission.ACCESS_COARSE_LOCATION
    private val permissions= arrayOf(
        readFine,readCorse
    )

    private val mViewModel: HomeViewModel by viewModel()
    override fun getViewModel() = mViewModel

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    lateinit var adapter: SevenDaysForecastAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestPermissions()
        initView()
        initData()
    }

    private fun initView(){
        if (checkPermissionLocation()){
            mViewModel.loadData()
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            mViewModel.loadData()
        }

        initSevenDaysForecast()
    }

    private fun initData(){
        viewLifecycleOwner.lifecycleScope.launch {
            mViewModel.userData.collect { userDataState ->
                initData(userDataState)
                binding.swipeRefreshLayout.isRefreshing = false
            }
        }
    }

    private fun initData(userDataState: UserDataState){
        val hourIndex = LocalTime.now().hour
        val forecast = userDataState.userData?.hourlyForecast?.get(0)?.get(hourIndex)
        binding.tvCityName.text = userDataState.userData?.location?.city ?: "Loading..."
        binding.tvDegree.text = getString(
            R.string.two_string_concat_text,
            forecast?.temperature ?: "",
            forecast?.hourlyUnits?.temperature_180m ?: ""
        )
        forecast?.weatherType?.iconRes?.let { binding.ivWeatherType.setImageResource(it) }

        userDataState.userData?.dailyForecast?.let {
            adapter.addAlliItems(it)
        }

        val day = userDataState.userData?.dailyForecast?.get(0)
        val temperatureOfTheDay = "${day?.forecast?.weatherType?.weatherDesc ?: ""} " +
                "${day?.forecast?.maxTemperature ?: ""}/${day?.forecast?.minTemperature ?: ""}" +
                (day?.forecast?.dailyUnits?.temperature_2m_max ?: "")
        binding.tvMinMaxTempOfDay.text = temperatureOfTheDay

        binding.tvPrecipitation.text = getString(
            R.string.two_string_concat_text,
            forecast?.precipitation ?: "",
            forecast?.hourlyUnits?.precipitation ?: ""
        )
        binding.tvPressure.text = getString(
            R.string.two_string_concat_text,
            forecast?.pressure ?: "",
            forecast?.hourlyUnits?.pressure_msl ?: ""
        )
        binding.tvWindSpeed.text = getString(
            R.string.two_string_concat_text,
            forecast?.wind ?: "",
            forecast?.hourlyUnits?.windspeed_180m ?: ""
        )
        binding.tvThermo.text = getString(
            R.string.two_string_concat_text,
            forecast?.relativeHumidity ?: "",
            forecast?.hourlyUnits?.relativehumidity_2m ?: ""
        )
    }

    private fun initSevenDaysForecast(){
        adapter = SevenDaysForecastAdapter(mutableListOf())
        binding.rvSevenDaysForecast.layoutManager = LinearLayoutManager(requireContext())
        binding.rvSevenDaysForecast.adapter = adapter
        binding.rvSevenDaysForecast.hasFixedSize()
    }

    private fun requestPermissions(){
        val notGrantedPermissions = permissions.filterNot { permission->
            ContextCompat.checkSelfPermission(requireContext(),permission) == PackageManager.PERMISSION_GRANTED
        }
        if (notGrantedPermissions.isNotEmpty()){
            val showRationale=notGrantedPermissions.any { permission->
                shouldShowRequestPermissionRationale(permission)
            }
            if (showRationale){
                AlertDialog.Builder(requireContext())
                    .setTitle("Location Permission")
                    .setMessage("Location permission is needed to get you city weather info")
                    .setNegativeButton("Cancel"){dialog,_->
                        Toast.makeText(requireContext(), "Location permission denied!", Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                    }
                    .setPositiveButton("OK"){_,_->
                        locationPermission.launch(notGrantedPermissions.toTypedArray())
                    }
                    .show()
            }else{
                locationPermission.launch(notGrantedPermissions.toTypedArray())
            }
        }else{
//            Toast.makeText(requireContext(), "Location permission granted", Toast.LENGTH_SHORT).show()
        }
    }

    private val locationPermission = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){ permissionMap->
        if (permissionMap.all { it.value }){
//            Toast.makeText(requireContext(), "Location permissions granted", Toast.LENGTH_SHORT).show()
            mViewModel.loadData()
        }else{
//            Toast.makeText(requireContext(), "Location permissions not granted!", Toast.LENGTH_SHORT).show()
            requestPermissions()
        }
    }
}