package com.juanasoapp.rickandmortychallenge.characterlist

import com.juanasoapp.rickandmortychallenge.api.RickAndMortyAPI
import com.juanasoapp.rickandmortychallenge.characterdetail.api.EpisodeService
import com.juanasoapp.rickandmortychallenge.characterdetail.api.URLRawDataMapper
import com.juanasoapp.rickandmortychallenge.charaterlist.api.CharacterListService
import com.juanasoapp.rickandmortychallenge.charaterlist.model.CharacterResponse
import com.juanasoapp.rickandmortychallenge.charaterlist.model.DefinedCharacterResponse
import com.juanasoapp.rickandmortychallenge.charaterlist.model.RAMCharacter
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

class CharacterListServiceShould : BaseUnitTest() {

    val api: RickAndMortyAPI = mock()
    val singleDefinedCharacterResponse: RAMCharacter = mock()
    val definedCharacterResponse: DefinedCharacterResponse = mock()
    private val characterResponse = mock<CharacterResponse>()
    private lateinit var service: CharacterListService
    val currentPage = 2
    val currentQuery = "test"
    var singleDefinedCharacterId = "1"
    var charactersRaw = listOf("")
    private var mapper: URLRawDataMapper = mock()
    var episodesMapped = "1"

    @ExperimentalCoroutinesApi
    @Test
    fun getCharacterListFromApi() = runBlockingTest {
        mockSuccessCase()
        service.fetchCharacters(currentPage, currentQuery).first()
        verify(api, times(1)).fetchCharacters(currentPage, currentQuery)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun convertValuesToFlowResultAndEmitsThem() = runBlockingTest {
        mockSuccessCase()
        Assert.assertEquals(Result.success(characterResponse), service.fetchCharacters(1, "").first())
    }

    @ExperimentalCoroutinesApi
    @Test
    fun emitsErrorResultWhenNetworksFails() = runBlockingTest {
        mockFailureCase()

        Assert.assertEquals(
            errorMessage,
            service.fetchCharacters(currentPage, "").first().exceptionOrNull()?.message
        )
    }

    @ExperimentalCoroutinesApi
    @Test
    fun passCurrentPageAndCurrentQueryIntoApi() = runBlockingTest {
        mockSuccessCase()
        service.fetchCharacters(currentPage, currentQuery).first().exceptionOrNull()
        verify(api).fetchCharacters(currentPage, currentQuery)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun fetchSingleDefinedCharacterFromApi() = runBlockingTest {
        mockSuccessCase()
        service.fetchSingleDefinedCharacter(charactersRaw).first()
        verify(api, times(1)).fetchSingleDefinedCharacters(singleDefinedCharacterId)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun fetchDefinedCharacterFromApi() = runBlockingTest {
        mockSuccessCase()
        service.fetchDefinedCharacters(charactersRaw).first()
        verify(api, times(1)).fetchDefinedCharacters(singleDefinedCharacterId)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun emitResultFromSingleDefinedCharacterFromApi() = runBlockingTest {
        mockSuccessCase()
        Assert.assertEquals(Result.success(listOf(singleDefinedCharacterResponse)), service.fetchSingleDefinedCharacter(charactersRaw).first())
    }

    @ExperimentalCoroutinesApi
    @Test
    fun emitResultFromFetchDefinedCharactersFromApi() = runBlockingTest {
        mockSuccessCase()
        Assert.assertEquals(Result.success(definedCharacterResponse), service.fetchDefinedCharacters(charactersRaw).first())
    }

    @ExperimentalCoroutinesApi
    @Test
    fun delegateCastingToMapperWhenSingle() = runBlockingTest {
        whenever(mapper.invoke(charactersRaw)).thenReturn(episodesMapped)
        whenever(api.fetchSingleDefinedCharacters(episodesMapped)).thenReturn(singleDefinedCharacterResponse)
        service = CharacterListService(api, mapper)
        service.fetchSingleDefinedCharacter(charactersRaw).first()
        verify(mapper, times(1)).invoke(charactersRaw)
    }


    @ExperimentalCoroutinesApi
    @Test
    fun delegateCastingToMapperWhenDefinedCharacters() = runBlockingTest {
        whenever(mapper.invoke(charactersRaw)).thenReturn(episodesMapped)
        whenever(api.fetchDefinedCharacters(episodesMapped)).thenReturn(definedCharacterResponse)
        service = CharacterListService(api, mapper)
        service.fetchDefinedCharacters(charactersRaw).first()
        verify(mapper, times(1)).invoke(charactersRaw)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun useMappedDataIntoApi() = runBlockingTest {
        whenever(mapper.invoke(charactersRaw)).thenReturn(episodesMapped)
        whenever(api.fetchSingleDefinedCharacters(episodesMapped)).thenReturn(singleDefinedCharacterResponse)
        service = CharacterListService(api, mapper)
        service.fetchSingleDefinedCharacter(charactersRaw).first()
        verify(api, times(1)).fetchSingleDefinedCharacters(episodesMapped)
    }

    private suspend fun mockSuccessCase() {
        whenever(mapper.invoke(charactersRaw)).thenReturn(episodesMapped)
        whenever(api.fetchCharacters(any(), any())).thenReturn(characterResponse)
        whenever(api.fetchSingleDefinedCharacters(any())).thenReturn(singleDefinedCharacterResponse)
        whenever(api.fetchDefinedCharacters(any())).thenReturn(definedCharacterResponse)
        service = CharacterListService(api, mapper)
    }

    private suspend fun mockFailureCase() {
        whenever(api.fetchCharacters(any(), any())).thenThrow(RuntimeException(backendExceptionErrorMessage))
        service = CharacterListService(api, mapper)
    }
}