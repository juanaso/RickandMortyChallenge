package com.juanasoapp.rickandmortychallenge.locationdetail.api

import com.juanasoapp.rickandmortychallenge.locationdetail.model.RAMLocation
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocationRepository @Inject constructor(var service: LocationService) {

    fun getLocation(locationId: String): Flow<Result<RAMLocation>> {
           return service.fetchLocation(locationId)
    }
}