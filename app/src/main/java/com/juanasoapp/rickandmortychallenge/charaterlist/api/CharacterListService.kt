package com.juanasoapp.rickandmortychallenge.charaterlist.api

import com.juanasoapp.rickandmortychallenge.api.RickAndMortyAPI
import com.juanasoapp.rickandmortychallenge.characterdetail.api.URLRawDataMapper
import com.juanasoapp.rickandmortychallenge.characterdetail.model.EpisodesResponse
import com.juanasoapp.rickandmortychallenge.charaterlist.model.CharacterResponse
import com.juanasoapp.rickandmortychallenge.charaterlist.model.DefinedCharacterResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.lang.RuntimeException
import javax.inject.Inject

class CharacterListService @Inject constructor(var api: RickAndMortyAPI, var mapper: URLRawDataMapper) {
    private val errorMessage = "Something went wrong"

    fun fetchCharacters(currentPage: Int, currentQuery: String): Flow<Result<CharacterResponse>> {
        return flow {
            emit(Result.success(api.fetchCharacters(currentPage, currentQuery)))
        }.catch {
            emit(Result.failure(RuntimeException(errorMessage)))
        }
    }

    fun fetchSingleDefinedCharacter(characters: List<String>): Flow<Result<DefinedCharacterResponse>> {
        return flow {
            val response = DefinedCharacterResponse().apply { addAll(listOf(api.fetchSingleDefinedCharacters(mapper(characters)))) }
            emit(Result.success(response))
        }.catch {
            emit(Result.failure(RuntimeException(errorMessage)))
        }
    }

    fun fetchDefinedCharacters(characters: List<String>): Flow<Result<DefinedCharacterResponse>> {
        return flow {
            emit(Result.success(api.fetchDefinedCharacters(mapper(characters))))
        }.catch {
            emit(Result.failure(RuntimeException(errorMessage)))
        }
    }
}