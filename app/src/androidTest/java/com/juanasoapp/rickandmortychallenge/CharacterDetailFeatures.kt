package com.juanasoapp.rickandmortychallenge

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
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
        onView(withId(R.id.episodesContainerTitle)).perform(ViewActions.click())
        assertDisplayed(R.id.episodesContainerTitleProgressBar)
    }

    @Test
    fun displayCorrectAmountOfEpisodesAndSeasons() {
        navigateToCharacterDetailScreen(9)
        val linearLayout = onView(withId(R.id.episodesContainer))
        onView(withId(R.id.episodesContainerTitle)).perform(ViewActions.click())
        Thread.sleep(1000)
        linearLayout.check(matches(childCountIs(3)))
    }

    @Test
    fun shouldNavigateToLocationDetailScreen() {
        navigateToLocationOfCharacter()
        assertDisplayed(R.id.locationDetailRoot)
    }
}