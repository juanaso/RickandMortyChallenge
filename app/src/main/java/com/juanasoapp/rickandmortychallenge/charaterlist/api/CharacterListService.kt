package com.juanasoapp.rickandmortychallenge.charaterlist.api

import com.juanasoapp.rickandmortychallenge.api.RickAndMortyAPI
import com.juanasoapp.rickandmortychallenge.charaterlist.model.CharacterResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.lang.RuntimeException
import javax.inject.Inject

class CharacterListService @Inject constructor(var api: RickAndMortyAPI) {
    private val errorMessage = "Something went wrong"

    fun fetchCharacters(currentPage: Int): Flow<Result<CharacterResponse>> {
        return flow {
            emit(Result.success(api.fetchCharacters(currentPage)))
        }.catch {
            emit(Result.failure(RuntimeException(errorMessage)))
        }
    }
}