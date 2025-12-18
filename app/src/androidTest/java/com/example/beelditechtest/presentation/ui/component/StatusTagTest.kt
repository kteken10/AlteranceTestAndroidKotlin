package com.example.beelditechtest.presentation.ui.component

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.beelditechtest.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class StatusTagTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun statusTag_displaysStatusText() {
        composeTestRule.onAllNodesWithText("Conforme").onFirst().assertExists()
        composeTestRule.onAllNodesWithText("À compléter").onFirst().assertExists()
        composeTestRule.onAllNodesWithText("En défaut").onFirst().assertExists()
    }
}
