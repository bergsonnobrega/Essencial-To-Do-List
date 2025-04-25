package com.bergsonnobrega.essenciallenil.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bergsonnobrega.essenciallenil.data.AppDatabase
import com.bergsonnobrega.essenciallenil.data.repository.TaskRepository
import com.bergsonnobrega.essenciallenil.data.repository.TaskRepositoryImpl

// Factory para criar instâncias dos ViewModels, injetando o TaskRepository
class ViewModelFactory(private val application: Application) : ViewModelProvider.Factory {

    // Instancia o repositório aqui (poderia ser um Singleton se não usasse Hilt)
    private val taskRepository: TaskRepository by lazy {
        val database = AppDatabase.getDatabase(application)
        TaskRepositoryImpl(database.taskDao())
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(TaskListViewModel::class.java) -> {
                TaskListViewModel(taskRepository) as T
            }
            modelClass.isAssignableFrom(TaskDetailViewModel::class.java) -> {
                // O SavedStateHandle é fornecido automaticamente pelo `viewModel()` composable
                // quando usamos a factory correta, não precisamos passá-lo aqui.
                TaskDetailViewModel(taskRepository) as T // Passamos null para savedStateHandle aqui
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
} 