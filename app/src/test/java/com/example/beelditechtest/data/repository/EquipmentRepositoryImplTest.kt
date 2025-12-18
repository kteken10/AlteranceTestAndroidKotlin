package com.example.beelditechtest.data.repository

import com.example.beelditechtest.data.datasource.local.EquipmentLocalDataSource
import com.example.beelditechtest.data.datasource.local.model.EquipmentEntity
import com.example.beelditechtest.data.mapper.toDomain
import com.example.beelditechtest.data.mapper.toEntity
import com.example.beelditechtest.domain.model.Equipment
import com.example.beelditechtest.domain.model.EquipmentStatus
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class EquipmentRepositoryImplTest {
    private lateinit var localDataSource: EquipmentLocalDataSource
    private lateinit var repository: EquipmentRepositoryImpl

    private val equipmentEntity = EquipmentEntity(
        id = "1",
        name = "Test Equip",
        brand = "BrandX",
        model = "ModelY",
        serialNumber = "SN123",
        floor = "1",
        status = "OK",
        completionRate = 100,
        defectCount = 0,
        updatedAt = 123456789L,
        buildingId = "B1",
        imagePath = null
    )
    private val equipment = equipmentEntity.toDomain()

    @Before
    fun setUp() {
        localDataSource = mockk(relaxed = true)
        repository = EquipmentRepositoryImpl(localDataSource)
    }

    @Test
    fun `getEquipments returns mapped equipments`() = runTest {
        coEvery { localDataSource.getEquipments() } returns listOf(equipmentEntity)
        val result = repository.getEquipments()
        assertEquals(listOf(equipment), result)
    }

    @Test
    fun `getEquipmentById returns correct equipment`() = runTest {
        coEvery { localDataSource.getEquipments() } returns listOf(equipmentEntity)
        val result = repository.getEquipmentById("1")
        assertEquals(equipment, result)
    }

    @Test
    fun `getEquipmentsByBuildingId filters by building`() = runTest {
        coEvery { localDataSource.getEquipments() } returns listOf(equipmentEntity)
        val result = repository.getEquipmentsByBuildingId("B1")
        assertEquals(listOf(equipment), result)
    }

    @Test
    fun `addEquipment calls localDataSource`() = runTest {
        coEvery { localDataSource.addEquipment(any()) } returns Unit
        repository.addEquipment(equipment)
        coVerify { localDataSource.addEquipment(equipment.toEntity()) }
    }

    @Test
    fun `updateEquipment calls localDataSource`() = runTest {
        coEvery { localDataSource.updateEquipment(any()) } returns Unit
        repository.updateEquipment(equipment)
        coVerify { localDataSource.updateEquipment(equipment.toEntity()) }
    }

    @Test
    fun `deleteEquipment calls localDataSource`() = runTest {
        coEvery { localDataSource.deleteEquipment(any()) } returns Unit
        repository.deleteEquipment("1")
        coVerify { localDataSource.deleteEquipment("1") }
    }
}
