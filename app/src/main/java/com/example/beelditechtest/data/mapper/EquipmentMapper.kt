package com.example.beelditechtest.data.mapper

import com.example.beelditechtest.data.datasource.local.model.EquipmentEntity
import com.example.beelditechtest.domain.model.Equipment

fun EquipmentEntity.toDomain(): Equipment {
    return Equipment(
        id = id,
        name = name,
        brand = brand,
        model = model,
        serialNumber = serialNumber,
    )
}

fun List<EquipmentEntity>.toDomain(): List<Equipment> {
    return map { it.toDomain() }
}
