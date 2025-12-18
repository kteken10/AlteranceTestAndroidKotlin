package com.example.beelditechtest.presentation.ui.component

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.beelditechtest.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EquipmentItemTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun equipmentItem_displaysMainInfo_andActions() {
        // Vérifie la présence d'un équipement dans la liste (nom, marque, bouton éditer/supprimer)
        composeTestRule.onAllNodesWithText("Équipements").onFirst().assertIsDisplayed()
        // Vérifie la présence des boutons d'action (icônes)
        composeTestRule.onAllNodesWithContentDescription("Modifier").onFirst().assertIsDisplayed()
        composeTestRule.onAllNodesWithContentDescription("Supprimer").onFirst().assertIsDisplayed()
    }
}
