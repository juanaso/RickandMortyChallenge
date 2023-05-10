package com.juanasoapp.rickandmortychallenge.charaterlist.api

import com.juanasoapp.rickandmortychallenge.charaterlist.model.CharacterResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CharacterListRepository @Inject constructor(val service: CharacterListService) {
    fun getCharacters(): Flow<Result<CharacterResponse>> {
        return service.fetchCharacters()
    }
}