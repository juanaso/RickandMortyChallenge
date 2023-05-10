package com.juanasoapp.rickandmortychallenge.charaterlist.ui

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.juanasoapp.rickandmortychallenge.charaterlist.model.RAMCharacter
import com.juanasoapp.rickandmortychallenge.custom.GenericAdapter
import kotlinx.android.synthetic.main.character_list_item.view.*

class CharacterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
    GenericAdapter.Binder<RAMCharacter> {
    private var title: TextView = itemView.characterName

    override fun bind(data: RAMCharacter) {
        title.text = data.name
    }
}