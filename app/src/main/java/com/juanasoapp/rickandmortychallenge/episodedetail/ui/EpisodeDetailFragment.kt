package com.juanasoapp.rickandmortychallenge.episodedetail.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import com.juanasoapp.rickandmortychallenge.R
import com.juanasoapp.rickandmortychallenge.characterdetail.model.Episode
import com.juanasoapp.rickandmortychallenge.databinding.FragmentEpisodeDetailBinding

class EpisodeDetailFragment : Fragment() {

    private lateinit var binding: FragmentEpisodeDetailBinding
    private val arg: EpisodeDetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

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