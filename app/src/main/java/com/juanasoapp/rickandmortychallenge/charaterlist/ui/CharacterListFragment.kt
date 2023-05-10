package com.juanasoapp.rickandmortychallenge.charaterlist.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.juanasoapp.rickandmortychallenge.R
import com.juanasoapp.rickandmortychallenge.charaterlist.viemodel.CharacterListViewModel
import com.juanasoapp.rickandmortychallenge.custom.GenericAdapter
import com.juanasoapp.rickandmortychallenge.custom.SpacesItemDecoration
import com.juanasoapp.rickandmortychallenge.databinding.CharacterListItemBinding
import com.juanasoapp.rickandmortychallenge.databinding.FragmentCharacterListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterListFragment : Fragment() {

    private val viewModel: CharacterListViewModel by activityViewModels()
    var genericAdapter: GenericAdapter<Any>? = null
    lateinit var binding: FragmentCharacterListBinding
    val spaceInPixels = 6

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_character_list,
            container,
            false
        )
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpAdapter()
        setUpObservers()
    }

    private fun setUpAdapter() {

        val staggeredGridLayoutManager =
            StaggeredGridLayoutManager(3, LinearLayoutManager.VERTICAL)

        genericAdapter = object : GenericAdapter<Any>(emptyList(), R.layout.character_list_item) {

            override fun getViewHolder(viewDataBinding: ViewDataBinding): RecyclerView.ViewHolder {
                return CharacterViewHolder(viewDataBinding as CharacterListItemBinding)
            }
        }
        binding.homeCharacterRecycler.addItemDecoration(SpacesItemDecoration(spaceInPixels))
        binding.homeCharacterRecycler.layoutManager = staggeredGridLayoutManager
        binding.homeCharacterRecycler.adapter = genericAdapter
    }

    private fun setUpObservers() {
        viewModel.characters.observe(this as LifecycleOwner) { response ->
            response.getOrNull()?.let {
                (genericAdapter as GenericAdapter<Any>).setItems(it.results)
            }
        }
    }
}