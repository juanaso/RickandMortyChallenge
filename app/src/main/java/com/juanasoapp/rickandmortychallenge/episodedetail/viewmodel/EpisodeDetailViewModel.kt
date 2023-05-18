package com.juanasoapp.rickandmortychallenge.episodedetail.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juanasoapp.rickandmortychallenge.charaterlist.api.CharacterListRepository
import com.juanasoapp.rickandmortychallenge.charaterlist.model.DefinedCharacterResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EpisodeDetailViewModel @Inject constructor(val repository: CharacterListRepository) : ViewModel() {

    var charactersRaw: List<String>? = null
    val currentCharacters = MutableLiveData<DefinedCharacterResponse>()

    fun getCharacters() {
        if (currentCharacters.value.isNullOrEmpty()) {
            charactersRaw?.let { episodesRaw ->
                viewModelScope.launch {
                    repository.getDefinedCharacters(episodesRaw).collect{
                        currentCharacters.value = it.getOrNull()
                    }
                }
            }
        }
    }
}