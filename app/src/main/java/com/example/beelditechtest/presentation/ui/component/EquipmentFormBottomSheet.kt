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
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.beelditechtest.domain.model.Equipment
import com.example.beelditechtest.domain.model.EquipmentStatus
import com.example.beelditechtest.presentation.viewmodel.EquipmentFormViewModel
import com.example.beelditechtest.ui.theme.primaryColor
import com.example.beelditechtest.ui.theme.screenBackground

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
            TextField(
                value = state.name,
                onValueChange = { viewModel.onNameChange(it) },
                placeholder = { Text("Nom *", color = Color(0xFF9CA3AF)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .clip(RoundedCornerShape(12.dp)),
                isError = state.nameError != null,
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = screenBackground,
                    unfocusedContainerColor = screenBackground,
                    errorContainerColor = screenBackground,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent,
                ),
            )
            state.nameError?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(start = 4.dp, top = 4.dp),
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Champ Marque
            TextField(
                value = state.brand,
                onValueChange = { viewModel.onBrandChange(it) },
                placeholder = { Text("Marque *", color = Color(0xFF9CA3AF)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .clip(RoundedCornerShape(12.dp)),
                isError = state.brandError != null,
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = screenBackground,
                    unfocusedContainerColor = screenBackground,
                    errorContainerColor = screenBackground,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent,
                ),
            )
            state.brandError?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(start = 4.dp, top = 4.dp),
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Champ Modèle
            TextField(
                value = state.model,
                onValueChange = { viewModel.onModelChange(it) },
                placeholder = { Text("Modèle *", color = Color(0xFF9CA3AF)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .clip(RoundedCornerShape(12.dp)),
                isError = state.modelError != null,
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = screenBackground,
                    unfocusedContainerColor = screenBackground,
                    errorContainerColor = screenBackground,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent,
                ),
            )
            state.modelError?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(start = 4.dp, top = 4.dp),
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Champ Numéro de série
            TextField(
                value = state.serialNumber,
                onValueChange = { viewModel.onSerialNumberChange(it) },
                placeholder = { Text("Numéro de série *", color = Color(0xFF9CA3AF)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .clip(RoundedCornerShape(12.dp)),
                isError = state.serialNumberError != null,
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = screenBackground,
                    unfocusedContainerColor = screenBackground,
                    errorContainerColor = screenBackground,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent,
                ),
            )
            state.serialNumberError?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(start = 4.dp, top = 4.dp),
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Champ Étage
            TextField(
                value = state.floor,
                onValueChange = { viewModel.onFloorChange(it) },
                placeholder = { Text("Étage", color = Color(0xFF9CA3AF)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .clip(RoundedCornerShape(12.dp)),
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = screenBackground,
                    unfocusedContainerColor = screenBackground,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
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
                    val isSelected = state.status == status

                    FilterChip(
                        selected = isSelected,
                        onClick = { viewModel.onStatusChange(status) },
                        label = {
                            Text(
                                text = label,
                                fontSize = 12.sp,
                                color = if (isSelected) color else Color(0xFF6B7280),
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            containerColor = screenBackground,
                            selectedContainerColor = color.copy(alpha = 0.15f),
                            labelColor = Color(0xFF6B7280),
                            selectedLabelColor = color,
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            borderColor = Color.Transparent,
                            selectedBorderColor = color.copy(alpha = 0.3f),
                            borderWidth = 0.dp,
                            selectedBorderWidth = 1.dp,
                            enabled = true,
                            selected = isSelected,
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
                Button(
                    onClick = {
                        viewModel.resetState()
                        onDismiss()
                    },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = screenBackground,
                        contentColor = Color(0xFF6B7280),
                    ),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 0.dp,
                        pressedElevation = 0.dp,
                    ),
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
