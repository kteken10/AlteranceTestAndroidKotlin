package com.example.beelditechtest.presentation.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.beelditechtest.R
import com.example.beelditechtest.presentation.ui.component.EquipmentFormBottomSheet
import com.example.beelditechtest.presentation.ui.component.EquipmentItem
import com.example.beelditechtest.presentation.ui.component.EquipmentListTopAppBar
import com.example.beelditechtest.presentation.ui.component.KpiCard
import com.example.beelditechtest.presentation.ui.component.SearchField
import com.example.beelditechtest.presentation.viewmodel.EquipmentFormViewModel
import com.example.beelditechtest.presentation.viewmodel.EquipmentListViewModel
import com.example.beelditechtest.ui.theme.primaryColor
import com.example.beelditechtest.ui.theme.screenBackground
import kotlinx.coroutines.launch

@Composable
fun EquipmentListScreen(
    viewModel: EquipmentListViewModel,
    onEquipmentClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    formViewModel: EquipmentFormViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            EquipmentListTopAppBar(
                userAvatarResId = R.drawable.avatar_user,
                onAvatarClick = { /* TODO: Navigate to profile */ },
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { data ->
                Snackbar(
                    snackbarData = data,
                    containerColor = primaryColor,
                    contentColor = Color.Black,
                )
            }
        },
        containerColor = screenBackground,
        modifier = modifier,
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
             

            state.parkStats?.let { stats ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    KpiCard(
                        title = "Total équipements",
                        value = stats.equipmentStats.total.toString(),
                        icon = painterResource(id = R.drawable.ic_equipment),
                    )
                    KpiCard(
                        title = "Conformes",
                        value = stats.equipmentStats.okCount.toString(),
                        icon = painterResource(id = R.drawable.ic_validated),
                    )
                    KpiCard(
                        title = "À compléter",
                        value = stats.equipmentStats.toCompleteCount.toString(),
                        icon = painterResource(id = R.drawable.ic_edit),
                    )
                    KpiCard(
                        title = "En défaut",
                        value = stats.equipmentStats.defectCount.toString(),
                        icon = painterResource(id = R.drawable.ic_defect),
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
              // Titre Dashboard
           
            // Section Équipements avec bouton Ajouter
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Équipements",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold,
                )

                // Bouton Ajouter
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(primaryColor)
                        .clickable { viewModel.openBottomSheetForCreate() },
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_add),
                        contentDescription = "Ajouter un équipement",
                        modifier = Modifier.size(22.dp),
                        tint = Color(0xFF0F0F0F),
                    )
                }
            }

            // Champ de recherche
            SearchField(
                value = state.searchQuery,
                onValueChange = { viewModel.onSearchQueryChange(it) },
                placeholder = "Rechercher un équipement...",
            )

            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier.fillMaxSize(),
            ) {
                when {
                    state.isLoading -> {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center),
                        )
                    }
                    state.error != null -> {
                        Text(
                            text = state.error ?: "Erreur",
                            modifier = Modifier.align(Alignment.Center),
                            color = MaterialTheme.colorScheme.error,
                        )
                    }
                    state.filteredEquipments.isEmpty() && state.searchQuery.isNotEmpty() -> {
                        Text(
                            text = "Aucun équipement trouvé",
                            modifier = Modifier.align(Alignment.Center),
                            color = Color.Gray,
                        )
                    }
                    else -> {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                        ) {
                            items(state.filteredEquipments) { equipment ->
                                EquipmentItem(
                                    equipment = equipment,
                                    onClick = { onEquipmentClick(equipment.id) },
                                    onEditClick = { viewModel.openBottomSheetForEdit(equipment) },
                                    onDeleteClick = { viewModel.showDeleteConfirmation(equipment) },
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    // BottomSheet pour créer/éditer un équipement
    if (state.isBottomSheetOpen) {
        val isEditMode = state.selectedEquipment != null
        EquipmentFormBottomSheet(
            viewModel = formViewModel,
            equipment = state.selectedEquipment,
            onDismiss = { viewModel.closeBottomSheet() },
            onSaved = {
                viewModel.onEquipmentSaved()
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(
                        message = if (isEditMode) "✅ Équipement mis à jour avec succès" else "✅ Équipement ajouté avec succès",
                    )
                }
            },
        )
    }

    // Dialog de confirmation de suppression
    if (state.showDeleteConfirmation) {
        AlertDialog(
            onDismissRequest = { viewModel.hideDeleteConfirmation() },
            title = { Text("Confirmer la suppression") },
            text = {
                Text(
                    "Êtes-vous sûr de vouloir supprimer l'équipement \"${state.equipmentToDelete?.name}\" ? Cette action est irréversible."
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.confirmDelete()
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar(
                                message = "🗑️ Équipement supprimé avec succès",
                            )
                        }
                    },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = Color(0xFFF44336),
                    ),
                ) {
                    Text("Supprimer")
                }
            },
            dismissButton = {
                TextButton(onClick = { viewModel.hideDeleteConfirmation() }) {
                    Text("Annuler")
                }
            },
        )
    }
}
