package com.example.beelditechtest.domain.usecase

import com.example.beelditechtest.domain.model.Equipment
import com.example.beelditechtest.domain.repository.EquipmentRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetEquipmentsUseCaseTest {

    private lateinit var repository: EquipmentRepository
    private lateinit var useCase: GetEquipmentsUseCase

    @Before
    fun setUp() {
        repository = mockk()
        useCase = GetEquipmentsUseCase(repository)
    }

    @Test
    fun `invoke should return list of equipments from repository`() = runTest {
        // Given
        val expectedEquipments = listOf(
            Equipment(
                id = "1",
                name = "Equipment 1",
                brand = "Brand A",
                model = "Model X",
                serialNumber = "SN001",
            ),
            Equipment(
                id = "2",
                name = "Equipment 2",
                brand = "Brand B",
                model = "Model Y",
                serialNumber = "SN002",
            ),
        )
        coEvery { repository.getEquipments() } returns expectedEquipments

        // When
        val result = useCase()

        // Then
        assertEquals(expectedEquipments, result)
        coVerify(exactly = 1) { repository.getEquipments() }
    }

    @Test
    fun `invoke should return empty list when repository returns empty`() = runTest {
        // Given
        coEvery { repository.getEquipments() } returns emptyList()

        // When
        val result = useCase()

        // Then
        assertEquals(emptyList<Equipment>(), result)
    }

    @Test
    fun `invoke should propagate exception from repository`() = runTest {
        // Given
        val expectedException = RuntimeException("Network error")
        coEvery { repository.getEquipments() } throws expectedException

        // When / Then
        try {
            useCase()
            assert(false) { "Exception should have been thrown" }
        } catch (e: RuntimeException) {
            assertEquals("Network error", e.message)
        }
    }
}
