package com.juanasoapp.rickandmortychallenge.characterdetail.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juanasoapp.rickandmortychallenge.characterdetail.api.EpisodesRepository
import com.juanasoapp.rickandmortychallenge.characterdetail.model.Episode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(private val repository: EpisodesRepository) : ViewModel() {
    var episodes = MutableLiveData<List<Episode>>()
    var isLoadingEpisodes = MutableLiveData<Boolean>()
    var episodesRaw: List<String>? = null

    fun getEpisodes() {
       if (episodes.value.isNullOrEmpty()) {
            episodesRaw?.let {
                viewModelScope.launch {
                    isLoadingEpisodes.value = true
                    repository.getEpisodes(it).collect {
                        isLoadingEpisodes.value = false
                        episodes.value = it.getOrNull()
                    }
                }
            }
        }
    }
}