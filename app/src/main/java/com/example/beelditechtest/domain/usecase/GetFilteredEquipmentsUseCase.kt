package com.example.beelditechtest.domain.usecase

import com.example.beelditechtest.domain.model.Equipment
import com.example.beelditechtest.domain.model.EquipmentFilter
import com.example.beelditechtest.domain.repository.EquipmentRepository
import javax.inject.Inject

/**
 * Récupère les équipements filtrés selon les critères spécifiés
 */
class GetFilteredEquipmentsUseCase @Inject constructor(
    private val repository: EquipmentRepository,
) {
    suspend operator fun invoke(filter: EquipmentFilter): List<Equipment> {
        val allEquipments = repository.getEquipments()
        return filter.apply(allEquipments)
    }
}
