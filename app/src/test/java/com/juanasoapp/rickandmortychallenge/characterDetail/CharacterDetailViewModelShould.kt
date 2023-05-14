package com.juanasoapp.rickandmortychallenge.characterDetail

import com.juanasoapp.rickandmortychallenge.characterdetail.api.EpisodesRepository
import com.juanasoapp.rickandmortychallenge.characterdetail.model.Episode
import com.juanasoapp.rickandmortychallenge.characterdetail.model.EpisodesResponse
import com.juanasoapp.rickandmortychallenge.characterdetail.viewmodel.CharacterDetailViewModel
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

class CharacterDetailViewModelShould : BaseUnitTest() {

    var episodesRaw = mock<List<String>>()
    private var repository: EpisodesRepository = mock()
    private val episodesResponse = mock<EpisodesResponse>()
    private val episode = mock<Episode>()
    private val episode2 = mock<Episode>()
    private val episodes = mock<List<Episode>>()
    private var episodesPair = listOf(episode,episode2)
    private val exception = RuntimeException("Something went wrong")


    @ExperimentalCoroutinesApi
    @Test
    fun getCharacterEpisodesFromRepository() = runBlockingTest {
        val viewModel = muckSuccessfulCase()
        viewModel.getEpisodes()
        verify(repository, times(1)).getEpisodes(episodesRaw)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun emitsAllEpisodesFromRepository() = runBlockingTest {
        val viewModel = muckSuccessfulCase()
        viewModel.getEpisodes()
        assertEquals(episodesResponse, viewModel.episodes.value)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun emitErrorWhenReceiveError() = runBlockingTest {
        val viewModel = mockFailureCase()
        viewModel.getEpisodes()
        assertNull(viewModel.episodes.value)
    }

    private fun mockFailureCase(): CharacterDetailViewModel {
        runBlocking {
            whenever(repository.getEpisodes(any())).thenReturn(
                flow { emit(Result.failure<EpisodesResponse>(exception)) }
            )
        }

        return CharacterDetailViewModel(repository)
    }

    private fun muckSuccessfulCase(): CharacterDetailViewModel {
        repository = mock<EpisodesRepository> {
            onBlocking { getEpisodes(any()) } doReturn flowOf(
                Result.success(
                    episodesResponse
                )
            )
        }
        whenever(episodesResponse.get(any())).thenReturn(episode)
        val viewModel = CharacterDetailViewModel(repository)
        viewModel.episodesRaw = episodesRaw
        return viewModel
    }
}