package com.juanasoapp.rickandmortychallenge.locationdetail

import com.juanasoapp.rickandmortychallenge.locationdetail.api.LocationRepository
import com.juanasoapp.rickandmortychallenge.locationdetail.api.LocationService
import com.juanasoapp.rickandmortychallenge.locationdetail.model.RAMLocation
import com.juanasoapp.rickandmortychallenge.utils.BaseUnitTest
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Test

class LocationRepositoryShould: BaseUnitTest() {

    var service = mock<LocationService>()
    private var locationId = "1"
    private var locationResponse: RAMLocation = mock()
    lateinit var repository: LocationRepository
    private val exception = RuntimeException("Something went wrong")


    @Test
    fun getLocationFromService() = runBlockingTest {
        val repository = LocationRepository(service)
        repository.getLocation(locationId)
        verify(service, times(1)).fetchLocation(locationId)
    }

    @Test
    fun emitsLocationFromService() = runBlockingTest {
        mockSuccessfulCase()
        Assert.assertEquals(locationResponse, repository.getLocation(locationId).first().getOrNull())
    }

    @Test
    fun propagateErrors() = runBlockingTest {
        mockFailureCase()
        Assert.assertEquals(exception, repository.getLocation(locationId).first().exceptionOrNull())
    }


    private fun mockFailureCase() {
        runBlocking {
            whenever(service.fetchLocation(any())).thenReturn(
                flow { emit(Result.failure<RAMLocation>(exception)) }
            )
        }
        repository = LocationRepository(service)
    }

    private fun mockSuccessfulCase(){
        whenever(service.fetchLocation(any())).thenReturn(
            flow { emit(Result.success(locationResponse)) }
        )
        repository =  LocationRepository(service)
    }
}