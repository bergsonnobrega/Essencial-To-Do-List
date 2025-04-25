package com.bergsonnobrega.essenciallenil.ui.screens

import android.app.Application
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bergsonnobrega.essenciallenil.ui.theme.EssencialLenilTheme
import com.bergsonnobrega.essenciallenil.viewmodel.TaskDetailViewModel
import com.bergsonnobrega.essenciallenil.viewmodel.TaskDetailUiState
import com.bergsonnobrega.essenciallenil.viewmodel.ViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailScreen(
    navigateBack: () -> Unit,
    // Injete a Factory para criar o ViewModel
    viewModelFactory: ViewModelFactory = ViewModelFactory(LocalContext.current.applicationContext as Application),
    // Nota: O taskId é passado via SavedStateHandle diretamente para o ViewModel
    viewModel: TaskDetailViewModel = viewModel(factory = viewModelFactory)
) {
    val uiState by viewModel.uiState.collectAsState()

    // Campos de texto controlados
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    // Atualiza os campos de texto quando a tarefa carrega do ViewModel
    LaunchedEffect(uiState.task) {
        uiState.task?.let {
            title = it.title
            description = it.description ?: ""
        }
    }

    // Efeito para navegar de volta quando taskSaved for true
    LaunchedEffect(uiState.taskSaved) {
        if (uiState.taskSaved) {
            navigateBack()
            viewModel.onTaskSavedNavigated() // Reseta o estado
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (uiState.task == null) "Nova Tarefa" else "Editar Tarefa") },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                if (title.isNotBlank()) { // Validação simples
                    viewModel.saveTask(title, description.ifBlank { null })
                }
            }) {
                Icon(Icons.Filled.Check, contentDescription = "Salvar Tarefa")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            if (uiState.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Título*") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    isError = title.isBlank() && uiState.isSaving // Mostra erro se tentar salvar em branco
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Descrição (Opcional)") },
                    modifier = Modifier.fillMaxWidth().height(150.dp) // Altura maior para descrição
                )
                // TODO: Adicionar seleção de Data/Hora aqui no futuro

                // Exibir mensagem de erro
                uiState.error?.let {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            // Indicador de salvamento
            if (uiState.isSaving) {
                 Spacer(modifier = Modifier.height(16.dp))
                 CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TaskDetailScreenNewPreview() {
    EssencialLenilTheme {
        // Simula a tela de nova tarefa
        TaskDetailScreen(navigateBack = {})
    }
}

// Seria necessário um preview mais complexo para simular edição
// com um ViewModel mockado, omitido por simplicidade. 