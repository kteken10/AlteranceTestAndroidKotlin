package com.example.beelditechtest.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    private fun filterEquipments(
        equipments: List<com.example.beelditechtest.domain.model.Equipment>,
        query: String,
    ): List<com.example.beelditechtest.domain.model.Equipment> {
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
