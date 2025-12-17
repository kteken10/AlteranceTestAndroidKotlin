package com.example.beelditechtest.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beelditechtest.domain.model.Equipment
import com.example.beelditechtest.domain.model.EquipmentStatus
import com.example.beelditechtest.domain.usecase.CreateEquipmentUseCase
import com.example.beelditechtest.domain.usecase.DeleteEquipmentUseCase
import com.example.beelditechtest.domain.usecase.UpdateEquipmentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EquipmentFormViewModel @Inject constructor(
    private val createEquipmentUseCase: CreateEquipmentUseCase,
    private val updateEquipmentUseCase: UpdateEquipmentUseCase,
    private val deleteEquipmentUseCase: DeleteEquipmentUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(EquipmentFormState())
    val state: StateFlow<EquipmentFormState> = _state.asStateFlow()

    fun initForCreate() {
        _state.value = EquipmentFormState()
    }

    fun initForEdit(equipment: Equipment) {
        _state.value = EquipmentFormState.fromEquipment(equipment)
    }

    fun onNameChange(name: String) {
        _state.value = _state.value.copy(
            name = name,
            nameError = if (name.isBlank()) "Le nom est requis" else null,
        )
    }

    fun onBrandChange(brand: String) {
        _state.value = _state.value.copy(
            brand = brand,
            brandError = if (brand.isBlank()) "La marque est requise" else null,
        )
    }

    fun onModelChange(model: String) {
        _state.value = _state.value.copy(
            model = model,
            modelError = if (model.isBlank()) "Le modèle est requis" else null,
        )
    }

    fun onSerialNumberChange(serialNumber: String) {
        _state.value = _state.value.copy(
            serialNumber = serialNumber,
            serialNumberError = if (serialNumber.isBlank()) "Le numéro de série est requis" else null,
        )
    }

    fun onFloorChange(floor: String) {
        _state.value = _state.value.copy(floor = floor)
    }

    fun onStatusChange(status: EquipmentStatus) {
        _state.value = _state.value.copy(status = status)
    }

    fun onSubmit() {
        val currentState = _state.value

        // Validation
        val nameError = if (currentState.name.isBlank()) "Le nom est requis" else null
        val brandError = if (currentState.brand.isBlank()) "La marque est requise" else null
        val modelError = if (currentState.model.isBlank()) "Le modèle est requis" else null
        val serialNumberError = if (currentState.serialNumber.isBlank()) "Le numéro de série est requis" else null

        if (nameError != null || brandError != null || modelError != null || serialNumberError != null) {
            _state.value = currentState.copy(
                nameError = nameError,
                brandError = brandError,
                modelError = modelError,
                serialNumberError = serialNumberError,
            )
            return
        }

        viewModelScope.launch {
            _state.value = currentState.copy(isLoading = true, error = null)

            val equipment = currentState.toEquipment()
            val result = if (currentState.isEditMode) {
                updateEquipmentUseCase(equipment)
            } else {
                createEquipmentUseCase(equipment)
            }

            result.fold(
                onSuccess = {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        isSaved = true,
                    )
                },
                onFailure = { error ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = error.message ?: "Une erreur est survenue",
                    )
                },
            )
        }
    }

    fun onDelete() {
        val currentState = _state.value
        val equipmentId = currentState.id ?: return

        viewModelScope.launch {
            _state.value = currentState.copy(isLoading = true, error = null)

            deleteEquipmentUseCase(equipmentId).fold(
                onSuccess = {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        isDeleted = true,
                    )
                },
                onFailure = { error ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = error.message ?: "Une erreur est survenue",
                    )
                },
            )
        }
    }

    fun resetState() {
        _state.value = EquipmentFormState()
    }
}
