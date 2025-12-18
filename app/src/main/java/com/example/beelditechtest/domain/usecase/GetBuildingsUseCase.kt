package com.example.beelditechtest.domain.usecase

import com.example.beelditechtest.domain.model.Building
import com.example.beelditechtest.domain.repository.BuildingRepository
import javax.inject.Inject

/**
 * Récupère la liste de tous les bâtiments du parc immobilier
 */
class GetBuildingsUseCase @Inject constructor(
    private val repository: BuildingRepository,
) {
    suspend operator fun invoke(): List<Building> {
        return repository.getBuildings()
    }
}
