package com.juanasoapp.rickandmortychallenge.characterdetail.model

data class ViewHolderEpisode(var type: ViewHolderEpisodeType, var description: String)

enum class ViewHolderEpisodeType {
    TITLE,
    EPISODE
}