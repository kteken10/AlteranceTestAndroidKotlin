package com.example.beelditechtest.presentation.ui.screen

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.beelditechtest.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EquipmentDetailScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun detailScreen_displaysEquipmentInfo() {
        // Vérifie la présence de certains champs clés (nom, marque, modèle)
        composeTestRule.onNodeWithText("Nom").assertExists()
        composeTestRule.onNodeWithText("Marque").assertExists()
        composeTestRule.onNodeWithText("Modèle").assertExists()
    }

    @Test
    fun backButton_navigatesBack() {
        // Clique sur le bouton retour
        composeTestRule.onNodeWithContentDescription("Retour").performClick()
        // Vérifie qu'on revient à la liste (présence du titre "Équipements")
        composeTestRule.onNodeWithText("Équipements").assertExists()
    }
}
