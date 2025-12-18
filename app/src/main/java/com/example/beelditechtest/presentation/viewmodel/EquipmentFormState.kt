package com.example.beelditechtest.presentation.viewmodel

import com.example.beelditechtest.domain.model.Equipment
import com.example.beelditechtest.domain.model.EquipmentStatus

data class EquipmentFormState(
    val id: String? = null,
    val name: String = "",
    val brand: String = "",
    val model: String = "",
    val serialNumber: String = "",
    val floor: String = "",
    val status: EquipmentStatus = EquipmentStatus.OK,
    val buildingId: String = "building-1",
    val imagePath: String? = null,
    val isEditMode: Boolean = false,
    val isLoading: Boolean = false,
    val isSaved: Boolean = false,
    val isDeleted: Boolean = false,
    val error: String? = null,
    val nameError: String? = null,
    val brandError: String? = null,
    val modelError: String? = null,
    val serialNumberError: String? = null,
) {
    val isFormValid: Boolean
        get() = name.isNotBlank() && brand.isNotBlank() && model.isNotBlank() && serialNumber.isNotBlank()

    fun toEquipment(): Equipment {
        return Equipment(
            id = id ?: "eq-${System.currentTimeMillis()}",
            name = name.trim(),
            brand = brand.trim(),
            model = model.trim(),
            serialNumber = serialNumber.trim(),
            floor = floor.trim().ifBlank { "RDC" },
            status = status,
            completionRate = if (status == EquipmentStatus.OK) 100 else 50,
            defectCount = if (status == EquipmentStatus.DEFECT) 1 else 0,
            updatedAt = System.currentTimeMillis(),
            buildingId = buildingId,
            imagePath = imagePath,
        )
    }

    companion object {
        fun fromEquipment(equipment: Equipment): EquipmentFormState {
            return EquipmentFormState(
                id = equipment.id,
                name = equipment.name,
                brand = equipment.brand,
                model = equipment.model,
                serialNumber = equipment.serialNumber,
                floor = equipment.floor,
                status = equipment.status,
                buildingId = equipment.buildingId,
                imagePath = equipment.imagePath,
                isEditMode = true,
            )
        }
    }
}
