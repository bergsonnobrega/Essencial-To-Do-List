package com.bergsonnobrega.essenciallenil.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bergsonnobrega.essenciallenil.data.dao.TaskDao
import com.bergsonnobrega.essenciallenil.data.model.Task

@Database(entities = [Task::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            // Retorna a instância existente ou cria uma nova de forma thread-safe
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "task_database" // Nome do arquivo do banco de dados
                )
                .fallbackToDestructiveMigration() // Descomentar para desenvolvimento
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
} 