package com.juanasoapp.rickandmortychallenge.characterDetail

import com.juanasoapp.rickandmortychallenge.characterdetail.api.EpisodeMapper
import com.juanasoapp.rickandmortychallenge.utils.BaseUnitTest
import junit.framework.Assert
import org.junit.Test

class EpisodesMapperShould : BaseUnitTest() {

    var episodesMapped = "1,2,3"
    var episodesRaw = listOf(
        "https://rickandmortyapi.com/api/character/1",
        "https://rickandmortyapi.com/api/character/2",
        "https://rickandmortyapi.com/api/character/3"
    )

    @Test
    fun returnMappedData() {
        var mapper = EpisodeMapper()
        Assert.assertEquals(episodesMapped,mapper(episodesRaw))
    }
}