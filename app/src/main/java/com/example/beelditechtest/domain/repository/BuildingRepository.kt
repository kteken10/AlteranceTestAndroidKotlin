package com.example.beelditechtest.domain.repository

import com.example.beelditechtest.domain.model.Building

interface BuildingRepository {
    suspend fun getBuildings(): List<Building>
    suspend fun getBuildingById(id: String): Building?
}
