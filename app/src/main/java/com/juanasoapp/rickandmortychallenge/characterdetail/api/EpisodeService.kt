package com.juanasoapp.rickandmortychallenge.characterdetail.api

import com.juanasoapp.rickandmortychallenge.BuildConfig
import com.juanasoapp.rickandmortychallenge.api.RickAndMortyAPI
import com.juanasoapp.rickandmortychallenge.characterdetail.model.EpisodesResponse
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.lang.RuntimeException
import javax.inject.Inject

class EpisodeService @Inject constructor(var api: RickAndMortyAPI, var mapper: URLRawDataMapper)  {
    val backendExceptionErrorMessage = "Backend Exception"

    fun fetchEpisodes(episodesRaw: List<String>): Flow<Result<EpisodesResponse>> {
        return flow {
            if (BuildConfig.IS_QA_FLAVOR){delay(500)}
            emit(Result.success(api.fetchEpisodes(mapper(episodesRaw))))
        }.catch {
            emit(Result.failure(RuntimeException(backendExceptionErrorMessage)))
        }
    }

    fun fetchSingleEpisode(episodesRaw: List<String>): Flow<Result<EpisodesResponse>> {
        var dataProsesed = mapper(episodesRaw)
        return flow {
            if (BuildConfig.IS_QA_FLAVOR){delay(500)}
            var responseRaw = api.fetchSingleEpisodes(dataProsesed)
            var response = EpisodesResponse().apply { addAll(listOf(responseRaw))}
            emit(Result.success(response))
        }.catch {
            emit(Result.failure(RuntimeException(backendExceptionErrorMessage)))
        }
    }
}