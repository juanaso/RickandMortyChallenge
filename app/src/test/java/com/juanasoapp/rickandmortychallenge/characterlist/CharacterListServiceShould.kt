package com.juanasoapp.rickandmortychallenge.characterlist

import com.juanasoapp.rickandmortychallenge.api.RickAndMortyAPI
import com.juanasoapp.rickandmortychallenge.charaterlist.api.CharacterListService
import com.juanasoapp.rickandmortychallenge.charaterlist.model.CharacterResponse
import com.juanasoapp.rickandmortychallenge.utils.BaseUnitTest
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Test

class CharacterListServiceShould:BaseUnitTest() {

    val api: RickAndMortyAPI = mock()
    private val characterResponse = mock<CharacterResponse>()
    private lateinit var service: CharacterListService
    val currentPage = 2
    val currentQuery = "test"

    @ExperimentalCoroutinesApi
    @Test
    fun getCharacterListFromApi() = runBlockingTest {
        mockSuccessCase()
        service.fetchCharacters(currentPage,currentQuery).first()
        verify(api, times(1)).fetchCharacters(currentPage,currentQuery)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun convertValuesToFlowResultAndEmitsThem() = runBlockingTest {
        mockSuccessCase()
        Assert.assertEquals(Result.success(characterResponse), service.fetchCharacters(1,"").first())
    }

    @ExperimentalCoroutinesApi
    @Test
    fun emitsErrorResultWhenNetworksFails() = runBlockingTest {
        mockFailureCase()

        Assert.assertEquals(
            errorMessage,
            service.fetchCharacters(currentPage,"").first().exceptionOrNull()?.message
        )
    }

    @ExperimentalCoroutinesApi
    @Test
    fun passCurrentPageAndCurrentQueryIntoApi() = runBlockingTest {
        mockSuccessCase()
        service.fetchCharacters(currentPage,currentQuery).first().exceptionOrNull()
        verify(api).fetchCharacters(currentPage,currentQuery)
    }

    private suspend fun mockSuccessCase() {
        whenever(api.fetchCharacters(any(), any())).thenReturn(characterResponse)
        service = CharacterListService(api)
    }

    private suspend fun mockFailureCase() {
        whenever(api.fetchCharacters(any(), any())).thenThrow(RuntimeException(backendExceptionErrorMessage))
        service = CharacterListService(api)
    }
}