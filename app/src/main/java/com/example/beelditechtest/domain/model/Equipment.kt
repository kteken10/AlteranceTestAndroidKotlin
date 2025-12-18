package com.example.beelditechtest.domain.model

import com.example.beelditechtest.domain.model.EquipmentStatus

/**
 * Équipement technique appartenant à un bâtiment
 */
data class Equipment(
    val id: String,
    val name: String,
    val brand: String,
    val model: String,
    val serialNumber: String,
    val floor: String,
    val status: EquipmentStatus,
    val completionRate: Int,
    val defectCount: Int,
    val updatedAt: Long,
    val buildingId: String,
    val imagePath: String? = null,
)