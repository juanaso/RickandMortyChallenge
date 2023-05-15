package com.juanasoapp.rickandmortychallenge.locationDetail

import com.juanasoapp.rickandmortychallenge.locationdetail.api.LocationRepository
import com.juanasoapp.rickandmortychallenge.locationdetail.model.RAMLocation
import com.juanasoapp.rickandmortychallenge.locationdetail.viewmodel.LocationDetailViewModel
import com.juanasoapp.rickandmortychallenge.utils.BaseUnitTest
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

class LocationDetailViewModelShould : BaseUnitTest() {

    private var repository: LocationRepository = mock()
    private var locationResponse: RAMLocation = mock()
    private var locationId = "1"
    private lateinit var viewModel: LocationDetailViewModel
    private val exception = RuntimeException("Something went wrong")

    @ExperimentalCoroutinesApi
    @Test
    fun getLocationFromRepository() = runBlockingTest {
        mockSuccessfulCase()
        viewModel.getLocation()
        verify(repository, times(1)).getLocation(locationId)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun emitsLocationFromRepository() = runBlockingTest {
        mockSuccessfulCase()
        viewModel.getLocation()
        assertEquals(locationResponse, viewModel.currentLocation.value)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun emitErrorWhenReceiveError() = runBlockingTest {
        mockFailureCase()
        viewModel.getLocation()
        assertNull(viewModel.currentLocation.value)
    }

    private fun mockFailureCase() {
        runBlocking {
            whenever(repository.getLocation(any())).thenReturn(
                flow { emit(Result.failure<RAMLocation>(exception)) }
            )
        }

        viewModel =  LocationDetailViewModel(repository)
        viewModel.locationId = locationId
    }

    private fun mockSuccessfulCase() {
        repository = mock<LocationRepository> {
            onBlocking { getLocation(any()) } doReturn flowOf(
                Result.success(
                    locationResponse
                )
            )
        }
        viewModel = LocationDetailViewModel(repository)
        viewModel.locationId = locationId
    }
}