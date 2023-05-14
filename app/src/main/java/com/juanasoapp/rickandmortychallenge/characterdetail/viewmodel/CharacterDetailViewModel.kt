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
    var episodesRaw: List<String>? = null

    fun getEpisodes() {
        episodesRaw?.let {
            viewModelScope.launch {
                repository.getEpisodes(it).collect {
                    episodes.value = it.getOrNull()
                }
            }
        }
    }
}