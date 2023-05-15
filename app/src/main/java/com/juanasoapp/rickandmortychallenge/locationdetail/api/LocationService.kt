package com.juanasoapp.rickandmortychallenge.locationdetail.api

import com.juanasoapp.rickandmortychallenge.api.RickAndMortyAPI
import com.juanasoapp.rickandmortychallenge.locationdetail.model.RAMLocation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.lang.RuntimeException
import javax.inject.Inject

class LocationService @Inject constructor(var api: RickAndMortyAPI) {
    private val backendExceptionErrorMessage = "Backend Exception"

    fun fetchLocation(locationId: String): Flow<Result<RAMLocation>> {
        return flow {
            emit(Result.success(api.fetchLocation(locationId)))
        }.catch {
            emit(Result.failure(RuntimeException(backendExceptionErrorMessage)))
        }
    }
}
