package com.juanasoapp.rickandmortychallenge.api

import com.juanasoapp.rickandmortychallenge.charaterlist.model.CharacterResponse
import com.juanasoapp.rickandmortychallenge.charaterlist.model.RAMCharacter
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyAPI {

    @GET("character")
    fun fetchCharacters(): CharacterResponse
}
