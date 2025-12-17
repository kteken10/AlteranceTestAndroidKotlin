package com.example.beelditechtest.domain.repository

import com.example.beelditechtest.domain.model.Equipment

interface EquipmentRepository {
    suspend fun getEquipments(): List<Equipment>
    suspend fun getEquipmentById(id: String): Equipment?
    suspend fun getEquipmentsByBuildingId(buildingId: String): List<Equipment>
}
