package com.example.beelditechtest.domain.model

/**
 * Bâtiment avec ses statistiques d'équipements calculées
 * Utilisé pour l'affichage dans le dashboard
 */
data class BuildingWithStats(
    val building: Building,
    val equipmentCount: Int,
    val stats: EquipmentStats,
)
