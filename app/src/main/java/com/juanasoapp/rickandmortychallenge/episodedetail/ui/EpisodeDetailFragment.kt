package com.juanasoapp.rickandmortychallenge.episodedetail.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.juanasoapp.rickandmortychallenge.R
import com.juanasoapp.rickandmortychallenge.characterdetail.model.Episode
import com.juanasoapp.rickandmortychallenge.charaterlist.model.DefinedCharacterResponse
import com.juanasoapp.rickandmortychallenge.charaterlist.ui.CharacterViewHolder
import com.juanasoapp.rickandmortychallenge.custom.GenericAdapter
import com.juanasoapp.rickandmortychallenge.custom.SpacesItemDecoration
import com.juanasoapp.rickandmortychallenge.databinding.CharacterListItemBinding
import com.juanasoapp.rickandmortychallenge.databinding.FragmentEpisodeDetailBinding
import com.juanasoapp.rickandmortychallenge.episodedetail.viewmodel.EpisodeDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EpisodeDetailFragment : Fragment() {

    private lateinit var binding: FragmentEpisodeDetailBinding
    private val arg: EpisodeDetailFragmentArgs by navArgs()
    private val viewModel: EpisodeDetailViewModel by viewModels()
    var genericAdapter: GenericAdapter<Any>? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_episode_detail,
            container,
            false
        )
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val currentEpisode = arg.currentEpisode
        setUpView(currentEpisode)
        setUpObservers()
        viewModel.charactersRaw = currentEpisode.characters
        viewModel.getCharacters()
    }

    private fun setUpObservers() {
        viewModel.currentCharacters.observe(this as LifecycleOwner) { characters ->
            characters?.let{ handleCharacterList(characters) }
        }
    }

    private fun handleCharacterList(characters: DefinedCharacterResponse) {
        genericAdapter = object : GenericAdapter<Any>(arrayListOf(), R.layout.character_list_item) {

            override fun getViewHolder(viewDataBinding: ViewDataBinding): RecyclerView.ViewHolder {
                return CharacterViewHolder(viewDataBinding as CharacterListItemBinding) { currentCharacter,currentBitmap ->
                    val action =
                        EpisodeDetailFragmentDirections.actionEpisodeDetailFragmentToCharacterDetailFragment(currentCharacter,currentBitmap)
                    findNavController().navigate(action)
                }
            }
        }
        val staggeredGridLayoutManager =
            StaggeredGridLayoutManager(3, LinearLayoutManager.VERTICAL)
        (genericAdapter as GenericAdapter<Any>).setItems(ArrayList(characters))
        binding.episodeDetailCharacterRecycler.addItemDecoration(SpacesItemDecoration())
        binding.episodeDetailCharacterRecycler.layoutManager = staggeredGridLayoutManager
        binding.episodeDetailCharacterRecycler.adapter = genericAdapter
    }

    private fun setUpView(currentEpisode: Episode) {
        binding.apply {
            this.episodeDetailName.text = currentEpisode.name
            this.episodeDetailEpisode.text = context?.getString(
                R.string.episode_placeholder, currentEpisode.episode
            )

            this.episodDetailAirDate.text = context?.getString(
                R.string.air_date_placeholder, currentEpisode.airDate
            )
        }
    }
}