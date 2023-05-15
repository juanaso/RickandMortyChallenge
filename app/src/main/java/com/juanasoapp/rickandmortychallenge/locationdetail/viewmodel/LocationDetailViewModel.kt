package com.juanasoapp.rickandmortychallenge.locationdetail.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juanasoapp.rickandmortychallenge.locationdetail.api.LocationRepository
import com.juanasoapp.rickandmortychallenge.locationdetail.model.RAMLocation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationDetailViewModel @Inject constructor(private val repository: LocationRepository) : ViewModel() {

    lateinit var locationId: String
    var currentLocation = MutableLiveData<RAMLocation>()

    fun getLocation() {
        viewModelScope.launch {
            repository.getLocation(locationId).collect {
                currentLocation.value = it.getOrNull()
            }
        }
    }
}
