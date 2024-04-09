package com.example.weathertaskapp.feuture.locations

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weathertaskapp.common.base.BaseFragment
import com.example.weathertaskapp.databinding.FragmentUserLocationsBinding
import com.example.weathertaskapp.domain.models.entity.SavedLocationWeatherOverview
import com.example.weathertaskapp.domain.models.entity.UserLocation
import com.example.weathertaskapp.feuture.locations.view.SuggestedLocationsAdapter
import com.example.weathertaskapp.feuture.locations.view.UserLocationsAdapter
import com.example.weathertaskapp.ui.layoutmanagers.LinearLayoutManagerWrapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


const val SEARCH_DELAY = 1000L

class UserLocationsFragment: BaseFragment() {
    private val mViewModel: UserLocationsViewModel by viewModel()
    override fun getViewModel() = mViewModel

    private var _binding: FragmentUserLocationsBinding? = null
    private val binding get() = _binding!!

    private lateinit var suggestedLocationsAdapter: SuggestedLocationsAdapter
    private lateinit var userLocationsAdapter: UserLocationsAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserLocationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initData()
    }

    private fun initView(){
        binding.ivClear.setOnClickListener {
            binding.etSearch.setText("")
        }
        binding.swipeRefreshLayout.setOnRefreshListener {
            lifecycleScope.launch {
                mViewModel.getAllSavedPlaces().collect { savedPlaces ->
                    // Update your UI with the new list of saved places
                    // For example, you can update a RecyclerView adapter with the new list
                    mViewModel.getSavedWeather()
                    binding.swipeRefreshLayout.isRefreshing = false
                }
            }
        }
        initSuggestedLocations()
        initUserLocationsAdapter()
        inputSearchTextChangeListener()
    }

    private fun initData(){
        viewLifecycleOwner.lifecycleScope.launch {
            mViewModel.locationSearchResult.collect { locationSearchResult ->
                if (locationSearchResult.data.isEmpty()){
                    binding.rvSuggestedLocations.visibility = View.GONE
                }else{
                    binding.rvSuggestedLocations.visibility = View.VISIBLE
                    suggestedLocationsAdapter.addAllItems(locationSearchResult.data)
                }
                binding.swipeRefreshLayout.isRefreshing = false
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            mViewModel.savedPlaceState.collect { savedPlaceState ->
                if (savedPlaceState.savedPlaces.isEmpty()){
                    binding.rvUserLocations.visibility = View.GONE
                }else{
                    binding.rvUserLocations.visibility = View.VISIBLE
                    userLocationsAdapter.addAllItems(savedPlaceState.savedPlaces)
                }
                binding.swipeRefreshLayout.isRefreshing = false

            }
        }
    }

    private fun inputSearchTextChangeListener() {
        var searchJob: Job? = null
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // No action needed
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Cancel the previous search job if it exists
                searchJob?.cancel()
            }

            override fun afterTextChanged(editable: Editable?) {
                // Start a new search job after a delay
                searchJob = CoroutineScope(Dispatchers.Main).launch {
                    delay(SEARCH_DELAY) // Wait for 1000ms after user stops typing
                    suggestedLocationsAdapter.removeAllItems()
                    mViewModel.getLocation(editable.toString().trim())
                    binding.swipeRefreshLayout.isRefreshing = true
                }
                if (editable.toString().trim().isNotEmpty()){
                    binding.ivClear.visibility = View.VISIBLE
                }else{
                    binding.ivClear.visibility = View.GONE
                    mViewModel.removeAllLocationsData()
                }
            }
        })
    }

    private fun initSuggestedLocations(){
        binding.rvSuggestedLocations.layoutManager = LinearLayoutManagerWrapper(requireContext())
        suggestedLocationsAdapter = SuggestedLocationsAdapter(mutableListOf(), requireContext(), object : SuggestedLocationsAdapter.OnItemClickListener {
            override fun onItemClicked(location: UserLocation, itemView: View) {
//                mViewModel.savePlace(location)
                lifecycleScope.launch {
                    val existingLocation = mViewModel.getPlaceByLat(location.lat.toString())
                    handleLocation(existingLocation, location)
                }
                binding.rvSuggestedLocations.visibility = View.GONE
                binding.etSearch.setText("")
                mViewModel.removeAllLocationsData()
                hideKeyboard()
            }
        })
        binding.rvSuggestedLocations.adapter = suggestedLocationsAdapter
    }

    private fun initUserLocationsAdapter(){
        lifecycleScope.launch {
            mViewModel.getAllSavedPlaces().collect { savedPlaces ->
                mViewModel.getSavedWeather()
            }
        }
        binding.rvUserLocations.layoutManager = LinearLayoutManager(requireContext())

        userLocationsAdapter = UserLocationsAdapter(mutableListOf(), requireContext(), object : UserLocationsAdapter.OnItemClickListener{
            override fun onRemoveClick(
                position: Int,
                place: SavedLocationWeatherOverview,
                view: View
            ) {
                mViewModel.removePlace(place.location)
            }
        })
        binding.rvUserLocations.adapter = userLocationsAdapter
    }

    private fun handleLocation(existingLocation: UserLocation?, newItem: UserLocation) {
        if (existingLocation != null) {
            Toast.makeText(
                requireContext(),
                "${newItem.city} is already marked as favourite",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            mViewModel.savePlace(newItem)
            Toast.makeText(
                requireContext(),
                "Location marked as favorite",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}



