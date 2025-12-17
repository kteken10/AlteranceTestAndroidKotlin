package com.example.beelditechtest.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beelditechtest.domain.model.Equipment
import com.example.beelditechtest.domain.usecase.DeleteEquipmentUseCase
import com.example.beelditechtest.domain.usecase.GetEquipmentsUseCase
import com.example.beelditechtest.domain.usecase.GetParkStatsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EquipmentListViewModel @Inject constructor(
    private val getEquipmentsUseCase: GetEquipmentsUseCase,
    private val getParkStatsUseCase: GetParkStatsUseCase,
    private val deleteEquipmentUseCase: DeleteEquipmentUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(EquipmentListState())
    val state: StateFlow<EquipmentListState> = _state.asStateFlow()

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            try {
                val equipments = getEquipmentsUseCase()
                val parkStats = getParkStatsUseCase()
                _state.value = _state.value.copy(
                    equipments = equipments,
                    filteredEquipments = filterEquipments(equipments, _state.value.searchQuery),
                    parkStats = parkStats,
                    isLoading = false,
                    error = null,
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.message ?: "Erreur inconnue",
                )
            }
        }
    }

    fun onSearchQueryChange(query: String) {
        _state.value = _state.value.copy(
            searchQuery = query,
            filteredEquipments = filterEquipments(_state.value.equipments, query),
        )
    }

    fun openBottomSheetForCreate() {
        _state.value = _state.value.copy(
            isBottomSheetOpen = true,
            selectedEquipment = null,
        )
    }

    fun openBottomSheetForEdit(equipment: Equipment) {
        _state.value = _state.value.copy(
            isBottomSheetOpen = true,
            selectedEquipment = equipment,
        )
    }

    fun closeBottomSheet() {
        _state.value = _state.value.copy(
            isBottomSheetOpen = false,
            selectedEquipment = null,
        )
    }

    fun onEquipmentSaved() {
        closeBottomSheet()
        loadData()
    }

    fun onEquipmentDeleted() {
        closeBottomSheet()
        loadData()
    }

    fun showDeleteConfirmation(equipment: Equipment) {
        _state.value = _state.value.copy(
            showDeleteConfirmation = true,
            equipmentToDelete = equipment,
        )
    }

    fun hideDeleteConfirmation() {
        _state.value = _state.value.copy(
            showDeleteConfirmation = false,
            equipmentToDelete = null,
        )
    }

    fun confirmDelete() {
        val equipment = _state.value.equipmentToDelete ?: return
        viewModelScope.launch {
            deleteEquipmentUseCase(equipment.id).fold(
                onSuccess = {
                    hideDeleteConfirmation()
                    loadData()
                },
                onFailure = {
                    hideDeleteConfirmation()
                    _state.value = _state.value.copy(
                        error = it.message ?: "Erreur lors de la suppression",
                    )
                },
            )
        }
    }

    private fun filterEquipments(
        equipments: List<Equipment>,
        query: String,
    ): List<Equipment> {
        if (query.isBlank()) return equipments
        val lowerQuery = query.lowercase().trim()
        return equipments.filter { equipment ->
            equipment.name.lowercase().contains(lowerQuery) ||
                equipment.brand.lowercase().contains(lowerQuery) ||
                equipment.model.lowercase().contains(lowerQuery) ||
                equipment.serialNumber.lowercase().contains(lowerQuery) ||
                equipment.floor.lowercase().contains(lowerQuery)
        }
    }
}
