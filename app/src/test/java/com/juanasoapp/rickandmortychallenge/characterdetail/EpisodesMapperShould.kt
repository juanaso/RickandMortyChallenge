package com.juanasoapp.rickandmortychallenge.characterdetail

import com.juanasoapp.rickandmortychallenge.characterdetail.api.URLRawDataMapper
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
    var characterMapped = "23"
    var characterRaw = listOf(
        "https://rickandmortyapi.com/api/character/23"
    )
    var multipleCharacterMapped = "23,24,4"
    var multipleCharacterRaw = listOf(
        "https://rickandmortyapi.com/api/character/23",
        "https://rickandmortyapi.com/api/character/24",
        "https://rickandmortyapi.com/api/character/4"
    )

    @Test
    fun returnMappedData() {
        var mapper = URLRawDataMapper()
        Assert.assertEquals(episodesMapped,mapper(episodesRaw))
    }

    @Test
    fun returnSingleMappedData() {
        var mapper = URLRawDataMapper()
        Assert.assertEquals(characterMapped,mapper(characterRaw))
    }

    @Test
    fun returnMultipleCharacterMappedData() {
        var mapper = URLRawDataMapper()
        Assert.assertEquals(multipleCharacterMapped,mapper(multipleCharacterRaw))
    }
}