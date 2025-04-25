package com.bergsonnobrega.essenciallenil.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.bergsonnobrega.essenciallenil.data.repository.TaskRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@AndroidEntryPoint // Permite injeção de dependência
class BootReceiver : BroadcastReceiver() {

    @Inject
    lateinit var repository: TaskRepository

    @Inject
    lateinit var alarmScheduler: AlarmScheduler

    // Escopo de Coroutine para tarefas de background fora do ciclo de vida da UI
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            println("BootReceiver: Dispositivo inicializado. Reagendando alarmes...")

            scope.launch {
                try {
                    val tasks = repository.getAllTasks().first() // Pega a lista atual de tarefas
                    val now = LocalDateTime.now()
                    var rescheduledCount = 0
                    tasks.forEach { task ->
                        if (task.dateTime != null && task.dateTime.isAfter(now)) {
                            alarmScheduler.schedule(task)
                            rescheduledCount++
                        }
                    }
                    println("BootReceiver: $rescheduledCount alarmes reagendados.")
                } catch (e: Exception) {
                    println("### BootReceiver: Erro ao reagendar alarmes: ${e.message}")
                    // Logar o erro, talvez usando uma biblioteca de logging
                }
            }
        }
    }
} 