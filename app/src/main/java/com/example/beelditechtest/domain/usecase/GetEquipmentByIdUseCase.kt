package com.example.beelditechtest.domain.usecase

import com.example.beelditechtest.domain.model.Equipment
import com.example.beelditechtest.domain.repository.EquipmentRepository

class GetEquipmentByIdUseCase(
    private val repository: EquipmentRepository,
) {
    suspend operator fun invoke(equipmentId: String): Equipment? {
        return repository.getEquipments().find { it.id == equipmentId }
    }
}
