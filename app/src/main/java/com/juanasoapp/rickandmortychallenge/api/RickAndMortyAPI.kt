package com.juanasoapp.rickandmortychallenge.api

import com.juanasoapp.rickandmortychallenge.characterdetail.model.Episode
import com.juanasoapp.rickandmortychallenge.characterdetail.model.EpisodesResponse
import com.juanasoapp.rickandmortychallenge.charaterlist.model.CharacterResponse
import com.juanasoapp.rickandmortychallenge.charaterlist.model.DefinedCharacterResponse
import com.juanasoapp.rickandmortychallenge.charaterlist.model.RAMCharacter
import com.juanasoapp.rickandmortychallenge.locationdetail.model.RAMLocation
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyAPI {

    @GET("character")
    suspend fun fetchCharacters(@Query("page") page: Int, @Query("name") name: String): CharacterResponse

    @GET("episode/{currentLocation}")
    suspend fun fetchEpisodes(@Path("currentLocation") episodes: String): EpisodesResponse

    @GET("episode/{episodes}")
    suspend fun fetchSingleEpisodes(@Path("episodes") episodes: String): Episode

    @GET("location/{locationId}")
    suspend fun fetchLocation(@Path("locationId")locationId: String): RAMLocation

    @GET("character/{currentCharacterId}")
    suspend fun fetchSingleDefinedCharacters(@Path("currentCharacterId") currentCharacterId: String): RAMCharacter

    @GET("character/{currentCharacterId}")
    suspend fun fetchDefinedCharacters(@Path("currentCharacterId") currentCharacterId: String): DefinedCharacterResponse
}
