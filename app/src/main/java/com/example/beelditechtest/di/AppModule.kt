package com.example.beelditechtest.di

import android.content.Context
import com.example.beelditechtest.data.datasource.local.EquipmentLocalDataSource
import com.example.beelditechtest.data.repository.EquipmentRepositoryImpl
import com.example.beelditechtest.domain.repository.EquipmentRepository
import com.example.beelditechtest.domain.usecase.GetEquipmentByIdUseCase
import com.example.beelditechtest.domain.usecase.GetEquipmentsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideEquipmentLocalDataSource(
        @ApplicationContext context: Context,
    ): EquipmentLocalDataSource {
        return EquipmentLocalDataSource(context)
    }

    @Provides
    @Singleton
    fun provideEquipmentRepository(
        localDataSource: EquipmentLocalDataSource,
    ): EquipmentRepository {
        return EquipmentRepositoryImpl(localDataSource)
    }

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
}
