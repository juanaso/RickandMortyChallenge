package com.juanasoapp.rickandmortychallenge.utils

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule

open class BaseUnitTest {

    val errorMessage = "Something went wrong"
    val backendExceptionErrorMessage = "Backend Exception"


    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutineTestRule =
        MainCoroutineScopeRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
}