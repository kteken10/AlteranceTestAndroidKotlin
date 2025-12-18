package com.example.beelditechtest.domain.usecase

import com.example.beelditechtest.domain.repository.EquipmentRepository
import javax.inject.Inject

class DeleteEquipmentUseCase @Inject constructor(
    private val repository: EquipmentRepository,
) {
    suspend operator fun invoke(equipmentId: String): Result<Unit> {
        return try {
            repository.deleteEquipment(equipmentId)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
