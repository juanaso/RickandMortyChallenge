package com.juanasoapp.rickandmortychallenge.api

import com.juanasoapp.rickandmortychallenge.charaterlist.model.CharacterResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RickAndMortyAPI {

    @GET("character")
    suspend fun fetchCharacters(@Query("page") page: Int, @Query("name") name: String): CharacterResponse
}
