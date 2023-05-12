package com.juanasoapp.rickandmortychallenge.characterdetail.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.juanasoapp.rickandmortychallenge.R
import com.juanasoapp.rickandmortychallenge.charaterlist.model.RAMCharacter
import com.juanasoapp.rickandmortychallenge.databinding.FragmentCharacterDetailBinding

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
        binding.characterDetailStatus.text = context?.getString(R.string.status_place_holder, currentCharacter.gender,
            currentCharacter.status)
        binding.characterDetailOrigin.text =context?.getString(R.string.origin_place_holder, currentCharacter.origin.name)


        Glide.with(this)
            .load(currentCharacter.image)
            .placeholder(R.drawable.portal)
            .into(binding.characterDetailImage)
    }
}