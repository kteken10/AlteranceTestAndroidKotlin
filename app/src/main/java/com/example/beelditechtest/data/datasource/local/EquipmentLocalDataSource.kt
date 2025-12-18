package com.example.beelditechtest.data.datasource.local

import android.content.Context
import com.example.beelditechtest.data.datasource.local.model.EquipmentEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import org.json.JSONArray
import java.io.IOException

class EquipmentLocalDataSource(private val context: Context) {

    // Cache en mémoire pour simuler une base de données
    private val _equipmentsCache = MutableStateFlow<MutableList<EquipmentEntity>>(mutableListOf())
    private var isInitialized = false

    val equipmentsFlow: StateFlow<List<EquipmentEntity>> = _equipmentsCache.asStateFlow()

    private suspend fun ensureInitialized() {
        if (!isInitialized) {
            loadFromJson()
            isInitialized = true
        }
    }

    private suspend fun loadFromJson() = withContext(Dispatchers.IO) {
        try {
            val jsonString = context.assets.open("equipments.json")
                .bufferedReader()
                .use { it.readText() }

            val jsonArray = JSONArray(jsonString)
            val equipmentEntities = mutableListOf<EquipmentEntity>()

            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                val equipmentEntity = EquipmentEntity(
                    id = jsonObject.getString("id"),
                    name = jsonObject.getString("name"),
                    brand = jsonObject.getString("brand"),
                    model = jsonObject.getString("model"),
                    serialNumber = jsonObject.getString("serialNumber"),
                    floor = jsonObject.getString("floor"),
                    status = jsonObject.getString("status"),
                    completionRate = jsonObject.getInt("completionRate"),
                    defectCount = jsonObject.getInt("defectCount"),
                    updatedAt = jsonObject.getLong("updatedAt"),
                    buildingId = jsonObject.getString("buildingId"),
                    imagePath = if (jsonObject.has("imagePath")) jsonObject.getString("imagePath") else null,
                )
                equipmentEntities.add(equipmentEntity)
            }

            _equipmentsCache.value = equipmentEntities
        } catch (e: IOException) {
            _equipmentsCache.value = mutableListOf()
        }
    }

    suspend fun getEquipments(): List<EquipmentEntity> = withContext(Dispatchers.IO) {
        ensureInitialized()
        _equipmentsCache.value.toList()
    }

    suspend fun addEquipment(equipment: EquipmentEntity) = withContext(Dispatchers.IO) {
        ensureInitialized()
        val currentList = _equipmentsCache.value.toMutableList()
        currentList.add(equipment)
        _equipmentsCache.value = currentList
    }

    suspend fun updateEquipment(equipment: EquipmentEntity) = withContext(Dispatchers.IO) {
        ensureInitialized()
        val currentList = _equipmentsCache.value.toMutableList()
        val index = currentList.indexOfFirst { it.id == equipment.id }
        if (index != -1) {
            currentList[index] = equipment
            _equipmentsCache.value = currentList
        }
    }

    suspend fun deleteEquipment(equipmentId: String) = withContext(Dispatchers.IO) {
        ensureInitialized()
        val currentList = _equipmentsCache.value.toMutableList()
        currentList.removeAll { it.id == equipmentId }
        _equipmentsCache.value = currentList
    }
}
