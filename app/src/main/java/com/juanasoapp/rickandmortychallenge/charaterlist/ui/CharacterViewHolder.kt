package com.juanasoapp.rickandmortychallenge.charaterlist.ui

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.juanasoapp.rickandmortychallenge.R
import com.juanasoapp.rickandmortychallenge.charaterlist.model.RAMCharacter
import com.juanasoapp.rickandmortychallenge.custom.GenericAdapter
import com.juanasoapp.rickandmortychallenge.databinding.CharacterListItemBinding
import com.juanasoapp.rickandmortychallenge.databinding.FragmentCharacterListBinding
import kotlinx.android.synthetic.main.character_list_item.view.*

class CharacterViewHolder(binding: CharacterListItemBinding, val onClick:((RAMCharacter) -> Unit)) : RecyclerView.ViewHolder(binding.root),
    GenericAdapter.Binder<RAMCharacter> {
    private var title: TextView = binding.characterName
    private var image: ImageView = binding.characterImage
    private var characterItemContainer: View = binding.characterItemContainer

    override fun bind(data: RAMCharacter) {
        title.text = data.name
        Glide.with(this.itemView)
            .load(data.image)
            .placeholder(R.drawable.portal)
            .into(image)
        characterItemContainer.setOnClickListener {
            onClick.invoke(data)
        }
    }
}