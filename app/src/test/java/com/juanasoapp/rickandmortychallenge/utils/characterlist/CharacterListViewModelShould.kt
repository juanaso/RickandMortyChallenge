package com.juanasoapp.rickandmortychallenge.utils.characterlist

import com.juanasoapp.rickandmortychallenge.charaterlist.api.CharacterListRepository
import com.juanasoapp.rickandmortychallenge.charaterlist.model.CharacterResponse
import com.juanasoapp.rickandmortychallenge.charaterlist.viemodel.CharacterListViewModel
import com.juanasoapp.rickandmortychallenge.utils.BaseUnitTest
import com.juanasoapp.rickandmortychallenge.utils.getValueForTest
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import com.nhaarman.mockitokotlin2.verify
import junit.framework.TestCase

class CharacterListViewModelShould : BaseUnitTest() {

    private val repository: CharacterListRepository = mock()
    private val characterResponse = mock<CharacterResponse>()
    private val expected = Result.success(characterResponse)
    private val exception = RuntimeException("Something went wrong")

    @ExperimentalCoroutinesApi
    @Test
    fun getCharacterListFromRepository() = runBlockingTest {
        val viewModel = mockSuccessfulCase()
        viewModel.characters.getValueForTest()
        verify(repository, times(1)).getCharacters()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun emitsAllWordsListFromRepository() = runBlockingTest {
        val viewModel = mockSuccessfulCase()
        TestCase.assertEquals(expected, viewModel.characters.getValueForTest())
    }

    @ExperimentalCoroutinesApi
    @Test
    fun emitErrorWhenReceiveError()=runBlockingTest {
        val viewModel = mockFailureCase()
        TestCase.assertEquals(exception, viewModel.characters.getValueForTest()!!.exceptionOrNull())
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
        runBlocking {
            whenever(repository.getCharacters()).thenReturn(
                flow {
                    emit(expected)
                }
            )
        }
        return CharacterListViewModel(repository)
    }

}