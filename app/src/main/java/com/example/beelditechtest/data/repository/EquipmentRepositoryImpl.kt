package com.example.beelditechtest.data.repository

import com.example.beelditechtest.data.datasource.local.EquipmentLocalDataSource
import com.example.beelditechtest.data.mapper.toDomain
import com.example.beelditechtest.domain.model.Equipment
import com.example.beelditechtest.domain.repository.EquipmentRepository

class EquipmentRepositoryImpl(
    private val localDataSource: EquipmentLocalDataSource,
) : EquipmentRepository {

    override suspend fun getEquipments(): List<Equipment> {
        return localDataSource.getEquipments().toDomain()
    }
}
