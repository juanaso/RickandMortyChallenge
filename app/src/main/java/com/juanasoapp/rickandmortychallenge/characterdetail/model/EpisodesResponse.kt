package com.juanasoapp.rickandmortychallenge.characterdetail.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

class EpisodesResponse: ArrayList<Episode>()

@Parcelize
data class Episode(
    val id: Int,
    val name: String,
    @SerializedName("air_date")
    val airDate: String,
    val episode: String,
    val characters: List<String>,
    val url: String,
    val created: String
): Parcelable