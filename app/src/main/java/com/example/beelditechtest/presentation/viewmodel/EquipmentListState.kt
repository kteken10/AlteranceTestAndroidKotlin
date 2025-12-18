package com.example.beelditechtest.presentation.viewmodel

import com.example.beelditechtest.domain.model.Equipment
import com.example.beelditechtest.domain.usecase.ParkStats

data class EquipmentListState(
    val equipments: List<Equipment> = emptyList(),
    val filteredEquipments: List<Equipment> = emptyList(),
    val searchQuery: String = "",
    val parkStats: ParkStats? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isBottomSheetOpen: Boolean = false,
    val selectedEquipment: Equipment? = null,
    val showDeleteConfirmation: Boolean = false,
    val equipmentToDelete: Equipment? = null,
    val kpiFilter: KpiFilter = KpiFilter.ALL,
)

enum class KpiFilter {
    ALL, OK, TO_COMPLETE, DEFECT
}
