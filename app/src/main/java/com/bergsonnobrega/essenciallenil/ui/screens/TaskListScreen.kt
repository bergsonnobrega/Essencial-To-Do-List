package com.bergsonnobrega.essenciallenil.ui.screens

import android.app.Application
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bergsonnobrega.essenciallenil.data.model.Task
import com.bergsonnobrega.essenciallenil.ui.components.TaskItem
import com.bergsonnobrega.essenciallenil.ui.theme.EssencialLenilTheme
import com.bergsonnobrega.essenciallenil.viewmodel.TaskListViewModel
import com.bergsonnobrega.essenciallenil.viewmodel.ViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(
    onAddTaskClick: () -> Unit,
    onTaskClick: (Long) -> Unit,
    // Injete a Factory para criar o ViewModel
    viewModelFactory: ViewModelFactory = ViewModelFactory(LocalContext.current.applicationContext as Application),
    viewModel: TaskListViewModel = viewModel(factory = viewModelFactory)
) {
    val tasks by viewModel.tasks.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Minhas Tarefas") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddTaskClick) {
                Icon(Icons.Filled.Add, contentDescription = "Adicionar Tarefa")
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
            if (tasks.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Nenhuma tarefa ainda!", style = MaterialTheme.typography.bodyLarge)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    items(tasks, key = { it.id }) { task ->
                        TaskItem(
                            task = task,
                            onTaskClick = { onTaskClick(task.id) },
                            onCheckedChange = { t, _ -> viewModel.toggleTaskDone(t) },
                            onDeleteClick = { viewModel.deleteTask(it) }
                        )
                    }
                }
            }
        }
    }
}

// Preview básico sem ViewModel real
@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun TaskListScreenPreview() {
    EssencialLenilTheme {
        // Simula a tela sem interações reais
        Scaffold(
            topBar = { TopAppBar(title = { Text("Minhas Tarefas") }) },
            floatingActionButton = {
                FloatingActionButton(onClick = {}) {
                    Icon(Icons.Filled.Add, contentDescription = "Adicionar Tarefa")
                }
            }
        ) { padding ->
             LazyColumn(modifier = Modifier.padding(padding).fillMaxSize(), contentPadding = PaddingValues(vertical = 8.dp)) {
                 items(listOf(
                     Task(1, "Tarefa 1", isDone = false),
                     Task(2, "Tarefa 2", isDone = true),
                     Task(3, "Tarefa 3", isDone = false)
                 )) { task ->
                      TaskItem(task = task, onTaskClick = {}, onCheckedChange = {_,_ -> }, onDeleteClick = {}) // Mock handlers
                 }
             }
        }
    }
} 