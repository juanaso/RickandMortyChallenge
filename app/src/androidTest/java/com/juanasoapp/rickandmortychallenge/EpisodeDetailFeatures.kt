package com.juanasoapp.rickandmortychallenge

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import com.juanasoapp.rickandmortychallenge.utils.BaseUITest
import com.schibsted.spain.barista.assertion.BaristaRecyclerViewAssertions
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions
import org.hamcrest.CoreMatchers
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
        Thread.sleep(1000)
        BaristaRecyclerViewAssertions.assertRecyclerViewItemCount(R.id.episodeDetailCharacterRecycler, 19)
    }

    @Test
    fun navigateToCharacterScreen(){
        navigateToEpisodeDetail()
        Espresso.onView(
            CoreMatchers.allOf(
                ViewMatchers.withId(R.id.characterName),
                ViewMatchers.isDescendantOfA(nthChildOf(ViewMatchers.withId(R.id.episodeDetailCharacterRecycler), 0))
            )
        ).perform(ViewActions.click())
        BaristaVisibilityAssertions.assertDisplayed(R.id.characterDetailRoot)
    }
}