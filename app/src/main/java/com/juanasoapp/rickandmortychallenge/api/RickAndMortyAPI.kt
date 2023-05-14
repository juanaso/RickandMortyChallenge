package com.juanasoapp.rickandmortychallenge.api

import com.juanasoapp.rickandmortychallenge.characterdetail.model.Episode
import com.juanasoapp.rickandmortychallenge.characterdetail.model.EpisodesResponse
import com.juanasoapp.rickandmortychallenge.charaterlist.model.CharacterResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyAPI {

    @GET("character")
    suspend fun fetchCharacters(@Query("page") page: Int, @Query("name") name: String): CharacterResponse

    @GET("episode/{episodes}")
    suspend fun fetchEpisodes(@Path("episodes") episodes: String): EpisodesResponse

    @GET("episode/{episodes}")
    suspend fun fetchSingleEpisodes(@Path("episodes") episodes: String): Episode

    //    @GET("dv2/directVoice/{roomName}/loves")
    //    suspend fun getLoveHistory(@Path("roomName") roomName: String) : Response<LoveHistoryResponse>
}
