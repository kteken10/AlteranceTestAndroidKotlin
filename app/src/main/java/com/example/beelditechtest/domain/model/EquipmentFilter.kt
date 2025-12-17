package com.example.beelditechtest.domain.model

import com.example.beelditechtest.domain.model.EquipmentStatus

/**
 * Filtre pour la recherche d'équipements
 */
data class EquipmentFilter(
    val buildingId: String? = null,
    val status: EquipmentStatus? = null,
    val brand: String? = null,
    val onlyIncomplete: Boolean = false,
) {
    fun apply(equipments: List<Equipment>): List<Equipment> {
        return equipments.filter { equipment ->
            val matchesBuilding = buildingId == null || equipment.buildingId == buildingId
            val matchesStatus = status == null || equipment.status == status
            val matchesBrand = brand == null || equipment.brand.contains(brand, ignoreCase = true)
            val matchesIncomplete = !onlyIncomplete || equipment.completionRate < 100

            matchesBuilding && matchesStatus && matchesBrand && matchesIncomplete
        }
    }

    companion object {
        fun empty() = EquipmentFilter()
    }
}
