package com.bergsonnobrega.essenciallenil.ui

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.bergsonnobrega.essenciallenil.ui.screens.TaskDetailScreen
import com.bergsonnobrega.essenciallenil.ui.screens.TaskListScreen
import com.bergsonnobrega.essenciallenil.viewmodel.ViewModelFactory

// Define as rotas da aplicação
object AppDestinations {
    const val TASK_LIST = "taskList"
    const val TASK_DETAIL = "taskDetail"
    const val TASK_ID_ARG = "taskId"
}

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController()
) {
    // Cria a Factory uma vez para reutilizar nas telas que precisam
    val application = LocalContext.current.applicationContext as Application
    val viewModelFactory = remember { ViewModelFactory(application) }

    NavHost(
        navController = navController,
        startDestination = AppDestinations.TASK_LIST
    ) {
        // Rota para a lista de tarefas
        composable(AppDestinations.TASK_LIST) {
            TaskListScreen(
                onAddTaskClick = {
                    // Navega para detalhes com ID 0 (ou nulo) para indicar nova tarefa
                    navController.navigate("${AppDestinations.TASK_DETAIL}/0")
                },
                onTaskClick = { taskId ->
                    // Navega para detalhes passando o ID da tarefa clicada
                    navController.navigate("${AppDestinations.TASK_DETAIL}/$taskId")
                },
                viewModelFactory = viewModelFactory // Passa a factory
            )
        }

        // Rota para adicionar/editar tarefa
        composable(
            route = "${AppDestinations.TASK_DETAIL}/{${AppDestinations.TASK_ID_ARG}}",
            arguments = listOf(navArgument(AppDestinations.TASK_ID_ARG) { type = NavType.LongType })
        ) {
            // Não precisa pegar o taskId aqui, o ViewModel faz isso via SavedStateHandle
            TaskDetailScreen(
                navigateBack = { navController.popBackStack() },
                viewModelFactory = viewModelFactory // Passa a factory
            )
        }
    }
} 