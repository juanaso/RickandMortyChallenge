package com.juanasoapp.rickandmortychallenge.characterdetail.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.juanasoapp.rickandmortychallenge.R
import com.juanasoapp.rickandmortychallenge.charaterlist.model.RAMCharacter
import com.juanasoapp.rickandmortychallenge.databinding.FragmentCharacterDetailBinding
import com.juanasoapp.rickandmortychallenge.utils.dpToPx

class CharacterDetailFragment : Fragment() {

    lateinit var binding: FragmentCharacterDetailBinding
    private val args: CharacterDetailFragmentArgs by navArgs()

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
        setUpView(currentCharacter)
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

        binding.title.setOnClickListener {
            binding.episodesContainer.addView(addText("test",20))
            binding.episodesContainer.addView(addText("test",20))
            binding.episodesContainer.addView(addText("test",20))
            binding.episodesContainer.addView(addText("test",20))
            binding.episodesContainer.addView(addText("test",20))
            binding.episodesContainer.addView(addText("test",20))
            binding.episodesContainer.addView(addText("test",20))
            binding.episodesContainer.addView(addText("test",20))
            binding.episodesContainer.addView(addText("test",20))
            binding.episodesContainer.addView(addText("test",20))
            val heightInPixels = context?.let { it1 -> dpToPx(220, it1) }
            val layoutParams = binding.episodesContainer.layoutParams
            layoutParams.height = heightInPixels?:0
            binding.episodesContainer.layoutParams = layoutParams

        }
    }

    private fun addText(text: String, int: Int): View {
        val textView = TextView(context)
        val heightInPx = context?.let { dpToPx(int, it) } // convert dp to px
        val layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            heightInPx?:0
        )
        textView.text = text
        textView.layoutParams = layoutParams
        return textView
    }
}