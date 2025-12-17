package com.example.beelditechtest.data.datasource.local.model

data class EquipmentEntity(
    val id: String,
    val name: String,
    val brand: String,
    val model: String,
    val serialNumber: String,
    val floor: String,
    val status: String,
    val completionRate: Int,
    val defectCount: Int,
    val updatedAt: Long,
    val buildingId: String,
)
