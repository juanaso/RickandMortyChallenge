package com.juanasoapp.rickandmortychallenge.characterdetail

import com.juanasoapp.rickandmortychallenge.api.RickAndMortyAPI
import com.juanasoapp.rickandmortychallenge.characterdetail.api.URLRawDataMapper
import com.juanasoapp.rickandmortychallenge.characterdetail.api.EpisodeService
import com.juanasoapp.rickandmortychallenge.characterdetail.model.Episode
import com.juanasoapp.rickandmortychallenge.characterdetail.model.EpisodesResponse
import com.juanasoapp.rickandmortychallenge.utils.BaseUnitTest
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Test

class EpisodesServiceShould : BaseUnitTest() {

    var api: RickAndMortyAPI = mock()
    var episodesRaw = mock<List<String>>()
    var episodesMapped = "1,2,3"
    private val episodeResponse = mock<EpisodesResponse>()
    private val singleEpisodeResponse = mock<Episode>()
    private lateinit var service: EpisodeService
    private var mapper: URLRawDataMapper = mock()

    @ExperimentalCoroutinesApi
    @Test
    fun getCharacterListFromApi() = runBlockingTest {
        mockSuccessCase()
        service.fetchEpisodes(episodesRaw).first()
        verify(api, times(1)).fetchEpisodes(episodesMapped)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getSingleEpisodeFromApi() = runBlockingTest {
        mockSuccessSingleCase()
        service.fetchSingleEpisode(episodesRaw).first()
        verify(api, times(1)).fetchSingleEpisodes(episodesMapped)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun convertValuesToFlowResultAndEmitsThem() = runBlockingTest {
        mockSuccessCase()
        assertEquals(Result.success(episodeResponse), service.fetchEpisodes(episodesRaw).first())
    }

    @ExperimentalCoroutinesApi
    @Test
    fun emitsErrorResultWhenNetworksFails() = runBlockingTest {
        mockFailureCase()

        Assert.assertEquals(
            backendExceptionErrorMessage,
            service.fetchEpisodes(episodesRaw).first().exceptionOrNull()?.message
        )
    }

    @Test
    fun delegateCastingToMapper() = runBlockingTest {
        runBlocking {
            whenever(mapper.invoke(episodesRaw)).thenReturn(episodesMapped)
            whenever(api.fetchEpisodes(episodesMapped)).thenReturn(episodeResponse)
            service = EpisodeService(api, mapper)
        }
        service.fetchEpisodes(episodesRaw).first()
        verify(mapper, times(1)).invoke(episodesRaw)
    }

    private suspend fun mockSuccessCase() {
        runBlocking {
            whenever(mapper.invoke(episodesRaw)).thenReturn(episodesMapped)
            whenever(api.fetchEpisodes(episodesMapped)).thenReturn(episodeResponse)
        }
        service = EpisodeService(api, mapper)
    }

    private suspend fun mockSuccessSingleCase() {
        runBlocking {
            whenever(mapper.invoke(episodesRaw)).thenReturn(episodesMapped)
            whenever(api.fetchSingleEpisodes(episodesMapped)).thenReturn(singleEpisodeResponse)
        }
        service = EpisodeService(api, mapper)
    }

    private suspend fun mockFailureCase() {
        runBlocking {
            whenever(mapper.invoke(any())).thenReturn(episodesMapped)
            whenever(api.fetchEpisodes(episodesMapped)).thenThrow(RuntimeException(backendExceptionErrorMessage))
        }
        service = EpisodeService(api, mapper)
    }
}