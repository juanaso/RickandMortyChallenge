package com.juanasoapp.rickandmortychallenge.charaterlist.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.juanasoapp.rickandmortychallenge.R
import com.juanasoapp.rickandmortychallenge.custom.GenericAdapter
import kotlinx.android.synthetic.main.fragment_character_list.*

class CharacterListFragment : Fragment() {

    var genericAdapter: GenericAdapter<Any>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_character_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        genericAdapter = object : GenericAdapter<Any>(emptyList()) {
            override fun getLayoutId(position: Int, obj: Any): Int {
                return R.layout.character_list_item
            }

            override fun getViewHolder(view: View, viewType: Int): RecyclerView.ViewHolder {
                return CharacterViewHolder(view)
            }
        }
        homeCharacterRecycler.adapter = genericAdapter
    }

    companion object {

    }
}