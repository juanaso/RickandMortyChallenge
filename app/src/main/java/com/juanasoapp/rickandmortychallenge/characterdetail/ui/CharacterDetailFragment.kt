package com.juanasoapp.rickandmortychallenge.characterdetail.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.juanasoapp.rickandmortychallenge.R
import com.juanasoapp.rickandmortychallenge.characterdetail.model.ViewHolderEpisode
import com.juanasoapp.rickandmortychallenge.characterdetail.model.ViewHolderEpisodeType
import com.juanasoapp.rickandmortychallenge.characterdetail.viewmodel.CharacterDetailViewModel
import com.juanasoapp.rickandmortychallenge.charaterlist.model.RAMCharacter
import com.juanasoapp.rickandmortychallenge.databinding.FragmentCharacterDetailBinding
import com.juanasoapp.rickandmortychallenge.utils.EpisodesViewHolderMapper
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CharacterDetailFragment : Fragment() {

    private val viewModel: CharacterDetailViewModel by viewModels()
    private lateinit var binding: FragmentCharacterDetailBinding
    private val args: CharacterDetailFragmentArgs by navArgs()

    @Inject
    lateinit var episodesMapper: EpisodesViewHolderMapper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_character_detail,
            container,
            false
        )
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val currentCharacter = args.currentCharacter
        viewModel.episodesRaw = currentCharacter.episode
        setUpView(currentCharacter)
        setUpObservers()
    }

    private fun setUpObservers() {
        viewModel.episodes.observe(this as LifecycleOwner) { episodes ->
            createEpisodesViewer(episodesMapper(episodes))
        }

        viewModel.isLoadingEpisodes.observe(this as LifecycleOwner) { isLoading ->
            binding.episodesContainerTitleProgressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    private fun createEpisodesViewer(episodesMapper: List<ViewHolderEpisode>) {
        episodesMapper.forEach { viewHolderEpisode ->
            when (viewHolderEpisode.type) {
                ViewHolderEpisodeType.TITLE -> {
                    addViewToEpisodesContainer(R.layout.view_holder_episode_item_title, viewHolderEpisode.description)
                }
                ViewHolderEpisodeType.EPISODE -> {
                    addViewToEpisodesContainer(R.layout.view_holder_episode_item_episode, viewHolderEpisode.description)
                }
            }
        }
        expandContainer()
        Handler(Looper.getMainLooper()).postDelayed({
            scrollToChild(binding.scrollView, binding.episodesContainer)
        }, 500)
    }

    fun scrollToChild(scrollView: ScrollView, childView: View) {
        val childPosition = IntArray(2)
        childView.getLocationInWindow(childPosition)
        val scrollViewPosition = IntArray(2)
        scrollView.getLocationInWindow(scrollViewPosition)
        val scrollTo = childPosition[1] - scrollViewPosition[1]
        scrollView.smoothScrollTo(0, scrollTo)
    }

    private fun setUpView(currentCharacter: RAMCharacter) {
        binding.characterDetailName.text = currentCharacter.name
        binding.characterDetailSpecies.text = currentCharacter.species
        binding.characterDetailStatus.text = context?.getString(
            R.string.status_place_holder, currentCharacter.gender,
            currentCharacter.status
        )
        binding.characterDetailOrigin.text = context?.getString(R.string.origin_place_holder, currentCharacter.origin.name)

        Glide.with(this)
            .load(currentCharacter.image)
            .placeholder(R.drawable.portal)
            .into(binding.characterDetailImage)

        binding.episodesContainerTitle.setOnClickListener {
            viewModel.getEpisodes()
        }
    }

    private fun expandContainer() {
        val layoutParams = binding.episodesContainer.layoutParams
        val heightInPixels = resources.getDimensionPixelSize(R.dimen.episodes_viewer_row_height)
        layoutParams.height = (heightInPixels.times(((binding.episodesContainer.childCount) + 1)))
        binding.episodesContainer.layoutParams = layoutParams
        if (binding.episodesContainer.childCount > 1) {
            (binding.episodesContainer.getChildAt(3) as ViewGroup).getChildAt(0).visibility = View.VISIBLE
        }
    }

    private fun addViewToEpisodesContainer(layoutId: Int, text: String) {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(layoutId, null)
        view.findViewById<TextView>(R.id.description).text = text
        binding.episodesContainer.addView(view)
    }
}