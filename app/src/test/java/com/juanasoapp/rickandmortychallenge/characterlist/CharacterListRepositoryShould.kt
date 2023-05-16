package com.juanasoapp.rickandmortychallenge.characterlist

import com.juanasoapp.rickandmortychallenge.charaterlist.api.CharacterListRepository
import com.juanasoapp.rickandmortychallenge.charaterlist.api.CharacterListService
import com.juanasoapp.rickandmortychallenge.charaterlist.model.CharacterResponse
import com.juanasoapp.rickandmortychallenge.charaterlist.model.DefinedCharacterResponse
import com.juanasoapp.rickandmortychallenge.utils.BaseUnitTest
import com.nhaarman.mockitokotlin2.any
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

class CharacterListRepositoryShould : BaseUnitTest() {

    var service: CharacterListService = mock()
    private val characterResponse = mock<CharacterResponse>()
    private val definedCharacterResponse = mock<DefinedCharacterResponse>()
    private val exception = RuntimeException(errorMessage)
    var charactersRaw = listOf("test")
    var charactersRawPair = listOf("test","test")

    @ExperimentalCoroutinesApi
    @Test
    fun getCharacterListFromService() = runBlockingTest {
        val repository = CharacterListRepository(service)
        repository.getCharacters(1, "")
        verify(service, times(1)).fetchCharacters(any(), any())
    }

    @ExperimentalCoroutinesApi
    @Test
    fun emitsCharacterFromService() = runBlockingTest {
        val repository = mockSuccessfulCase()
        Assert.assertEquals(characterResponse, repository.getCharacters(1, "").first().getOrNull())
    }

    @ExperimentalCoroutinesApi
    @Test
    fun passCurrentPageAndCurrentCurrentQueryIntoService() = runBlockingTest {
        val currentPage = 2
        val currentQuery = "test"
        val repository = mockSuccessfulCase()
        repository.getCharacters(currentPage, currentQuery).first().exceptionOrNull()
        verify(service).fetchCharacters(currentPage, currentQuery)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getSingleDefinedCharacterFromService() = runBlockingTest {
        val repository = CharacterListRepository(service)
        repository.getDefinedCharacters(charactersRaw)
        verify(service, times(1)).fetchSingleDefinedCharacter(charactersRaw)
    }
    @ExperimentalCoroutinesApi
    @Test
    fun getDefinedCharactersFromService() = runBlockingTest {
        val repository = mockSuccessfulCase()
        repository.getDefinedCharacters(charactersRawPair)
        verify(service, times(1)).fetchDefinedCharacters(charactersRawPair)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun propagateErrors() = runBlockingTest {
        val repository = mockFailureCase()

        Assert.assertEquals(exception, repository.getCharacters(1, "").first().exceptionOrNull())
    }

    private fun mockFailureCase(): CharacterListRepository {
        runBlocking {
            whenever(service.fetchCharacters(any(), any())).thenReturn(
                flow { emit(Result.failure<CharacterResponse>(exception)) }
            )
        }
        return CharacterListRepository(service)
    }

    private fun mockSuccessfulCase(): CharacterListRepository {
        runBlocking {
            whenever(service.fetchCharacters(any(), any())).thenReturn(
                flow { emit(Result.success(characterResponse)) }
            )

            whenever(service.fetchSingleDefinedCharacter(charactersRaw)).thenReturn(
                flow { emit(Result.success(definedCharacterResponse)) }
            )

            whenever(service.fetchDefinedCharacters(any())).thenReturn(
                flow { emit(Result.success(definedCharacterResponse)) }
            )
        }
        return CharacterListRepository(service)
    }
}