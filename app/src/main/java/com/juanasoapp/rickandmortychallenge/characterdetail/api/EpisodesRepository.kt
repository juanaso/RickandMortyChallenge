package com.juanasoapp.rickandmortychallenge.characterdetail.api

import com.juanasoapp.rickandmortychallenge.characterdetail.model.EpisodesResponse
import kotlinx.coroutines.flow.Flow

class EpisodesRepository(val service: EpisodeService) {

    fun getEpisodes(episodes: List<String>) : Flow<Result<EpisodesResponse>> {
        return service.fetchEpisodes(episodes)
    }
}