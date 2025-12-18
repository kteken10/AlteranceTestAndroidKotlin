package com.example.beelditechtest.domain.repository

import com.example.beelditechtest.domain.model.Equipment

interface EquipmentRepository {
    suspend fun getEquipments(): List<Equipment>
    suspend fun getEquipmentById(id: String): Equipment?
    suspend fun getEquipmentsByBuildingId(buildingId: String): List<Equipment>
    suspend fun addEquipment(equipment: Equipment)
    suspend fun updateEquipment(equipment: Equipment)
    suspend fun deleteEquipment(equipmentId: String)
}
