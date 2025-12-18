package com.example.beelditechtest.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.example.beelditechtest.domain.model.Equipment
import com.example.beelditechtest.domain.usecase.GetEquipmentByIdUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class EquipmentDetailViewModelTest {

    private lateinit var getEquipmentByIdUseCase: GetEquipmentByIdUseCase
    private val testDispatcher = StandardTestDispatcher()

    private val testEquipment = Equipment(
        id = "1",
        name = "Equipment 1",
        brand = "Brand A",
        model = "Model X",
        serialNumber = "SN001",
        floor = "1",
        status = com.example.beelditechtest.domain.model.EquipmentStatus.OK,
        completionRate = 100,
        defectCount = 0,
        updatedAt = 123456789L,
        buildingId = "B1",
        imagePath = null
    )

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        getEquipmentByIdUseCase = mockk()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun createSavedStateHandle(equipmentId: String): SavedStateHandle {
        return SavedStateHandle(mapOf("equipmentId" to equipmentId))
    }

    @Test
    fun `should load equipment successfully`() = runTest {
        // Given
        coEvery { getEquipmentByIdUseCase("1") } returns testEquipment
        val savedStateHandle = createSavedStateHandle("1")

        // When
        val viewModel = EquipmentDetailViewModel(getEquipmentByIdUseCase, savedStateHandle)

        viewModel.state.test {
            // Skip initial default emission
            awaitItem()
            // Loading state
            val loadingState = awaitItem()
            assertTrue(loadingState.isLoading)

            // Advance dispatcher before collecting next state
            testDispatcher.scheduler.advanceUntilIdle()

            // Await the next emission after coroutine completes
            val loadedState = awaitItem()

            // Then
            assertFalse(loadedState.isLoading)
            assertNotNull(loadedState.equipment)
            assertEquals(testEquipment, loadedState.equipment)
            assertNull(loadedState.error)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `should set error when equipment not found`() = runTest {
        // Given
        coEvery { getEquipmentByIdUseCase("999") } returns null
        val savedStateHandle = createSavedStateHandle("999")

        // When
        val viewModel = EquipmentDetailViewModel(getEquipmentByIdUseCase, savedStateHandle)

        viewModel.state.test {
            // Skip initial default emission
            awaitItem()
            // Loading state
            val loadingState = awaitItem()
            assertTrue(loadingState.isLoading)

            testDispatcher.scheduler.advanceUntilIdle()
            val errorState = awaitItem()

            // Then
            assertFalse(errorState.isLoading)
            assertNull(errorState.equipment)
            assertEquals("Équipement non trouvé", errorState.error)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `should set error when use case throws exception`() = runTest {
        // Given
        coEvery { getEquipmentByIdUseCase("1") } throws RuntimeException("Network error")
        val savedStateHandle = createSavedStateHandle("1")

        // When
        val viewModel = EquipmentDetailViewModel(getEquipmentByIdUseCase, savedStateHandle)

        viewModel.state.test {
            // Skip initial default emission
            awaitItem()
            // Loading state
            val loadingState = awaitItem()
            assertTrue(loadingState.isLoading)

            testDispatcher.scheduler.advanceUntilIdle()
            val errorState = awaitItem()

            // Then
            assertFalse(errorState.isLoading)
            assertNull(errorState.equipment)
            assertEquals("Network error", errorState.error)

            cancelAndIgnoreRemainingEvents()
        }
    }
}
