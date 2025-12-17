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
}
