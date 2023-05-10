package com.juanasoapp.rickandmortychallenge.characterlist

import com.juanasoapp.rickandmortychallenge.charaterlist.api.CharacterListRepository
import com.juanasoapp.rickandmortychallenge.charaterlist.model.CharacterResponse
import com.juanasoapp.rickandmortychallenge.charaterlist.model.Info
import com.juanasoapp.rickandmortychallenge.charaterlist.model.RAMCharacter
import com.juanasoapp.rickandmortychallenge.charaterlist.viemodel.CharacterListViewModel
import com.juanasoapp.rickandmortychallenge.utils.BaseUnitTest
import com.juanasoapp.rickandmortychallenge.utils.getValueForTest
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import com.nhaarman.mockitokotlin2.verify
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNull
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf

class CharacterListViewModelShould : BaseUnitTest() {

    private var repository: CharacterListRepository = mock()
    private val characterResponse = mock<CharacterResponse>()
    private val ramCharacters = mock<List<RAMCharacter>>()
    private val info = mock<Info>()
    private val expected = Result.success(characterResponse)
    private val exception = RuntimeException("Something went wrong")

    @ExperimentalCoroutinesApi
    @Test
    fun getCharacterListFromRepository() = runBlockingTest {
        val viewModel = mockSuccessfulCase()
        viewModel.loadCharacters()
        verify(repository, times(1)).getCharacters()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun emitsAllWordsListFromRepository() = runBlockingTest {
        val viewModel = mockSuccessfulCase()
        viewModel.loadCharacters()
        assertEquals(ramCharacters, viewModel.characters.value)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun emitErrorWhenReceiveError() = runBlockingTest {
        val viewModel = mockFailureCase()
        viewModel.loadCharacters()
        assertNull(viewModel.characters.value)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun increasePageAfterSuccessfulCalling() {
        val viewModel = mockSuccessfulCase()
        viewModel.loadCharacters()
        assertEquals(2, viewModel.currentPage)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun notCallGetCharactersWhenAllDataLoaded() {
        repository = mock<CharacterListRepository> {
            onBlocking { getCharacters(any()) } doReturn flowOf(
                Result.success(
                    characterResponse
                )
            )
        }

        whenever(characterResponse.results).thenReturn(ramCharacters)
        whenever(expected.getOrNull()?.info).thenReturn(info)
        whenever(info.next).thenReturn(null)

        val viewModel = CharacterListViewModel(repository)
        viewModel.loadCharacters()
        viewModel.loadCharacters()
        verify(repository, times(1)).getCharacters()
    }

    private fun mockFailureCase(): CharacterListViewModel {
        runBlocking {
            whenever(repository.getCharacters()).thenReturn(
                flow { emit(Result.failure<CharacterResponse>(exception)) }
            )
        }
        return CharacterListViewModel(repository)
    }

    private fun mockSuccessfulCase(): CharacterListViewModel {
        repository = mock<CharacterListRepository> {
            onBlocking { getCharacters(any()) } doReturn flowOf(
                Result.success(
                    characterResponse
                )
            )
        }
        whenever(characterResponse.results).thenReturn(ramCharacters)
        whenever(expected.getOrNull()?.info).thenReturn(info)
        whenever(info.next).thenReturn("")
        return CharacterListViewModel(repository)
    }
}