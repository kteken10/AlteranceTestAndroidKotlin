package com.example.beelditechtest.domain.usecase

import com.example.beelditechtest.domain.model.BuildingWithStats
import com.example.beelditechtest.domain.model.EquipmentStats
import com.example.beelditechtest.domain.repository.BuildingRepository
import com.example.beelditechtest.domain.repository.EquipmentRepository
import javax.inject.Inject

/**
 * Récupère tous les bâtiments avec leurs statistiques d'équipements calculées
 * Utilisé pour le dashboard de gestion de parc
 */
class GetBuildingsWithStatsUseCase @Inject constructor(
    private val buildingRepository: BuildingRepository,
    private val equipmentRepository: EquipmentRepository,
) {
    suspend operator fun invoke(): List<BuildingWithStats> {
        val buildings = buildingRepository.getBuildings()
        val allEquipments = equipmentRepository.getEquipments()

        return buildings.map { building ->
            val buildingEquipments = allEquipments.filter { it.buildingId == building.id }
            BuildingWithStats(
                building = building,
                equipmentCount = buildingEquipments.size,
                stats = EquipmentStats.fromEquipments(buildingEquipments),
            )
        }
    }
}
