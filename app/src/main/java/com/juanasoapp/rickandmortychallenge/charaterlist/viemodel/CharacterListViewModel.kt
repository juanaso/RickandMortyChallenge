package com.juanasoapp.rickandmortychallenge.charaterlist.viemodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.juanasoapp.rickandmortychallenge.charaterlist.api.CharacterListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(repository: CharacterListRepository):ViewModel() {

    val characters = liveData {
        emitSource(repository.getCharacters().asLiveData())
    }
}