package com.example.beelditechtest.presentation.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.beelditechtest.domain.model.Equipment
import com.example.beelditechtest.domain.model.EquipmentStatus
import com.example.beelditechtest.presentation.viewmodel.EquipmentFormViewModel
import com.example.beelditechtest.ui.theme.primaryColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EquipmentFormBottomSheet(
    viewModel: EquipmentFormViewModel,
    equipment: Equipment?,
    onDismiss: () -> Unit,
    onSaved: () -> Unit,
) {
    val state by viewModel.state.collectAsState()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    LaunchedEffect(equipment) {
        if (equipment != null) {
            viewModel.initForEdit(equipment)
        } else {
            viewModel.initForCreate()
        }
    }

    LaunchedEffect(state.isSaved) {
        if (state.isSaved) {
            viewModel.resetState()
            onSaved()
        }
    }

    ModalBottomSheet(
        onDismissRequest = {
            viewModel.resetState()
            onDismiss()
        },
        sheetState = sheetState,
        containerColor = Color.White,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(bottom = 32.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            // Titre
            Text(
                text = if (state.isEditMode) "Modifier l'équipement" else "Nouvel équipement",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 24.dp),
            )

            // Champ Nom
            OutlinedTextField(
                value = state.name,
                onValueChange = { viewModel.onNameChange(it) },
                label = { Text("Nom *") },
                modifier = Modifier.fillMaxWidth(),
                isError = state.nameError != null,
                supportingText = state.nameError?.let { { Text(it) } },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Champ Marque
            OutlinedTextField(
                value = state.brand,
                onValueChange = { viewModel.onBrandChange(it) },
                label = { Text("Marque *") },
                modifier = Modifier.fillMaxWidth(),
                isError = state.brandError != null,
                supportingText = state.brandError?.let { { Text(it) } },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Champ Modèle
            OutlinedTextField(
                value = state.model,
                onValueChange = { viewModel.onModelChange(it) },
                label = { Text("Modèle *") },
                modifier = Modifier.fillMaxWidth(),
                isError = state.modelError != null,
                supportingText = state.modelError?.let { { Text(it) } },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Champ Numéro de série
            OutlinedTextField(
                value = state.serialNumber,
                onValueChange = { viewModel.onSerialNumberChange(it) },
                label = { Text("Numéro de série *") },
                modifier = Modifier.fillMaxWidth(),
                isError = state.serialNumberError != null,
                supportingText = state.serialNumberError?.let { { Text(it) } },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Champ Étage
            OutlinedTextField(
                value = state.floor,
                onValueChange = { viewModel.onFloorChange(it) },
                label = { Text("Étage") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Sélecteur de statut
            Text(
                text = "Statut",
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(bottom = 8.dp),
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                EquipmentStatus.entries.forEach { status ->
                    val (label, color) = when (status) {
                        EquipmentStatus.OK -> "Conforme" to Color(0xFF4CAF50)
                        EquipmentStatus.TO_COMPLETE -> "À compléter" to Color(0xFFFF9800)
                        EquipmentStatus.DEFECT -> "En défaut" to Color(0xFFF44336)
                    }

                    FilterChip(
                        selected = state.status == status,
                        onClick = { viewModel.onStatusChange(status) },
                        label = { Text(label, fontSize = 12.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = color.copy(alpha = 0.2f),
                            selectedLabelColor = color,
                        ),
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Erreur
            state.error?.let { error ->
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(bottom = 8.dp),
                )
            }

            // Boutons d'action
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                // Bouton Annuler
                OutlinedButton(
                    onClick = {
                        viewModel.resetState()
                        onDismiss()
                    },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                ) {
                    Text("Annuler")
                }

                // Bouton principal
                Button(
                    onClick = { viewModel.onSubmit() },
                    modifier = Modifier.weight(1f),
                    enabled = !state.isLoading,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = primaryColor,
                        contentColor = Color.Black,
                    ),
                    shape = RoundedCornerShape(12.dp),
                ) {
                    if (state.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .width(20.dp)
                                .height(20.dp),
                            strokeWidth = 2.dp,
                        )
                    } else {
                        Text(if (state.isEditMode) "Mettre à jour" else "Ajouter")
                    }
                }
            }
        }
    }
}
