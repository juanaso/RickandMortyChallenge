package com.juanasoapp.rickandmortychallenge.characterdetail.api

import javax.inject.Inject

class EpisodeMapper @Inject constructor() : Function1<List<String>, String> {
    override fun invoke(p1: List<String>): String {
        return p1.map { it.substringAfterLast("/") }
            .joinToString(",")
    }
}