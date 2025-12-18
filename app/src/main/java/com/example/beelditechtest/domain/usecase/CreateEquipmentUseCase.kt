package com.example.beelditechtest.domain.usecase

import com.example.beelditechtest.domain.model.Equipment
import com.example.beelditechtest.domain.repository.EquipmentRepository
import javax.inject.Inject

class CreateEquipmentUseCase @Inject constructor(
    private val repository: EquipmentRepository,
) {
    suspend operator fun invoke(equipment: Equipment): Result<Unit> {
        return try {
            repository.addEquipment(equipment)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
