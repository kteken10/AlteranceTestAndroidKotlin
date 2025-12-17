package com.example.beelditechtest.presentation.ui.screen

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.beelditechtest.R
import com.example.beelditechtest.presentation.ui.component.EquipmentItem
import com.example.beelditechtest.presentation.ui.component.EquipmentListTopAppBar
import com.example.beelditechtest.presentation.ui.component.KpiCard
import com.example.beelditechtest.presentation.viewmodel.EquipmentListViewModel
import com.example.beelditechtest.ui.theme.screenBackground

@Composable
fun EquipmentListScreen(
    viewModel: EquipmentListViewModel,
    onEquipmentClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            EquipmentListTopAppBar(
                userAvatarResId = R.drawable.avatar_user,
                onAvatarClick = { /* TODO: Navigate to profile */ },
            )
        },
        containerColor = screenBackground,
        modifier = modifier,
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            // Titre Dashboard
            Text(
                text = "Dashboard",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 16.dp, bottom = 8.dp),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
            )

            // KPI Cards
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
                        icon = painterResource(id = R.drawable.ic_chart),
                        iconTint = Color(0xFF3B82F6),
                    )
                    KpiCard(
                        title = "Conformes",
                        value = stats.equipmentStats.okCount.toString(),
                        icon = painterResource(id = R.drawable.ic_check_circle),
                        iconTint = Color(0xFF10B981),
                    )
                    KpiCard(
                        title = "À compléter",
                        value = stats.equipmentStats.toCompleteCount.toString(),
                        icon = painterResource(id = R.drawable.ic_warning),
                        iconTint = Color(0xFFF59E0B),
                    )
                    KpiCard(
                        title = "En défaut",
                        value = stats.equipmentStats.defectCount.toString(),
                        icon = painterResource(id = R.drawable.ic_error),
                        iconTint = Color(0xFFEF4444),
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Section Équipements
            Text(
                text = "Équipements",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, bottom = 8.dp),
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
            )

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
                    else -> {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            items(state.equipments) { equipment ->
                                EquipmentItem(
                                    equipment = equipment,
                                    onClick = { onEquipmentClick(equipment.id) },
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
