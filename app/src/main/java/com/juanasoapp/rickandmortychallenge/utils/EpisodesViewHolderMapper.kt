package com.juanasoapp.rickandmortychallenge.utils

import com.juanasoapp.rickandmortychallenge.characterdetail.model.Episode
import com.juanasoapp.rickandmortychallenge.characterdetail.model.ViewHolderEpisode
import com.juanasoapp.rickandmortychallenge.characterdetail.model.ViewHolderEpisodeType

class EpisodesViewHolderMapper :Function1<List<Episode>, List<ViewHolderEpisode>>  {
    override fun invoke(episodes: List<Episode>): List<ViewHolderEpisode> {
        val viewHolderEpisodes = mutableListOf<ViewHolderEpisode>()
        val sortedEpisodes = episodes.sortedBy { it.episode.substring(1, 3).toInt() }
        var currentSeason = ""
        for (episode in sortedEpisodes) {
            val season = episode.episode.substring(1, 3)
            if (season != currentSeason) {
                currentSeason = season
                viewHolderEpisodes.add(
                    ViewHolderEpisode(
                        ViewHolderEpisodeType.TITLE,
                        "Season $currentSeason"
                    )
                )
            }
            viewHolderEpisodes.add(
                ViewHolderEpisode(
                    ViewHolderEpisodeType.EPISODE,
                    "Episode ${episode.episode}: ${episode.name}"
                )
            )
        }
        return viewHolderEpisodes
    }
}