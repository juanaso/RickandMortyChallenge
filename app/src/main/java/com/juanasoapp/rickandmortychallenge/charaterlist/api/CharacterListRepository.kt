package com.juanasoapp.rickandmortychallenge.charaterlist.api

import com.juanasoapp.rickandmortychallenge.charaterlist.model.CharacterResponse
import com.juanasoapp.rickandmortychallenge.charaterlist.model.RAMCharacter
import kotlinx.coroutines.flow.Flow

class CharacterListRepository(val service: CharacterListService) {
    fun getCharacters(): Flow<Result<CharacterResponse>> {
        return service.fetchCharacters()
    }
}