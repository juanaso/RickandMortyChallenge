package com.juanasoapp.rickandmortychallenge.characterDetail

import com.juanasoapp.rickandmortychallenge.characterdetail.api.EpisodeService
import com.juanasoapp.rickandmortychallenge.characterdetail.api.EpisodesRepository
import com.juanasoapp.rickandmortychallenge.characterdetail.model.EpisodesResponse
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

class EpisodesRepositoryShould : BaseUnitTest() {

    var service = mock<EpisodeService>()
    var episodesRaw = listOf("","")
    var episodesRawSingle = listOf("")
    private val episodesResponse = mock<EpisodesResponse>()
    private val episodesResponseSingle = mock<EpisodesResponse>()
    private val exception = RuntimeException("Something went wrong")

    @Test
    fun getEpisodesFromService() = runBlockingTest {
        val repository = EpisodesRepository(service)
        repository.getEpisodes(episodesRaw)
        verify(service, times(1)).fetchEpisodes(episodesRaw)
    }

    @Test
    fun getSingleEpisodeFromService() = runBlockingTest {
        val repository = EpisodesRepository(service)
        repository.getEpisodes(episodesRawSingle)
        verify(service, times(1)).fetchSingleEpisode(episodesRawSingle)
    }

    @Test
    fun emitsEpisodesFromService() = runBlockingTest {
        val repository = mockSuccessfulCase()
        Assert.assertEquals(episodesResponse, repository.getEpisodes(episodesRaw).first().getOrNull())
    }

    @Test
    fun emitsEpisodesFromServiceWhenSingle() = runBlockingTest {
        val repository = mockSuccessfulCaseSingle()
        Assert.assertEquals(episodesResponseSingle, repository.getEpisodes(episodesRawSingle).first().getOrNull())
    }

    @Test
    fun propagateErrors() = runBlockingTest {
        val repository = mockFailureCase()
        Assert.assertEquals(exception, repository.getEpisodes(episodesRaw).first().exceptionOrNull())
    }

    private fun mockFailureCase(): EpisodesRepository {
        runBlocking {
            whenever(service.fetchEpisodes(episodesRaw)).thenReturn(
                flow { emit(Result.failure<EpisodesResponse>(exception)) }
            )
        }
        return EpisodesRepository(service)
    }

    private fun mockSuccessfulCase(): EpisodesRepository {
        whenever(service.fetchEpisodes(any())).thenReturn(
            flow { emit(Result.success(episodesResponse)) }
        )
        return EpisodesRepository(service)
    }

    private fun mockSuccessfulCaseSingle(): EpisodesRepository {
        whenever(service.fetchSingleEpisode(any())).thenReturn(
            flow { emit(Result.success(episodesResponseSingle)) }
        )
        return EpisodesRepository(service)
    }
}