package com.example.beelditechtest.data.datasource.local

import android.content.Context
import com.example.beelditechtest.data.datasource.local.model.BuildingEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import java.io.IOException

class BuildingLocalDataSource(private val context: Context) {

    suspend fun getBuildings(): List<BuildingEntity> = withContext(Dispatchers.IO) {
        try {
            val jsonString = context.assets.open("buildings.json")
                .bufferedReader()
                .use { it.readText() }

            val jsonArray = JSONArray(jsonString)
            val buildingEntities = mutableListOf<BuildingEntity>()

            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                val buildingEntity = BuildingEntity(
                    id = jsonObject.getString("id"),
                    name = jsonObject.getString("name"),
                    address = jsonObject.getString("address"),
                    city = jsonObject.getString("city"),
                )
                buildingEntities.add(buildingEntity)
            }

            buildingEntities
        } catch (e: IOException) {
            emptyList()
        }
    }
}
