package com.example.beelditechtest.presentation.ui.component

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.beelditechtest.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SearchFieldTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun searchField_acceptsInput_andClears() {
        val searchText = "TestSearch"
        composeTestRule.onNodeWithText("Rechercher un équipement...").performTextInput(searchText)
        composeTestRule.onNodeWithText(searchText).assertExists()
        // Si bouton clear présent, le tester
        composeTestRule.onAllNodesWithContentDescription("Effacer").onFirst().performClick()
        composeTestRule.onNodeWithText(searchText).assertDoesNotExist()
    }
}
