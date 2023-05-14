package com.juanasoapp.rickandmortychallenge.characterDetail

import com.juanasoapp.rickandmortychallenge.characterdetail.model.Episode
import com.juanasoapp.rickandmortychallenge.utils.BaseUnitTest
import com.juanasoapp.rickandmortychallenge.utils.EpisodesViewHolderMapper
import junit.framework.Assert.assertEquals
import org.junit.Test

class EpisodesViewHolderMapperShould : BaseUnitTest() {

    @Test
    fun returnMappedViewHolderData() {
        var mapper = EpisodesViewHolderMapper()
        assertEquals(11, mapper(listOfEpisodes()).size)
    }

    @Test
    fun returnMappedViewHolderDataPair() {
        var mapper = EpisodesViewHolderMapper()
        assertEquals(4, mapper(listOfEpisodesPair()).size)
    }

    @Test
    fun returnMappedViewHolderDataOne() {
        var mapper = EpisodesViewHolderMapper()
        assertEquals(2, mapper(listOfEpisodesOne()).size)
    }

    fun listOfEpisodes(): List<Episode> {
        val episode4 = Episode(4, "tt", "", "S01E06", listOf(), "", "")
        val episode6 = Episode(6, "tt", "", "S01E07", listOf(), "", "")
        val episode7 = Episode(7, "tt", "", "S02E06", listOf(), "", "")
        val episode1 = Episode(1, "tt", "", "S03E04", listOf(), "", "")
        val episode2 = Episode(2, "tt", "", "S03E05", listOf(), "", "")
        val episode3 = Episode(3, "tt", "", "S03E06", listOf(), "", "")
        val episode5 = Episode(5, "tt", "", "S04E09", listOf(), "", "")
        return listOf(episode1, episode2, episode3, episode4, episode5, episode6, episode7)
    }

    fun listOfEpisodesPair(): List<Episode> {
        val episode4 = Episode(4, "tt", "", "S01E06", listOf(), "", "")
        val episode5 = Episode(5, "tt", "", "S04E09", listOf(), "", "")
        return listOf(episode4, episode5)
    }
    fun listOfEpisodesOne(): List<Episode> {
        val episode5 = Episode(5, "tt", "", "S04E09", listOf(), "", "")
        return listOf(episode5)
    }
}