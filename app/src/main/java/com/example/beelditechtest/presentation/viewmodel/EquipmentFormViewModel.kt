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
        // Limite à 25 caractères
        val limitedBrand = brand.take(25)
        val error = validateBrand(limitedBrand)
        _state.value = _state.value.copy(
            brand = limitedBrand,
            brandError = error,
        )
    }

    fun onModelChange(model: String) {
        // Limite à 50 caractères
        val limitedModel = model.take(50)
        val error = validateModel(limitedModel)
        _state.value = _state.value.copy(
            model = limitedModel,
            modelError = error,
        )
    }

    fun onSerialNumberChange(serialNumber: String) {
        // Convertir en majuscule et supprimer les espaces, limiter à 50 caractères
        val formattedSerial = serialNumber
            .uppercase()
            .replace(" ", "")
            .take(50)
        val error = validateSerialNumber(formattedSerial)
        _state.value = _state.value.copy(
            serialNumber = formattedSerial,
            serialNumberError = error,
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

        // Validation complète
        val nameError = if (currentState.name.isBlank()) "Le nom est requis" else null
        val brandError = validateBrand(currentState.brand)
        val modelError = validateModel(currentState.model)
        val serialNumberError = validateSerialNumber(currentState.serialNumber)

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

    // Validation de la marque: commence par majuscule, max 25 caractères
    private fun validateBrand(brand: String): String? {
        return when {
            brand.isBlank() -> "La marque est requise"
            brand.first().isLowerCase() -> "La marque doit commencer par une majuscule"
            brand.length > 25 -> "La marque ne doit pas dépasser 25 caractères"
            else -> null
        }
    }

    // Validation du modèle: max 50 caractères
    private fun validateModel(model: String): String? {
        return when {
            model.isBlank() -> "Le modèle est requis"
            model.length > 50 -> "Le modèle ne doit pas dépasser 50 caractères"
            else -> null
        }
    }

    // Validation du numéro de série: chiffres, lettres, tirets uniquement, majuscules, pas d'espaces, max 50 caractères
    private fun validateSerialNumber(serialNumber: String): String? {
        val validPattern = Regex("^[A-Z0-9-]+$")
        return when {
            serialNumber.isBlank() -> "Le numéro de série est requis"
            serialNumber.contains(" ") -> "Le numéro de série ne peut pas contenir d'espaces"
            !validPattern.matches(serialNumber) -> "Le numéro de série ne peut contenir que des lettres majuscules, chiffres et tirets"
            serialNumber.length > 50 -> "Le numéro de série ne doit pas dépasser 50 caractères"
            else -> null
        }
    }
}
