package com.juanasoapp.rickandmortychallenge.locationdetail.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juanasoapp.rickandmortychallenge.charaterlist.api.CharacterListRepository
import com.juanasoapp.rickandmortychallenge.charaterlist.model.DefinedCharacterResponse
import com.juanasoapp.rickandmortychallenge.charaterlist.model.RAMCharacter
import com.juanasoapp.rickandmortychallenge.locationdetail.api.LocationRepository
import com.juanasoapp.rickandmortychallenge.locationdetail.model.RAMLocation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationDetailViewModel @Inject constructor(
    private val repository: LocationRepository,
    private val characterRepository: CharacterListRepository
) : ViewModel() {

    lateinit var locationId: String
    var currentLocation = MutableLiveData<RAMLocation>()
    val currentCharacters = MutableLiveData<DefinedCharacterResponse>()

    fun getLocation() {
        if (currentLocation.value == null) {
            viewModelScope.launch {
                repository.getLocation(locationId).collect {
                    currentLocation.value = it.getOrNull()
                    getCharacters()
                }
            }
        }
    }

    fun getCharacters() {
        currentLocation.value?.let {
            viewModelScope.launch {
                characterRepository.getDefinedCharacters(it.residents).collect {
                    currentCharacters.value = it.getOrNull()
                }
            }
        }
    }
}
