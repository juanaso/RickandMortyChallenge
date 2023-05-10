package com.juanasoapp.rickandmortychallenge.utils.characterlist

import com.juanasoapp.rickandmortychallenge.charaterlist.api.CharacterListRepository
import com.juanasoapp.rickandmortychallenge.charaterlist.api.CharacterListService
import com.juanasoapp.rickandmortychallenge.charaterlist.model.CharacterResponse
import com.juanasoapp.rickandmortychallenge.charaterlist.model.RAMCharacter
import com.juanasoapp.rickandmortychallenge.utils.BaseUnitTest
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Test

class CharacterListRepositoryShould:BaseUnitTest() {

    var service : CharacterListService = mock()
    private val characterResponse = mock<CharacterResponse>()
    private val exception = RuntimeException(errorMessage)

    @ExperimentalCoroutinesApi
    @Test
    fun getCharacterListFromService() = runBlockingTest {
        val repository =  CharacterListRepository(service)
        repository.getCharacters()
        verify(service, times(1)).fetchCharacters()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun emitsCharacterFromService() = runBlockingTest {
        val repository = mockSuccessfulCase()
        Assert.assertEquals(characterResponse, repository.getCharacters().first().getOrNull())
    }

    @ExperimentalCoroutinesApi
    @Test
    fun propagateErrors() = runBlockingTest{
        val repository = mockFailureCase()

        Assert.assertEquals(exception, repository.getCharacters().first().exceptionOrNull())
    }

    private fun mockFailureCase(): CharacterListRepository {
        runBlocking {
            whenever(service.fetchCharacters()).thenReturn(
                flow { emit(Result.failure<CharacterResponse>(exception)) }
            )
        }
        return CharacterListRepository(service)
    }

    private fun mockSuccessfulCase(): CharacterListRepository {
        runBlocking {
            whenever(service.fetchCharacters()).thenReturn(
                flow { emit(Result.success(characterResponse)) }
            )
        }
        return CharacterListRepository(service)
    }
}