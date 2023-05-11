package com.juanasoapp.rickandmortychallenge.charaterlist.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
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
        setUpSearchView()
        loadData()
    }

    private fun loadData() {
        if (viewModel.characters.value == null || viewModel.characters.value?.isEmpty() == true) {
            viewModel.loadCharacters()
        }
    }

    private fun setUpAdapter() {
        genericAdapter = object : GenericAdapter<Any>(arrayListOf(), R.layout.character_list_item) {

            override fun onLoadMoreItems() {
                viewModel.loadCharacters()
            }

            override fun getViewHolder(viewDataBinding: ViewDataBinding): RecyclerView.ViewHolder {
                return CharacterViewHolder(viewDataBinding as CharacterListItemBinding) {
                    val action =
                        CharacterListFragmentDirections.actionCharacterListFragmentToCharacterDetailFragment()
                    findNavController().navigate(action)
                }
            }
        }
        val staggeredGridLayoutManager =
            StaggeredGridLayoutManager(3, LinearLayoutManager.VERTICAL)
        binding.homeCharacterRecycler.addItemDecoration(SpacesItemDecoration())
        binding.homeCharacterRecycler.layoutManager = staggeredGridLayoutManager
        binding.homeCharacterRecycler.adapter = genericAdapter
    }

    private fun setUpSearchView() {

        binding.homeCharacterSearchView.findViewById<View>(R.id.search_close_btn).setOnClickListener {
            binding.homeCharacterSearchView.setOnQueryTextListener(null)
            binding.homeCharacterSearchView.setQuery("", false)
            viewModel.onTextSet("")
            binding.homeCharacterSearchView.setOnQueryTextListener(onQueryTextListener)
        }

        binding.homeCharacterSearchView.setOnClickListener {
            (it as SearchView).isIconified = false
        }

        binding.homeCharacterSearchView.setOnQueryTextListener(onQueryTextListener)
    }

    var onQueryTextListener = object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            viewModel.onTextSet(query ?: "")
            return false
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            if (newText?.isNotEmpty() == true) {
                viewModel.onTextSet(newText)
            }
            return false
        }
    }

    private fun setUpObservers() {
        viewModel.characters.observe(this as LifecycleOwner) { response ->
            response.let {
                (genericAdapter as GenericAdapter<Any>).setItems(ArrayList(it))
                if (viewModel.firstVisiblePosition > 0) {
                    binding.homeCharacterRecycler.scrollToPosition(viewModel.firstVisiblePosition)
                    viewModel.firstVisiblePosition = 0
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        val firstVisibleItemPositions: IntArray =
            (binding.homeCharacterRecycler.layoutManager as StaggeredGridLayoutManager).findFirstVisibleItemPositions(null)
        if (firstVisibleItemPositions.isNotEmpty()) {
            viewModel.firstVisiblePosition = firstVisibleItemPositions[0]
        }
    }
}