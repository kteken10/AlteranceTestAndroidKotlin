package com.example.beelditechtest.di

import android.content.Context
import com.example.beelditechtest.data.datasource.local.BuildingLocalDataSource
import com.example.beelditechtest.data.datasource.local.EquipmentLocalDataSource
import com.example.beelditechtest.data.repository.BuildingRepositoryImpl
import com.example.beelditechtest.data.repository.EquipmentRepositoryImpl
import com.example.beelditechtest.domain.repository.BuildingRepository
import com.example.beelditechtest.domain.repository.EquipmentRepository
import com.example.beelditechtest.domain.usecase.CreateEquipmentUseCase
import com.example.beelditechtest.domain.usecase.DeleteEquipmentUseCase
import com.example.beelditechtest.domain.usecase.GetBuildingsUseCase
import com.example.beelditechtest.domain.usecase.GetBuildingsWithStatsUseCase
import com.example.beelditechtest.domain.usecase.GetEquipmentByIdUseCase
import com.example.beelditechtest.domain.usecase.GetEquipmentsUseCase
import com.example.beelditechtest.domain.usecase.GetFilteredEquipmentsUseCase
import com.example.beelditechtest.domain.usecase.GetParkStatsUseCase
import com.example.beelditechtest.domain.usecase.UpdateEquipmentUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // DataSources
    @Provides
    @Singleton
    fun provideEquipmentLocalDataSource(
        @ApplicationContext context: Context,
    ): EquipmentLocalDataSource {
        return EquipmentLocalDataSource(context)
    }

    @Provides
    @Singleton
    fun provideBuildingLocalDataSource(
        @ApplicationContext context: Context,
    ): BuildingLocalDataSource {
        return BuildingLocalDataSource(context)
    }

    // Repositories
    @Provides
    @Singleton
    fun provideEquipmentRepository(
        localDataSource: EquipmentLocalDataSource,
    ): EquipmentRepository {
        return EquipmentRepositoryImpl(localDataSource)
    }

    @Provides
    @Singleton
    fun provideBuildingRepository(
        localDataSource: BuildingLocalDataSource,
    ): BuildingRepository {
        return BuildingRepositoryImpl(localDataSource)
    }

    // UseCases - Equipment
    @Provides
    @Singleton
    fun provideGetEquipmentsUseCase(
        repository: EquipmentRepository,
    ): GetEquipmentsUseCase {
        return GetEquipmentsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetEquipmentByIdUseCase(
        repository: EquipmentRepository,
    ): GetEquipmentByIdUseCase {
        return GetEquipmentByIdUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetFilteredEquipmentsUseCase(
        repository: EquipmentRepository,
    ): GetFilteredEquipmentsUseCase {
        return GetFilteredEquipmentsUseCase(repository)
    }

    // UseCases - Building
    @Provides
    @Singleton
    fun provideGetBuildingsUseCase(
        repository: BuildingRepository,
    ): GetBuildingsUseCase {
        return GetBuildingsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetBuildingsWithStatsUseCase(
        buildingRepository: BuildingRepository,
        equipmentRepository: EquipmentRepository,
    ): GetBuildingsWithStatsUseCase {
        return GetBuildingsWithStatsUseCase(buildingRepository, equipmentRepository)
    }

    @Provides
    @Singleton
    fun provideGetParkStatsUseCase(
        buildingRepository: BuildingRepository,
        equipmentRepository: EquipmentRepository,
    ): GetParkStatsUseCase {
        return GetParkStatsUseCase(buildingRepository, equipmentRepository)
    }

    @Provides
    @Singleton
    fun provideCreateEquipmentUseCase(
        repository: EquipmentRepository,
    ): CreateEquipmentUseCase {
        return CreateEquipmentUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideUpdateEquipmentUseCase(
        repository: EquipmentRepository,
    ): UpdateEquipmentUseCase {
        return UpdateEquipmentUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideDeleteEquipmentUseCase(
        repository: EquipmentRepository,
    ): DeleteEquipmentUseCase {
        return DeleteEquipmentUseCase(repository)
    }
}
