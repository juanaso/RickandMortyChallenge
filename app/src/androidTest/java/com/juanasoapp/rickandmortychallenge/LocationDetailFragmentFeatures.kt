package com.juanasoapp.rickandmortychallenge

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import com.juanasoapp.rickandmortychallenge.utils.BaseUITest
import com.schibsted.spain.barista.assertion.BaristaRecyclerViewAssertions
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import org.hamcrest.CoreMatchers
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

    @Test
    fun navigateToCharacterScreen(){
        navigateToLocationOfCharacter(9)
        Espresso.onView(
            CoreMatchers.allOf(
                ViewMatchers.withId(R.id.characterName),
                ViewMatchers.isDescendantOfA(nthChildOf(ViewMatchers.withId(R.id.locationDetailCharacterRecycler), 0))
            )
        ).perform(ViewActions.click())
        assertDisplayed(R.id.characterDetailRoot)
    }
}