package com.example.beelditechtest.presentation.ui.component

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.beelditechtest.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EquipmentListTopAppBarTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun topAppBar_displaysLogoAndActions() {
        composeTestRule.onNodeWithContentDescription("Logo Beeldi").assertExists()
        composeTestRule.onNodeWithContentDescription("Notifications").assertExists()
        composeTestRule.onNodeWithContentDescription("Thème clair").assertExists()
        composeTestRule.onNodeWithContentDescription("Avatar utilisateur").assertExists()
    }
}
