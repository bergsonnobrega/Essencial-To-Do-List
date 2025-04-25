package com.bergsonnobrega.essenciallenil.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bergsonnobrega.essenciallenil.data.model.Task
import com.bergsonnobrega.essenciallenil.data.repository.TaskRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TaskListViewModel(private val taskRepository: TaskRepository) : ViewModel() {

    // Expõe o Flow de tarefas como StateFlow para a UI observar
    val tasks: StateFlow<List<Task>> = taskRepository.getAllTasks()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000), // Mantém ativo por 5s após UI parar de observar
            initialValue = emptyList() // Valor inicial enquanto carrega
        )

    // Função para marcar/desmarcar tarefa como concluída
    fun toggleTaskDone(task: Task) {
        viewModelScope.launch {
            taskRepository.toggleTaskDone(task)
        }
    }

    // Função para deletar uma tarefa
    fun deleteTask(task: Task) {
        viewModelScope.launch {
            taskRepository.delete(task)
        }
    }

    // Função para deletar todas as tarefas (opcional)
    fun deleteAllTasks() {
        viewModelScope.launch {
            taskRepository.deleteAllTasks()
        }
    }
} 