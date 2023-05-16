package com.juanasoapp.rickandmortychallenge

import com.juanasoapp.rickandmortychallenge.utils.BaseUITest
import com.schibsted.spain.barista.assertion.BaristaRecyclerViewAssertions
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import org.junit.Test

class LocationDetailFragmentFeatures : BaseUITest()  {

    @Test
    fun displayLocationInfo(){
        navigateToLocationOfCharacter()
        assertDisplayed(R.id.locationDetailName)
        assertDisplayed(R.id.locationDetailType)
        assertDisplayed(R.id.locationDetailDimension)
        assertDisplayed("Citadel of Ricks")
        assertDisplayed("Space station")
        assertDisplayed("Dimension : Unknown")
    }

    @Test
    fun displayResidents(){
        navigateToLocationOfCharacter(9)
        BaristaRecyclerViewAssertions.assertRecyclerViewItemCount(R.id.locationDetailCharacterRecycler, 9)
    }
}