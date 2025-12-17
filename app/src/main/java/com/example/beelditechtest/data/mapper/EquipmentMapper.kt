package com.example.beelditechtest.data.mapper

import com.example.beelditechtest.data.datasource.local.model.EquipmentEntity
import com.example.beelditechtest.domain.model.Equipment
import com.example.beelditechtest.domain.model.EquipmentStatus

fun EquipmentEntity.toDomain(): Equipment {
    return Equipment(
        id = id,
        name = name,
        brand = brand,
        model = model,
        serialNumber = serialNumber,
        floor = floor,
        status = EquipmentStatus.valueOf(status),
        completionRate = completionRate,
        defectCount = defectCount,
        updatedAt = updatedAt,
        buildingId = buildingId,
    )
}

fun List<EquipmentEntity>.toDomain(): List<Equipment> {
    return map { it.toDomain() }
}
