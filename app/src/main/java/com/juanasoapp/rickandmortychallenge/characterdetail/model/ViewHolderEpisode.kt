package com.juanasoapp.rickandmortychallenge.characterdetail.model

data class ViewHolderEpisode(var type: ViewHolderEpisodeType, var description: String, var episode: Episode? = null)

enum class ViewHolderEpisodeType {
    TITLE,
    EPISODE
}