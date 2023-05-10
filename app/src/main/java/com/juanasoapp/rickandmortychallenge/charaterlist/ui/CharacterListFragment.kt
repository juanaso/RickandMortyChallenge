package com.juanasoapp.rickandmortychallenge.charaterlist.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.juanasoapp.rickandmortychallenge.R
import com.juanasoapp.rickandmortychallenge.charaterlist.viemodel.CharacterListViewModel
import com.juanasoapp.rickandmortychallenge.custom.GenericAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_character_list.*

@AndroidEntryPoint
class CharacterListFragment : Fragment() {

    private val viewModel: CharacterListViewModel by activityViewModels()
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

        viewModel.characters.observe(this as LifecycleOwner) { response ->
            response.getOrNull()?.let {
                (genericAdapter as GenericAdapter<Any>).setItems(it.results)
            }
        }
    }

    companion object {

    }
}