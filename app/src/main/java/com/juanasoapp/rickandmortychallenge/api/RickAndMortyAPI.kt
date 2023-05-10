package com.juanasoapp.rickandmortychallenge.api

import com.juanasoapp.rickandmortychallenge.charaterlist.model.CharacterResponse
import retrofit2.http.GET

interface RickAndMortyAPI {

    @GET("character")
    suspend fun fetchCharacters(): CharacterResponse
}
