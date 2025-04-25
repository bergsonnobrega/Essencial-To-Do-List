package com.bergsonnobrega.essenciallenil.data.repository

import com.bergsonnobrega.essenciallenil.data.dao.TaskDao
import com.bergsonnobrega.essenciallenil.data.model.Task
import kotlinx.coroutines.flow.Flow

// Implementação concreta do repositório
class TaskRepositoryImpl(
    private val taskDao: TaskDao // Injeção do DAO
) : TaskRepository {

    override fun getAllTasks(): Flow<List<Task>> = taskDao.getAllTasks()

    override fun getTaskById(id: Long): Flow<Task?> = taskDao.getTaskById(id)

    override suspend fun insert(task: Task): Long = taskDao.insert(task)

    override suspend fun update(task: Task) = taskDao.update(task)

    override suspend fun delete(task: Task) = taskDao.delete(task)

    // Lógica extra para marcar/desmarcar como concluída
    override suspend fun toggleTaskDone(task: Task) {
        val updatedTask = task.copy(isDone = !task.isDone)
        taskDao.update(updatedTask)
    }

    override suspend fun deleteAllTasks() = taskDao.deleteAllTasks()
} 