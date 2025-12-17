package com.example.beelditechtest.data.repository

import com.example.beelditechtest.data.datasource.local.EquipmentLocalDataSource
import com.example.beelditechtest.data.mapper.toDomain
import com.example.beelditechtest.data.mapper.toEntity
import com.example.beelditechtest.domain.model.Equipment
import com.example.beelditechtest.domain.repository.EquipmentRepository
import javax.inject.Inject

class EquipmentRepositoryImpl @Inject constructor(
    private val localDataSource: EquipmentLocalDataSource,
) : EquipmentRepository {

    override suspend fun getEquipments(): List<Equipment> {
        return localDataSource.getEquipments().toDomain()
    }

    override suspend fun getEquipmentById(id: String): Equipment? {
        return localDataSource.getEquipments()
            .toDomain()
            .find { it.id == id }
    }

    override suspend fun getEquipmentsByBuildingId(buildingId: String): List<Equipment> {
        return localDataSource.getEquipments()
            .toDomain()
            .filter { it.buildingId == buildingId }
    }

    override suspend fun addEquipment(equipment: Equipment) {
        localDataSource.addEquipment(equipment.toEntity())
    }

    override suspend fun updateEquipment(equipment: Equipment) {
        localDataSource.updateEquipment(equipment.toEntity())
    }

    override suspend fun deleteEquipment(equipmentId: String) {
        localDataSource.deleteEquipment(equipmentId)
    }
}
