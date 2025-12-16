package com.example.beelditechtest.presentation.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.beelditechtest.presentation.ui.component.EquipmentItem
import com.example.beelditechtest.presentation.viewmodel.EquipmentListViewModel
import com.example.beelditechtest.ui.theme.screenBackground

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EquipmentListScreen(
    viewModel: EquipmentListViewModel,
    onEquipmentClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Équipements") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = screenBackground,
                ),
            )
        },
        containerColor = screenBackground,
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
                        contentPadding = paddingValues,
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
