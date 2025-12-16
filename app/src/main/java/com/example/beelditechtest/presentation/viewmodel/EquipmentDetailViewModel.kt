package com.example.beelditechtest.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beelditechtest.domain.usecase.GetEquipmentByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EquipmentDetailViewModel @Inject constructor(
    private val getEquipmentByIdUseCase: GetEquipmentByIdUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _state = MutableStateFlow(EquipmentDetailState())
    val state: StateFlow<EquipmentDetailState> = _state.asStateFlow()

    private val equipmentId: String = checkNotNull(savedStateHandle["equipmentId"])

    init {
        loadEquipment()
    }

    fun loadEquipment() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            try {
                val equipment = getEquipmentByIdUseCase(equipmentId)
                _state.value = _state.value.copy(
                    equipment = equipment,
                    isLoading = false,
                    error = if (equipment == null) "Équipement non trouvé" else null,
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
