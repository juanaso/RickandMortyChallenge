package com.juanasoapp.rickandmortychallenge

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.juanasoapp.rickandmortychallenge.utils.BaseUITest
import com.schibsted.spain.barista.assertion.BaristaRecyclerViewAssertions
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import org.hamcrest.CoreMatchers
import org.junit.Test

class CharacterListFeatures: BaseUITest() {

    @Test
    fun displaysListOfCharacters(){
        BaristaRecyclerViewAssertions.assertRecyclerViewItemCount(R.id.homeCharacterRecycler, 20)

        Espresso.onView(
            CoreMatchers.allOf(
                withId(R.id.characterName),
                ViewMatchers.isDescendantOfA(nthChildOf(withId(R.id.homeCharacterRecycler), 0))
            )
        )
            .check(ViewAssertions.matches(withText("Rick Sanchez")))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun displaySecondBatchOfCharactersOnSwipe(){
        Espresso.onView(withId(R.id.homeCharacterRecycler))
            .perform(ViewActions.swipeUp())

        assertDisplayed("Aqua Morty")
    }
}