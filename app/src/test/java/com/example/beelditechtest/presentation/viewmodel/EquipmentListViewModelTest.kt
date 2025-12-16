package com.example.beelditechtest.presentation.viewmodel

import app.cash.turbine.test
import com.example.beelditechtest.domain.model.Equipment
import com.example.beelditechtest.domain.usecase.GetEquipmentsUseCase
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
    private val testDispatcher = StandardTestDispatcher()

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
        Dispatchers.setMain(testDispatcher)
        getEquipmentsUseCase = mockk()
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
        val viewModel = EquipmentListViewModel(getEquipmentsUseCase)

        viewModel.state.test {
            // Initial state with loading
            val loadingState = awaitItem()
            assertTrue(loadingState.isLoading)

            // After loading completes
            testDispatcher.scheduler.advanceUntilIdle()
            val loadedState = awaitItem()

            assertFalse(loadedState.isLoading)
            assertEquals(testEquipments, loadedState.equipments)
            assertNull(loadedState.error)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `should set error state when use case throws exception`() = runTest {
        // Given
        coEvery { getEquipmentsUseCase() } throws RuntimeException("Test error")

        // When
        val viewModel = EquipmentListViewModel(getEquipmentsUseCase)

        viewModel.state.test {
            // Initial state
            awaitItem()

            testDispatcher.scheduler.advanceUntilIdle()
            val errorState = awaitItem()

            // Then
            assertFalse(errorState.isLoading)
            assertEquals("Test error", errorState.error)
            assertTrue(errorState.equipments.isEmpty())

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `loadEquipments should refresh data`() = runTest {
        // Given
        coEvery { getEquipmentsUseCase() } returns testEquipments

        val viewModel = EquipmentListViewModel(getEquipmentsUseCase)
        testDispatcher.scheduler.advanceUntilIdle()

        // When
        viewModel.loadEquipments()

        viewModel.state.test {
            val currentState = awaitItem()
            assertEquals(testEquipments, currentState.equipments)

            cancelAndIgnoreRemainingEvents()
        }
    }
}
