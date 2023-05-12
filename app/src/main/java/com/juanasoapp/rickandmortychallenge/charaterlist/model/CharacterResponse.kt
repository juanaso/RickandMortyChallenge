package com.juanasoapp.rickandmortychallenge.charaterlist.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class CharacterResponse(
    val info: Info,
    val results: List<RAMCharacter>
)

@Parcelize
data class Info(
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?
): Parcelable

@Parcelize
data class RAMCharacter(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String?,
    val gender: String,
    val origin: Location,
    val location: Location,
    val image: String,
    val episode: List<String>,
    val url: String,
    val created: String
): Parcelable

@Parcelize
data class Location(
    val name: String,
    val url: String
): Parcelable