package com.juanasoapp.rickandmortychallenge

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import com.juanasoapp.rickandmortychallenge.utils.BaseUITest
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import org.junit.Test

class CharacterDetailFeatures : BaseUITest() {

    @Test
    fun displayCharacterName() {
        navigateToCharacterDetailScreen()
        assertDisplayed(R.id.characterDetailName)
        assertDisplayed("Rick Sanchez")
    }

    @Test
    fun displayLoadingOnClickInAppearances() {
        navigateToCharacterDetailScreen()
        Espresso.onView(ViewMatchers.withId(R.id.episodesContainerTitle)).perform(ViewActions.click())
        assertDisplayed(R.id.episodesContainerTitleProgressBar)
    }
}