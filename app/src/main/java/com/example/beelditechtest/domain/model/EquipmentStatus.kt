package com.example.beelditechtest.domain.model

/**
 * Statut d'un équipement technique
 */
enum class EquipmentStatus {
    /** Équipement en bon état, complet */
    OK,

    /** Équipement nécessitant des informations complémentaires */
    TO_COMPLETE,

    /** Équipement présentant un défaut */
    DEFECT,
}
