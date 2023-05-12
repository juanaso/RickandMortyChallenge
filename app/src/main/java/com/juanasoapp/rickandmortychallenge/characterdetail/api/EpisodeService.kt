package com.juanasoapp.rickandmortychallenge.characterdetail.api

import com.juanasoapp.rickandmortychallenge.api.RickAndMortyAPI
import com.juanasoapp.rickandmortychallenge.characterdetail.model.EpisodesResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.lang.RuntimeException

class EpisodeService(var api: RickAndMortyAPI,var mapper: EpisodeMapper) {
    val backendExceptionErrorMessage = "Backend Exception"

    fun fetchEpisodes(episodesRaw: List<String>): Flow<Result<EpisodesResponse>> {
        return flow {
            emit(Result.success(api.fetchEpisodes( mapper(episodesRaw))))
        }.catch {
            emit(Result.failure(RuntimeException(backendExceptionErrorMessage)))
        }
    }
}