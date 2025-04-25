package com.bergsonnobrega.essenciallenil.di

import android.content.Context
import androidx.room.Room
import com.bergsonnobrega.essenciallenil.data.AppDatabase
import com.bergsonnobrega.essenciallenil.data.dao.TaskDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) // Dependências viverão enquanto o app viver
object DatabaseModule {

    @Provides
    @Singleton // Garante uma única instância do banco de dados
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "task_database"
        )
        // Adiciona estratégia de migração destrutiva ao atualizar versão
        .fallbackToDestructiveMigration()
        .build()
    }

    @Provides
    // Não precisa ser Singleton, o Room gerencia isso
    fun provideTaskDao(appDatabase: AppDatabase): TaskDao {
        return appDatabase.taskDao()
    }
} 