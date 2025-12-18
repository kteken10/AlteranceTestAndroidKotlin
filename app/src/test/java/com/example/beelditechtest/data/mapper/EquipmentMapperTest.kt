package com.example.beelditechtest.data.mapper

import com.example.beelditechtest.data.datasource.local.model.EquipmentEntity
import com.example.beelditechtest.domain.model.Equipment
import com.example.beelditechtest.domain.model.EquipmentStatus
import org.junit.Assert.assertEquals
import org.junit.Test

class EquipmentMapperTest {
    private val entity = EquipmentEntity(
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
        imagePath = "img.png"
    )

    private val domain = Equipment(
        id = "1",
        name = "Test Equip",
        brand = "BrandX",
        model = "ModelY",
        serialNumber = "SN123",
        floor = "1",
        status = EquipmentStatus.OK,
        completionRate = 100,
        defectCount = 0,
        updatedAt = 123456789L,
        buildingId = "B1",
        imagePath = "img.png"
    )

    @Test
    fun `toDomain maps entity to domain`() {
        val result = entity.toDomain()
        assertEquals(domain, result)
    }

    @Test
    fun `toEntity maps domain to entity`() {
        val result = domain.toEntity()
        assertEquals(entity, result)
    }

    @Test
    fun `list toDomain maps list of entities`() {
        val entities = listOf(entity, entity.copy(id = "2"))
        val expected = listOf(domain, domain.copy(id = "2"))
        assertEquals(expected, entities.toDomain())
    }
}
