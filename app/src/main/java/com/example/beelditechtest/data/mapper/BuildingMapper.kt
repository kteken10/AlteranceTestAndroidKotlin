package com.example.beelditechtest.data.mapper

import com.example.beelditechtest.data.datasource.local.model.BuildingEntity
import com.example.beelditechtest.domain.model.Building

fun BuildingEntity.toDomain(): Building {
    return Building(
        id = id,
        name = name,
        address = address,
        city = city,
    )
}

fun List<BuildingEntity>.toDomainBuildings(): List<Building> {
    return map { it.toDomain() }
}
