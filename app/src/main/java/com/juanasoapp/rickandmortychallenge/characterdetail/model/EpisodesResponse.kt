package com.juanasoapp.rickandmortychallenge.characterdetail.model

import com.google.gson.annotations.SerializedName

class EpisodesResponse: ArrayList<Episode>()

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