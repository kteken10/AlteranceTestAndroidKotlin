package com.example.beelditechtest.data.repository

import com.example.beelditechtest.data.datasource.local.BuildingLocalDataSource
import com.example.beelditechtest.data.mapper.toDomain
import com.example.beelditechtest.domain.model.Building
import com.example.beelditechtest.domain.repository.BuildingRepository
import javax.inject.Inject

class BuildingRepositoryImpl @Inject constructor(
    private val localDataSource: BuildingLocalDataSource,
) : BuildingRepository {

    override suspend fun getBuildings(): List<Building> {
        return localDataSource.getBuildings().map { it.toDomain() }
    }

    override suspend fun getBuildingById(id: String): Building? {
        return localDataSource.getBuildings()
            .map { it.toDomain() }
            .find { it.id == id }
    }
}
