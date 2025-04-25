package com.bergsonnobrega.essenciallenil.data.repository

import com.bergsonnobrega.essenciallenil.data.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun getAllTasks(): Flow<List<Task>>
    fun getTaskById(id: Long): Flow<Task?>
    suspend fun insert(task: Task): Long
    suspend fun update(task: Task)
    suspend fun delete(task: Task)
    suspend fun toggleTaskDone(task: Task)
    suspend fun deleteAllTasks()
} 