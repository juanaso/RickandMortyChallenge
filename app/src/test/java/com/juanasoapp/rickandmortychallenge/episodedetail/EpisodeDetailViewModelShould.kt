package com.juanasoapp.rickandmortychallenge.episodedetail

import com.juanasoapp.rickandmortychallenge.charaterlist.api.CharacterListRepository
import com.juanasoapp.rickandmortychallenge.charaterlist.model.DefinedCharacterResponse
import com.juanasoapp.rickandmortychallenge.episodedetail.viewmodel.EpisodeDetailViewModel
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

class EpisodeDetailViewModelShould:BaseUnitTest() {

    private lateinit var viewModel: EpisodeDetailViewModel
    private var repository: CharacterListRepository = mock()
    var charactersRaw = mock<List<String>>()
    private val characterResponse = mock<DefinedCharacterResponse>()
    private val exception = RuntimeException("Something went wrong")


    @ExperimentalCoroutinesApi
    @Test
    fun getLocationFromRepository() = runBlockingTest {
        mockSuccessfulCase()
        viewModel.getCharacters()
        verify(repository, times(1)).getDefinedCharacters(charactersRaw)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun emitsCharactersFromRepository() = runBlockingTest {
        mockSuccessfulCase()
        viewModel.charactersRaw = charactersRaw
        viewModel.getCharacters()
        assertEquals(characterResponse, viewModel.currentCharacters.value)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun emitErrorWhenReceiveError() = runBlockingTest {
        mockFailureCase()
        viewModel.getCharacters()
        assertNull(viewModel.currentCharacters.value)
    }

    private fun mockFailureCase() {
        runBlocking {
            whenever(repository.getDefinedCharacters(any())).thenReturn(
                flow { emit(Result.failure<DefinedCharacterResponse>(exception)) }
            )
        }

        viewModel = EpisodeDetailViewModel(repository)
        viewModel.charactersRaw = charactersRaw
    }

    private fun mockSuccessfulCase() {
        repository = mock {
            onBlocking { getDefinedCharacters(any()) } doReturn flowOf(
                Result.success(
                    characterResponse
                )
            )
        }

        viewModel = EpisodeDetailViewModel(repository)
        viewModel.charactersRaw = charactersRaw
//        whenever(locationResponse.residents).thenReturn(charactersRaw)
//        viewModel.locationId = locationId
    }

}