package com.juanasoapp.rickandmortychallenge.characterdetail.api

import com.juanasoapp.rickandmortychallenge.characterdetail.model.EpisodesResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EpisodesRepository @Inject constructor(val service: EpisodeService) {

    fun getEpisodes(episodes: List<String>): Flow<Result<EpisodesResponse>> {
        return if (episodes.size == 1) {
            service.fetchSingleEpisode(episodes)
        } else {
            service.fetchEpisodes(episodes)
        }
    }
}