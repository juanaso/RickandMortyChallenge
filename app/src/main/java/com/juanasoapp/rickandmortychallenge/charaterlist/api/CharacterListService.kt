package com.juanasoapp.rickandmortychallenge.charaterlist.api

import com.juanasoapp.rickandmortychallenge.api.RickAndMortyAPI
import com.juanasoapp.rickandmortychallenge.charaterlist.model.CharacterResponse
import com.juanasoapp.rickandmortychallenge.charaterlist.model.RAMCharacter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.lang.RuntimeException

class CharacterListService(var api: RickAndMortyAPI) {
    private val errorMessage = "Something went wrong"

    fun fetchCharacters(): Flow<Result<CharacterResponse>> {
        return flow {
            emit(Result.success(api.fetchCharacters()))
        }.catch {
            emit(Result.failure(RuntimeException(errorMessage)))
        }
    }
}