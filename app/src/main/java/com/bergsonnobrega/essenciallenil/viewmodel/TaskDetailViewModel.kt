package com.bergsonnobrega.essenciallenil.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bergsonnobrega.essenciallenil.data.model.Task
import com.bergsonnobrega.essenciallenil.data.repository.TaskRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

// Estado da UI para a tela de detalhes
data class TaskDetailUiState(
    val task: Task? = null, // Tarefa atual (null se for nova)
    val isLoading: Boolean = false,
    val isSaving: Boolean = false,
    val error: String? = null,
    val taskSaved: Boolean = false // Flag para indicar que salvou e pode navegar de volta
)

class TaskDetailViewModel(
    private val taskRepository: TaskRepository,
    // SavedStateHandle é injetado automaticamente pelo Navigation Compose
    private val savedStateHandle: SavedStateHandle? = null // Tornar opcional para factory
) : ViewModel() {

    private val _uiState = MutableStateFlow(TaskDetailUiState())
    val uiState: StateFlow<TaskDetailUiState> = _uiState.asStateFlow()

    // Pega o ID da tarefa dos argumentos de navegação
    private val taskId: Long? = savedStateHandle?.get<Long>("taskId")

    init {
        if (taskId != null && taskId != 0L) { // 0L pode indicar nova tarefa
            loadTask(taskId)
        }
    }

    private fun loadTask(id: Long) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            taskRepository.getTaskById(id)
                .filterNotNull() // Ignora o estado inicial null do Flow se a tarefa ainda não existe
                .collectLatest { task ->
                    _uiState.update {
                        it.copy(isLoading = false, task = task, error = null)
                    }
                }
            // Poderia adicionar tratamento de erro se o Flow completar sem emitir
        }
    }

    // Salva ou atualiza a tarefa
    fun saveTask(title: String, description: String?) {
        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true) }
            try {
                val currentTask = _uiState.value.task
                if (currentTask != null) { // Atualizar tarefa existente
                    val updatedTask = currentTask.copy(
                        title = title.trim(),
                        description = description?.trim()
                    )
                    taskRepository.update(updatedTask)
                } else { // Inserir nova tarefa
                    val newTask = Task(
                        title = title.trim(),
                        description = description?.trim(),
                        isDone = false // Valor padrão para nova tarefa
                    )
                    taskRepository.insert(newTask)
                }
                _uiState.update { it.copy(isSaving = false, taskSaved = true, error = null) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isSaving = false, error = "Falha ao salvar: ${e.message}", taskSaved = false) }
            }
        }
    }

    // Reseta o flag taskSaved após a navegação
    fun onTaskSavedNavigated() {
        _uiState.update { it.copy(taskSaved = false) }
    }
} 