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
import androidx.compose.material3.MaterialTheme
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
            .verticalScroll(scrollState)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        
        // Image et titre principal
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
        ) {
            Column {
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
                            modifier = Modifier.fillMaxSize(),
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
                
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = equipment.name,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF111827),
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    StatusTag(status = equipment.status)
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Barre de progression
                    Column(modifier = Modifier.fillMaxWidth()) {
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
        }

        // Section Informations générales
        DetailSectionCard(
            title = "Informations générales",
            icon = R.drawable.ic_equipment,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                InfoItem(label = "Marque", value = equipment.brand, modifier = Modifier.weight(1f))
                InfoItem(label = "Modèle", value = equipment.model, modifier = Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                InfoItem(label = "N° de série", value = equipment.serialNumber, modifier = Modifier.weight(1f))
                InfoItem(label = "ID", value = equipment.id, modifier = Modifier.weight(1f), isSecondary = true)
            }
        }

        // Section Localisation
        DetailSectionCard(
            title = "Localisation",
            icon = R.drawable.ic_building,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                InfoItem(
                    label = "Bâtiment",
                    value = equipment.buildingId,
                    modifier = Modifier.weight(1f),
                )
                InfoItem(
                    label = "Étage",
                    value = equipment.floor.ifEmpty { "Non renseigné" },
                    modifier = Modifier.weight(1f),
                )
            }
        }

        // Section État technique
        DetailSectionCard(
            title = "État technique",
            icon = R.drawable.ic_defect,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                InfoItem(
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
                    modifier = Modifier.weight(1f),
                )
                InfoItem(
                    label = "Défauts détectés",
                    value = equipment.defectCount.toString(),
                    valueColor = if (equipment.defectCount > 0) Color(0xFFF44336) else Color(0xFF4CAF50),
                    modifier = Modifier.weight(1f),
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            InfoItem(
                label = "Dernière mise à jour",
                value = formatDate(equipment.updatedAt),
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun DetailSectionCard(
    title: String,
    icon: Int,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            // Header de section
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    tint = Color(0xFF6B7280),
                )
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF111827),
                )
            }

            content()
        }
    }
}

@Composable
private fun InfoItem(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
    valueColor: Color = Color(0xFF111827),
    isSecondary: Boolean = false,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        Text(
            text = label,
            fontSize = 12.sp,
            color = Color(0xFF6B7280),
            fontWeight = FontWeight.Normal,
        )
        Text(
            text = value,
            fontSize = 16.sp,
            fontWeight = if (isSecondary) FontWeight.Medium else FontWeight.SemiBold,
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