package com.juanasoapp.rickandmortychallenge.locationdetail.model

data class RAMLocation(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val residents: List<String>,
    val url: String,
    val created: String
)