package com.example.beelditechtest.presentation.viewmodel

import com.example.beelditechtest.domain.model.Equipment
import com.example.beelditechtest.domain.usecase.ParkStats

data class EquipmentListState(
    val equipments: List<Equipment> = emptyList(),
    val parkStats: ParkStats? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)
