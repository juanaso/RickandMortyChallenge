package com.juanasoapp.rickandmortychallenge.locationdetail.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.juanasoapp.rickandmortychallenge.R
import com.juanasoapp.rickandmortychallenge.charaterlist.model.DefinedCharacterResponse
import com.juanasoapp.rickandmortychallenge.charaterlist.ui.CharacterListFragmentDirections
import com.juanasoapp.rickandmortychallenge.charaterlist.ui.CharacterViewHolder
import com.juanasoapp.rickandmortychallenge.custom.GenericAdapter
import com.juanasoapp.rickandmortychallenge.custom.SpacesItemDecoration
import com.juanasoapp.rickandmortychallenge.databinding.CharacterListItemBinding
import com.juanasoapp.rickandmortychallenge.databinding.FragmentLocationDetailBinding
import com.juanasoapp.rickandmortychallenge.locationdetail.model.RAMLocation
import com.juanasoapp.rickandmortychallenge.locationdetail.viewmodel.LocationDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LocationDetailFragment : Fragment() {

    private val viewModel: LocationDetailViewModel by viewModels()
    private lateinit var binding: FragmentLocationDetailBinding
    private val args: LocationDetailFragmentArgs by navArgs()
    var genericAdapter: GenericAdapter<Any>? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_location_detail,
            container,
            false
        )
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpObservers()
        viewModel.locationId = args.currentLocationId
        viewModel.getLocation()
    }

    private fun setUpObservers() {
        viewModel.currentLocation.observe(this as LifecycleOwner) { location ->
            location?.let{ handleLocationData(location) }
        }
        viewModel.currentCharacters.observe(this as LifecycleOwner) { characters ->
            characters?.let{ handleCharacterList(characters) }
        }
    }

    private fun handleCharacterList(characters: DefinedCharacterResponse) {
        genericAdapter = object : GenericAdapter<Any>(arrayListOf(), R.layout.character_list_item) {

            override fun getViewHolder(viewDataBinding: ViewDataBinding): RecyclerView.ViewHolder {
                return CharacterViewHolder(viewDataBinding as CharacterListItemBinding) { currentCharacyer,currentBitmap ->
                    val action =
                        LocationDetailFragmentDirections.actionLocationDetailFragmentToCharacterDetailFragment(currentCharacyer,currentBitmap)
                    findNavController().navigate(action)
                }
            }
        }
        val staggeredGridLayoutManager =
            StaggeredGridLayoutManager(3, LinearLayoutManager.VERTICAL)
        (genericAdapter as GenericAdapter<Any>).setItems(ArrayList(characters))
        binding.locationDetailCharacterRecycler.addItemDecoration(SpacesItemDecoration())
        binding.locationDetailCharacterRecycler.layoutManager = staggeredGridLayoutManager
        binding.locationDetailCharacterRecycler.adapter = genericAdapter
    }

    private fun handleLocationData(location: RAMLocation) {
        binding.locationDetailName.text = location.name
        binding.locationDetailTypeText.text = location.type
        binding.locationDetailDimension.text = location.dimension
        binding.locationDetailDimension.text = context?.getString(
            R.string.location_dimension_placeholder, location.dimension.capitalize())

    }
}