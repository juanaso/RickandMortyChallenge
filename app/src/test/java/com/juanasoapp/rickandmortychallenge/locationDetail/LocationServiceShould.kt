package com.juanasoapp.rickandmortychallenge.locationDetail

import com.juanasoapp.rickandmortychallenge.api.RickAndMortyAPI
import com.juanasoapp.rickandmortychallenge.locationdetail.api.LocationService
import com.juanasoapp.rickandmortychallenge.locationdetail.model.RAMLocation
import com.juanasoapp.rickandmortychallenge.utils.BaseUnitTest
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

class LocationServiceShould : BaseUnitTest() {
    lateinit var service: LocationService
    var api: RickAndMortyAPI = mock()
    private var locationId = "1"
    private var locationResponse: RAMLocation = mock()


    @ExperimentalCoroutinesApi
    @Test
    fun getLocationFromApi() = runBlockingTest {
        mockSuccessCase()
        service.fetchLocation(locationId).first()
        verify(api, times(1)).fetchLocation(locationId)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun emitsErrorResultWhenNetworksFails() = runBlockingTest {
        mockFailureCase()
        assertEquals(
            backendExceptionErrorMessage,
            service.fetchLocation(locationId).first().exceptionOrNull()?.message
        )
    }

    private suspend fun mockSuccessCase() {
        runBlocking {
            whenever(api.fetchLocation(locationId)).thenReturn(locationResponse)
        }
        service = LocationService(api)
    }

    private suspend fun mockFailureCase() {
        runBlocking {
            whenever(api.fetchLocation(locationId)).thenThrow(RuntimeException(backendExceptionErrorMessage))
        }
        service = LocationService(api)
    }

}