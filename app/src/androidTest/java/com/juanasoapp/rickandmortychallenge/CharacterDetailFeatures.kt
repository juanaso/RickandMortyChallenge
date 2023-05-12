package com.juanasoapp.rickandmortychallenge

import com.juanasoapp.rickandmortychallenge.utils.BaseUITest
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import org.junit.Test

class CharacterDetailFeatures : BaseUITest(){

    @Test
    fun displayCharacterName(){
        navigateToCharacterDetailScreen()
        assertDisplayed(R.id.characterDetailName)
        assertDisplayed("Rick Sanchez")
    }

}