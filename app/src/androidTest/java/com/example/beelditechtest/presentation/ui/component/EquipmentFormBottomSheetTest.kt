package com.example.beelditechtest.presentation.ui.component

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.beelditechtest.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EquipmentFormBottomSheetTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun formFields_areDisplayed() {
        // Ouvre le formulaire (simulateur: clique sur bouton ajouter)
        composeTestRule.onNodeWithContentDescription("Ajouter un équipement").performClick()
        // Vérifie la présence des champs principaux
        composeTestRule.onNodeWithText("Nom de l'équipement").assertIsDisplayed()
        composeTestRule.onNodeWithText("Marque").assertIsDisplayed()
        composeTestRule.onNodeWithText("Modèle").assertIsDisplayed()
    }

    @Test
    fun saveButton_savesEquipment() {
        // Ouvre le formulaire
        composeTestRule.onNodeWithContentDescription("Ajouter un équipement").performClick()
        // Remplit un champ obligatoire
        composeTestRule.onNodeWithText("Nom de l'équipement").performTextInput("Test Save")
        // Clique sur le bouton enregistrer
        composeTestRule.onNodeWithText("Enregistrer").performClick()
        // Vérifie la fermeture du formulaire (champ n'est plus visible)
        composeTestRule.onNodeWithText("Nom de l'équipement").assertDoesNotExist()
    }
}
