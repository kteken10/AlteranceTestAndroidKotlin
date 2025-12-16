package com.example.beelditechtest.presentation.viewmodel

import com.example.beelditechtest.domain.model.Equipment

data class EquipmentDetailState(
    val equipment: Equipment? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)
