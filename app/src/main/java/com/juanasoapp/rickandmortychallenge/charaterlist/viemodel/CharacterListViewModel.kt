package com.juanasoapp.rickandmortychallenge.charaterlist.viemodel

import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.juanasoapp.rickandmortychallenge.charaterlist.api.CharacterListRepository

class CharacterListViewModel(repository: CharacterListRepository) {

    val characters = liveData {
        emitSource(repository.getCharacters().asLiveData())
    }
}