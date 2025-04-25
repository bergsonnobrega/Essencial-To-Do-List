package com.bergsonnobrega.essenciallenil.data.dao

import androidx.room.*
import com.bergsonnobrega.essenciallenil.data.model.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    // Retorna o ID da linha inserida
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: Task): Long

    @Update
    suspend fun update(task: Task)

    @Delete
    suspend fun delete(task: Task)

    @Query("SELECT * FROM tasks WHERE id = :id")
    fun getTaskById(id: Long): Flow<Task?> // Usar Flow para observabilidade

    @Query("SELECT * FROM tasks ORDER BY id DESC") // Ordenar por ID decrescente (mais novas primeiro)
    fun getAllTasks(): Flow<List<Task>> // Usar Flow para observabilidade

    @Query("DELETE FROM tasks")
    suspend fun deleteAllTasks() // Adicional: útil para testes ou reset
} 