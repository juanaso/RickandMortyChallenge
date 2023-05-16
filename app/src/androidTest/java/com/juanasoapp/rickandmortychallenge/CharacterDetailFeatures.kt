package com.juanasoapp.rickandmortychallenge

import android.widget.LinearLayout
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import com.juanasoapp.rickandmortychallenge.utils.BaseUITest
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.schibsted.spain.barista.internal.performAction
import org.hamcrest.CoreMatchers.allOf
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
        onView(withId(R.id.episodesContainerTitle)).perform(click())
        assertDisplayed(R.id.episodesContainerTitleProgressBar)
    }

    @Test
    fun displayCorrectAmountOfEpisodesAndSeasons() {
        navigateToCharacterDetailScreen(9)
        val linearLayout = onView(withId(R.id.episodesContainer))
        onView(withId(R.id.episodesContainerTitle)).perform(click())
        Thread.sleep(1000)
        linearLayout.check(matches(childCountIs(3)))
    }

    @Test
    fun shouldNavigateToLocationDetailScreen() {
        navigateToLocationOfCharacter()
        assertDisplayed(R.id.locationDetailRoot)
    }

    @Test
    fun shouldNavigateToEpisodeDetailScreen() {
        navigateToCharacterDetailScreen()
        onView(withId(R.id.episodesContainerTitle)).perform(click())
        Thread.sleep(1000)
        onView(nthChildOfLinear(withId(R.id.episodesContainer), 3)).perform(click())
        assertDisplayed(R.id.episodeDetailRoot)
    }
}