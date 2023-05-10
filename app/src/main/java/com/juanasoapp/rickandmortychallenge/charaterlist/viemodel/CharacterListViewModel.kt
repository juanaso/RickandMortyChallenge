package com.juanasoapp.rickandmortychallenge.charaterlist.viemodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.juanasoapp.rickandmortychallenge.charaterlist.api.CharacterListRepository
import com.juanasoapp.rickandmortychallenge.charaterlist.model.RAMCharacter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(val repository: CharacterListRepository) : ViewModel() {

    var currentPage = 1
    val characters = MutableLiveData<List<RAMCharacter>>()
    var allDataLoaded = false

    fun loadCharacters() {
        if (!allDataLoaded) {
            viewModelScope.launch {
                repository.getCharacters(currentPage).collect {
                    if (it.isSuccess) {
                        val response = it.getOrNull()!!
                        characters.value = (response.results)
                        if (response.info.next == null) {
                            allDataLoaded =true
                        }else{
                            currentPage++
                        }
                    }
                }
            }
        }
    }
}