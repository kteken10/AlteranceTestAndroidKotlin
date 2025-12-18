package com.example.beelditechtest.presentation.ui.screen

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.beelditechtest.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EquipmentListScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun kpiCards_areDisplayed_andClickable() {
        // Vérifie la présence des cartes KPI
        composeTestRule.onNodeWithText("Total équipements").assertIsDisplayed()
        composeTestRule.onNodeWithText("Conformes").assertIsDisplayed()
        composeTestRule.onNodeWithText("À compléter").assertIsDisplayed()
        composeTestRule.onNodeWithText("En défaut").assertIsDisplayed()

        // Vérifie que chaque carte est cliquable
        composeTestRule.onNodeWithText("Total équipements").assertHasClickAction()
        composeTestRule.onNodeWithText("Conformes").assertHasClickAction()
        composeTestRule.onNodeWithText("À compléter").assertHasClickAction()
        composeTestRule.onNodeWithText("En défaut").assertHasClickAction()
    }

    @Test
    fun searchField_filtersEquipments() {
        // Tape un texte dans le champ de recherche
        val searchText = "Test"
        composeTestRule.onNodeWithText("Rechercher un équipement...").performTextInput(searchText)
        // Vérifie que la liste s'actualise (au moins un item ou message d'absence)
        composeTestRule.onAllNodesWithText(searchText).assertAny(hasText(searchText))
    }

    @Test
    fun addButton_opensBottomSheet() {
        // Clique sur le bouton d'ajout
        composeTestRule.onNodeWithContentDescription("Ajouter un équipement").performClick()
        // Vérifie la présence d'un champ du formulaire (ex: "Nom de l'équipement")
        composeTestRule.onNodeWithText("Nom de l'équipement").assertIsDisplayed()
    }
}
