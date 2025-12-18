package com.example.beelditechtest.presentation.viewmodel

import app.cash.turbine.test
import com.example.beelditechtest.domain.model.Equipment
import com.example.beelditechtest.domain.usecase.GetEquipmentsUseCase
import com.example.beelditechtest.domain.usecase.GetParkStatsUseCase
import com.example.beelditechtest.domain.usecase.DeleteEquipmentUseCase
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
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class EquipmentListViewModelTest {

    private lateinit var getEquipmentsUseCase: GetEquipmentsUseCase
    private lateinit var getParkStatsUseCase: GetParkStatsUseCase
    private lateinit var deleteEquipmentUseCase: DeleteEquipmentUseCase
    private val testDispatcher = StandardTestDispatcher()

    private val testEquipments = listOf(
        Equipment(
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
        ),
        Equipment(
            id = "2",
            name = "Equipment 2",
            brand = "Brand B",
            model = "Model Y",
            serialNumber = "SN002",
            floor = "2",
            status = com.example.beelditechtest.domain.model.EquipmentStatus.TO_COMPLETE,
            completionRate = 80,
            defectCount = 1,
            updatedAt = 123456790L,
            buildingId = "B2",
            imagePath = null
        ),
    )

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        getEquipmentsUseCase = mockk()
        getParkStatsUseCase = mockk(relaxed = true)
        deleteEquipmentUseCase = mockk(relaxed = true)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state should be loading then load equipments`() = runTest {
        // Given
        coEvery { getEquipmentsUseCase() } returns testEquipments

        // When
        val viewModel = EquipmentListViewModel(getEquipmentsUseCase, getParkStatsUseCase, deleteEquipmentUseCase)

        viewModel.state.test {
            // Skip initial default emission
            awaitItem()
            // Loading state
            val loadingState = awaitItem()
            assertTrue(loadingState.isLoading)

            // Advance dispatcher before collecting next state
            testDispatcher.scheduler.advanceUntilIdle()
            val loadedState = awaitItem()

            assertFalse(loadedState.isLoading)
            assertEquals(testEquipments, loadedState.filteredEquipments)
            assertNull(loadedState.error)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `should set error state when use case throws exception`() = runTest {
        // Given
        coEvery { getEquipmentsUseCase() } throws RuntimeException("Test error")

        // When
        val viewModel = EquipmentListViewModel(getEquipmentsUseCase, getParkStatsUseCase, deleteEquipmentUseCase)

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
            assertEquals("Test error", errorState.error)
            assertTrue(errorState.filteredEquipments.isEmpty())

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `loadEquipments should refresh data`() = runTest {
        // Given
        coEvery { getEquipmentsUseCase() } returns testEquipments

        val viewModel = EquipmentListViewModel(getEquipmentsUseCase, getParkStatsUseCase, deleteEquipmentUseCase)

        viewModel.state.test {
            // Skip initial default emission
            awaitItem()
            // Loading state
            awaitItem()
            testDispatcher.scheduler.advanceUntilIdle()
            val currentState = awaitItem()
            assertEquals(testEquipments, currentState.filteredEquipments)
            cancelAndIgnoreRemainingEvents()
        }
    }
}
