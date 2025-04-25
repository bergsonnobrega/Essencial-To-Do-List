package com.bergsonnobrega.essenciallenil.notification

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.bergsonnobrega.essenciallenil.data.repository.TaskRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class NotificationActionReceiver : BroadcastReceiver() {

    @Inject
    lateinit var repository: TaskRepository
    // Poderíamos injetar AlarmScheduler para cancelar o alarme aqui também, mas já é cancelado no ViewModel

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    companion object {
        const val ACTION_MARK_AS_DONE = "com.bergsonnobrega.essenciallenil.ACTION_MARK_AS_DONE"
        const val EXTRA_NOTIFICATION_ID = "NOTIFICATION_ID" // Para cancelar a notificação
        // Usaremos o mesmo EXTRA_TASK_ID do AlarmReceiver
    }

    override fun onReceive(context: Context, intent: Intent) {
        val taskId = intent.getLongExtra(AlarmReceiver.EXTRA_TASK_ID, -1)
        val notificationId = intent.getIntExtra(EXTRA_NOTIFICATION_ID, -1)

        if (taskId == -1L) {
            println("NotificationActionReceiver: ID da tarefa inválido.")
            return
        }

        when (intent.action) {
            ACTION_MARK_AS_DONE -> {
                println("NotificationActionReceiver: Ação MARCAR COMO FEITO para tarefa $taskId")
                scope.launch {
                    try {
                        val task = repository.getTaskById(taskId).firstOrNull()
                        if (task != null && !task.isDone) {
                            repository.toggleTaskDone(task)
                            println("NotificationActionReceiver: Tarefa $taskId marcada como concluída.")
                            // Cancelar a notificação após a ação
                            if (notificationId != -1) {
                                val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                                notificationManager.cancel(notificationId)
                                println("NotificationActionReceiver: Notificação $notificationId cancelada.")
                            }
                        } else {
                             println("NotificationActionReceiver: Tarefa $taskId não encontrada ou já concluída.")
                             // Cancelar notificação mesmo se já feita, para limpar
                             if (notificationId != -1) {
                                val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                                notificationManager.cancel(notificationId)
                             }
                        }
                    } catch (e: Exception) {
                        println("### NotificationActionReceiver: Erro ao marcar tarefa como concluída: ${e.message}")
                    }
                }
            }
            // Adicionar outros WHENs para futuras ações (ex: adiar)
        }
    }
} 