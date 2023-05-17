package com.juanasoapp.rickandmortychallenge.utils

import com.juanasoapp.rickandmortychallenge.R
import com.schibsted.spain.barista.assertion.BaristaRecyclerViewAssertions
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions
import org.junit.Test

class EpisodeDetailFeatures: BaseUITest() {

    @Test
    fun displayEpisodeName(){
        navigateToEpisodeDetail()
        BaristaVisibilityAssertions.assertDisplayed("Pilot")
        BaristaVisibilityAssertions.assertDisplayed("Episode: S01E01")
        BaristaVisibilityAssertions.assertDisplayed("Air Date: December 2, 2013")
    }

    @Test
    fun displayResidents(){
        navigateToEpisodeDetail()
        BaristaRecyclerViewAssertions.assertRecyclerViewItemCount(R.id.episodeDetailCharacterRecycler, 19)
    }
}