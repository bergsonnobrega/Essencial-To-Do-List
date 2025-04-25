package com.bergsonnobrega.essenciallenil.di

import com.bergsonnobrega.essenciallenil.data.dao.TaskDao
import com.bergsonnobrega.essenciallenil.data.repository.TaskRepository
import com.bergsonnobrega.essenciallenil.data.repository.TaskRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton // Repositório pode ser Singleton se não tiver estado mutável dependente de contexto
    fun provideTaskRepository(taskDao: TaskDao): TaskRepository {
        return TaskRepositoryImpl(taskDao)
    }
} 