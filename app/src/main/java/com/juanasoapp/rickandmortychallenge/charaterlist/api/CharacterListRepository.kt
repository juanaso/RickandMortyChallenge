package com.juanasoapp.rickandmortychallenge.charaterlist.api

import com.juanasoapp.rickandmortychallenge.charaterlist.model.CharacterResponse
import com.juanasoapp.rickandmortychallenge.charaterlist.model.DefinedCharacterResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CharacterListRepository @Inject constructor(val service: CharacterListService) {

    fun getCharacters(currentPage: Int = 1, currentQuery: String): Flow<Result<CharacterResponse>> {
        return service.fetchCharacters(currentPage, currentQuery)
    }

    fun getDefinedCharacters(characters: List<String>): Flow<Result<DefinedCharacterResponse>> {
        return if (characters.size == 1) {
            service.fetchSingleDefinedCharacter(characters)
        } else {
            service.fetchDefinedCharacters(characters)
        }
    }
}