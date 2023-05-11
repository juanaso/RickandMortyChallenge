package com.juanasoapp.rickandmortychallenge

import android.view.KeyEvent
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.juanasoapp.rickandmortychallenge.core.idlingResource
import com.juanasoapp.rickandmortychallenge.utils.BaseUITest
import com.juanasoapp.rickandmortychallenge.utils.SearchViewActionExtension
import com.schibsted.spain.barista.assertion.BaristaRecyclerViewAssertions
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import org.hamcrest.CoreMatchers
import org.junit.Test

class CharacterListFeatures : BaseUITest() {

    @Test
    fun displaySearchBar() {
        assertDisplayed(R.id.homeCharacterSearchView)
    }

    @Test
    fun displaysListOfCharacters() {
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
    fun displaySecondBatchOfCharactersOnSwipe() {
        onView(withId(R.id.homeCharacterRecycler))
            .perform(ViewActions.swipeUp())

        assertDisplayed("Aqua Morty")
    }

    @Test
    fun emptySeriesListOnNewSearch() {
        enterTextAndSearch()
        IdlingRegistry.getInstance().unregister(idlingResource)
        enterTextAndSearch("jen")
        BaristaRecyclerViewAssertions.assertRecyclerViewItemCount(R.id.homeCharacterRecycler, 0)
    }

    @Test
    fun displaySearchedCharacter() {
        enterTextAndSearch()

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
    fun displayCorrectAmountOfSearchedCharacter() {
        enterText("summer")
        BaristaRecyclerViewAssertions.assertRecyclerViewItemCount(R.id.homeCharacterRecycler, 12)
    }

    @Test
    fun displayAllCharactersOnPressClearSearch() {
        enterText("summer")
        BaristaRecyclerViewAssertions.assertRecyclerViewItemCount(R.id.homeCharacterRecycler, 12)
        Espresso.onView(withId(R.id.search_close_btn)).perform(ViewActions.click())
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
    fun shouldNavigateToCharacterDetailScreen() {
        navigateToCharacterDetailScreen()
        assertDisplayed(R.id.characterDetailRoot)
    }

    private fun enterTextAndSearch(textToSearch: String = characterDummyName) {
        Espresso.onView(withId(R.id.homeCharacterSearchView)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.homeCharacterSearchView))
            .perform(SearchViewActionExtension.typeText(textToSearch))
            .perform(ViewActions.pressKey(KeyEvent.KEYCODE_ENTER))
    }

    private fun enterText(textToSearch: String = characterDummyName) {
        Espresso.onView(withId(R.id.homeCharacterSearchView)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.homeCharacterSearchView))
            .perform(SearchViewActionExtension.typeText(textToSearch))
    }
}