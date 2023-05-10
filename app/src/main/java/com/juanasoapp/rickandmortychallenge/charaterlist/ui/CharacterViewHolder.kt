package com.juanasoapp.rickandmortychallenge.charaterlist.ui

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.juanasoapp.rickandmortychallenge.charaterlist.model.RAMCharacter
import com.juanasoapp.rickandmortychallenge.custom.GenericAdapter
import com.juanasoapp.rickandmortychallenge.databinding.CharacterListItemBinding
import com.juanasoapp.rickandmortychallenge.databinding.FragmentCharacterListBinding
import kotlinx.android.synthetic.main.character_list_item.view.*

class CharacterViewHolder(binding: CharacterListItemBinding) : RecyclerView.ViewHolder(binding.root),
    GenericAdapter.Binder<RAMCharacter> {
    private var title: TextView = binding.characterName

    override fun bind(data: RAMCharacter) {
        title.text = data.name
    }
}