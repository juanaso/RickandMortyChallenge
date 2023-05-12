package com.juanasoapp.rickandmortychallenge.characterdetail.model

import com.google.gson.annotations.SerializedName

data class EpisodesResponse(
    val episodes: List<Episode>
)

data class Episode(
    val id: Int,
    val name: String,
    @SerializedName("air_date")
    val airDate: String,
    val episode: String,
    val characters: List<String>,
    val url: String,
    val created: String
)