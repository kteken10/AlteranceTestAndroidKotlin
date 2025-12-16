package com.example.beelditechtest.domain.usecase

import com.example.beelditechtest.domain.model.Equipment
import com.example.beelditechtest.domain.repository.EquipmentRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

class GetEquipmentByIdUseCaseTest {

    private lateinit var repository: EquipmentRepository
    private lateinit var useCase: GetEquipmentByIdUseCase

    private val testEquipments = listOf(
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

    @Before
    fun setUp() {
        repository = mockk()
        useCase = GetEquipmentByIdUseCase(repository)
    }

    @Test
    fun `invoke should return equipment when id exists`() = runTest {
        // Given
        coEvery { repository.getEquipments() } returns testEquipments

        // When
        val result = useCase("1")

        // Then
        assertEquals(testEquipments[0], result)
    }

    @Test
    fun `invoke should return null when id does not exist`() = runTest {
        // Given
        coEvery { repository.getEquipments() } returns testEquipments

        // When
        val result = useCase("999")

        // Then
        assertNull(result)
    }

    @Test
    fun `invoke should return null when repository returns empty list`() = runTest {
        // Given
        coEvery { repository.getEquipments() } returns emptyList()

        // When
        val result = useCase("1")

        // Then
        assertNull(result)
    }
}
