package com.example.beelditechtest.presentation.ui.screen

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme  // ← AJOUTER CET IMPORT
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.beelditechtest.R
import com.example.beelditechtest.domain.model.Equipment
import com.example.beelditechtest.domain.model.EquipmentStatus
import com.example.beelditechtest.presentation.ui.component.StatusTag
import com.example.beelditechtest.presentation.viewmodel.EquipmentDetailViewModel

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EquipmentDetailScreen(
    viewModel: EquipmentDetailViewModel,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(
                        onClick = onNavigateBack,
                        modifier = Modifier
                            .padding(8.dp)
                            .background(Color.White, CircleShape)
                            .size(40.dp),
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Retour",
                            tint = Color.Black,
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFF5F5F5),
                ),
            )
        },
        containerColor = Color(0xFFF5F5F5),
        modifier = modifier,
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            when {
                state.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = MaterialTheme.colorScheme.primary,
                    )
                }
                state.error != null -> {
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_defect),
                            contentDescription = null,
                            modifier = Modifier.size(48.dp),
                            tint = Color(0xFFF44336),
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = state.error ?: "Erreur",
                            color = MaterialTheme.colorScheme.error,
                        )
                    }
                }
                state.equipment != null -> {
                    EquipmentDetailContent(
                        equipment = state.equipment!!,
                        modifier = Modifier.fillMaxSize(),
                    )
                }
            }
        }
    }
}

@Composable
private fun EquipmentDetailContent(
    equipment: Equipment,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    // Charger l'image depuis les assets
    val imageBitmap = remember(equipment.imagePath) {
        equipment.imagePath?.let { path ->
            try {
                context.assets.open(path).use { inputStream ->
                    BitmapFactory.decodeStream(inputStream)?.asImageBitmap()
                }
            } catch (e: Exception) {
                null
            }
        }
    }

    Column(
        modifier = modifier
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        // Image de l'équipement dans une carte blanche
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 8.dp),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp),
                contentAlignment = Alignment.Center,
            ) {
                if (imageBitmap != null) {
                    Image(
                        bitmap = imageBitmap,
                        contentDescription = equipment.name,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(20.dp)),
                        contentScale = ContentScale.Crop,
                    )
                } else {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_equipment),
                        contentDescription = equipment.name,
                        modifier = Modifier.size(80.dp),
                        tint = Color(0xFF6B7280),
                    )
                }
            }
        }

        // Header Card avec infos principales
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                // Nom de l'équipement
                Text(
                    text = equipment.name,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF111827),
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Status Tag
                StatusTag(status = equipment.status)

                Spacer(modifier = Modifier.height(16.dp))

                // Barre de progression
                Column(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text(
                            text = "Complétion",
                            fontSize = 12.sp,
                            color = Color(0xFF6B7280),
                        )
                        Text(
                            text = "${equipment.completionRate}%",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF111827),
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    LinearProgressIndicator(
                        progress = { equipment.completionRate / 100f },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        color = when {
                            equipment.completionRate >= 80 -> Color(0xFF4CAF50)
                            equipment.completionRate >= 50 -> Color(0xFFFF9800)
                            else -> Color(0xFFF44336)
                        },
                        trackColor = Color(0xFFE5E7EB),
                        strokeCap = StrokeCap.Round,
                    )
                }
            }
        }

        // Section Informations générales
        DetailSection(
            title = "Informations générales",
            icon = R.drawable.ic_equipment,
            modifier = Modifier.padding(horizontal = 16.dp),
        ) {
            DetailInfoRow(label = "Marque", value = equipment.brand)
            DetailInfoRow(label = "Modèle", value = equipment.model)
            DetailInfoRow(label = "N° de série", value = equipment.serialNumber)
            DetailInfoRow(label = "ID", value = equipment.id, isSecondary = true)
        }

        // Section Localisation
        DetailSection(
            title = "Localisation",
            icon = R.drawable.ic_building,
            modifier = Modifier.padding(horizontal = 16.dp),
        ) {
            DetailInfoRow(label = "Bâtiment", value = equipment.buildingId)
            DetailInfoRow(label = "Étage", value = equipment.floor.ifEmpty { "Non renseigné" })
        }

        // Section État technique
        DetailSection(
            title = "État technique",
            icon = R.drawable.ic_defect,
            modifier = Modifier.padding(horizontal = 16.dp),
        ) {
            DetailInfoRow(
                label = "Statut",
                value = when (equipment.status) {
                    EquipmentStatus.OK -> "Conforme"
                    EquipmentStatus.TO_COMPLETE -> "À compléter"
                    EquipmentStatus.DEFECT -> "En défaut"
                },
                valueColor = when (equipment.status) {
                    EquipmentStatus.OK -> Color(0xFF4CAF50)
                    EquipmentStatus.TO_COMPLETE -> Color(0xFFFF9800)
                    EquipmentStatus.DEFECT -> Color(0xFFF44336)
                },
            )
            DetailInfoRow(
                label = "Défauts détectés",
                value = equipment.defectCount.toString(),
                valueColor = if (equipment.defectCount > 0) Color(0xFFF44336) else Color(0xFF4CAF50),
            )
            DetailInfoRow(
                label = "Dernière mise à jour",
                value = formatDate(equipment.updatedAt),
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun DetailSection(
    title: String,
    icon: Int,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
        ) {
            // Header de section
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .background(Color(0xFFF3F4F6), CircleShape),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        painter = painterResource(id = icon),
                        contentDescription = null,
                        modifier = Modifier.size(18.dp),
                        tint = Color(0xFF111827),
                    )
                }
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF111827),
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            content()
        }
    }
}

@Composable
private fun DetailInfoRow(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
    valueColor: Color = Color(0xFF111827),
    isSecondary: Boolean = false,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            color = Color(0xFF6B7280),
        )
        Text(
            text = value,
            fontSize = 14.sp,
            fontWeight = if (isSecondary) FontWeight.Normal else FontWeight.Medium,
            color = if (isSecondary) Color(0xFF9CA3AF) else valueColor,
        )
    }
}

private fun formatDate(timestamp: Long): String {
    return try {
        val sdf = SimpleDateFormat("dd MMM yyyy à HH:mm", Locale.FRANCE)
        sdf.format(Date(timestamp))
    } catch (e: Exception) {
        "Date inconnue"
    }
}