package com.example.beelditechtest.domain.model

/**
 * Statistiques calculées dynamiquement pour un ensemble d'équipements
 * Ces données ne sont PAS stockées mais calculées à la demande
 */
data class EquipmentStats(
    val total: Int,
    val okCount: Int,
    val toCompleteCount: Int,
    val defectCount: Int,
    val averageCompletion: Float,
) {
    companion object {
        fun empty() = EquipmentStats(
            total = 0,
            okCount = 0,
            toCompleteCount = 0,
            defectCount = 0,
            averageCompletion = 0f,
        )

        fun fromEquipments(equipments: List<Equipment>): EquipmentStats {
            if (equipments.isEmpty()) return empty()

            return EquipmentStats(
                total = equipments.size,
                okCount = equipments.count { it.status == EquipmentStatus.OK },
                toCompleteCount = equipments.count { it.status == EquipmentStatus.TO_COMPLETE },
                defectCount = equipments.count { it.status == EquipmentStatus.DEFECT },
                averageCompletion = equipments.map { it.completionRate }.average().toFloat(),
            )
        }
    }
}
