package com.example.beelditechtest.domain.usecase

import com.example.beelditechtest.domain.model.EquipmentStats
import com.example.beelditechtest.domain.repository.BuildingRepository
import com.example.beelditechtest.domain.repository.EquipmentRepository
import javax.inject.Inject

/**
 * Statistiques globales du parc immobilier pour le dashboard
 */
data class ParkStats(
    val buildingCount: Int,
    val equipmentStats: EquipmentStats,
)

/**
 * Récupère les statistiques globales du parc immobilier
 * (nombre de bâtiments, nombre d'équipements, taux de complétion moyen, etc.)
 */
class GetParkStatsUseCase @Inject constructor(
    private val buildingRepository: BuildingRepository,
    private val equipmentRepository: EquipmentRepository,
) {
    suspend operator fun invoke(): ParkStats {
        val buildings = buildingRepository.getBuildings()
        val equipments = equipmentRepository.getEquipments()

        return ParkStats(
            buildingCount = buildings.size,
            equipmentStats = EquipmentStats.fromEquipments(equipments),
        )
    }
}
