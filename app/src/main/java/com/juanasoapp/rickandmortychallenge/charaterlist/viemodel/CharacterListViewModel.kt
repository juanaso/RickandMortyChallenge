package com.juanasoapp.rickandmortychallenge.charaterlist.viemodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juanasoapp.rickandmortychallenge.charaterlist.api.CharacterListRepository
import com.juanasoapp.rickandmortychallenge.charaterlist.model.RAMCharacter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(val repository: CharacterListRepository) : ViewModel() {

    var firstVisiblePosition: Int=0
    var currentQuery: String = ""
    var currentPage = 1
    val characters = MutableLiveData<List<RAMCharacter>>()
    var allDataLoaded = false
    private var charactersJob: Job? = null

    fun loadCharacters() {
        if (!allDataLoaded) {
            charactersJob?.cancel()
            charactersJob = viewModelScope.launch {
                repository.getCharacters(currentPage, currentQuery).collect {
                    if (it.isSuccess) {
                        val response = it.getOrNull()
                        response?.let {
                            val existingCharacters = characters.value ?: ArrayList()
                            response.results.let { results ->
                                (existingCharacters as ArrayList).addAll(results)
                            }
                            characters.value = existingCharacters

                            if (response.info.next == null) {
                                allDataLoaded = true
                            } else {
                                currentPage++
                            }
                        }
                    }
                }
            }
        }
    }

    fun onTextSet(s: String) {
        currentQuery = s
        characters.value = arrayListOf()
        allDataLoaded = false
        currentPage = 1
        loadCharacters()
    }
}