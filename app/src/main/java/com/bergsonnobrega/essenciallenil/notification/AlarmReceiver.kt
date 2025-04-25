package com.bergsonnobrega.essenciallenil.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.bergsonnobrega.essenciallenil.MainActivity // Para abrir o app ao clicar na notificação
import com.bergsonnobrega.essenciallenil.R // Para o ícone da notificação
import android.content.res.Resources // Para acessar Resources fora de @Composable

class AlarmReceiver : BroadcastReceiver() {

    companion object {
        const val EXTRA_TASK_ID = "TASK_ID"
        const val EXTRA_TASK_TITLE = "TASK_TITLE"
        const val EXTRA_TASK_DESC = "TASK_DESC"
        const val CHANNEL_ID = "task_reminder_channel"
        const val NOTIFICATION_ID_OFFSET = 1000 // Evitar colisões com outros IDs
    }

    override fun onReceive(context: Context, intent: Intent) {
        val taskId = intent.getLongExtra(EXTRA_TASK_ID, -1)
        val taskTitle = intent.getStringExtra(EXTRA_TASK_TITLE) ?: "Tarefa"
        val taskDesc = intent.getStringExtra(EXTRA_TASK_DESC)

        if (taskId == -1L) return // ID inválido

        println("AlarmReceiver recebeu alarme para tarefa $taskId: $taskTitle")

        createNotificationChannel(context)
        showNotification(context, taskId.toInt(), taskTitle, taskDesc)
    }

    private fun createNotificationChannel(context: Context) {
        // Criar o canal apenas no Android 8.0 (API 26) ou superior
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = context.getString(R.string.notification_channel_name)
            val descriptionText = context.getString(R.string.notification_channel_description)
            val importance = NotificationManager.IMPORTANCE_HIGH // Importância alta para lembretes
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
                // Configurações opcionais do canal:
                // enableLights(true)
                // lightColor = Color.RED
                // enableVibration(true)
            }
            // Registrar o canal no sistema
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
             println("Canal de notificação '$CHANNEL_ID' criado ou já existente.")
        }
    }

    private fun showNotification(context: Context, taskIdAsInt: Int, title: String, description: String?) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationId = taskIdAsInt + NOTIFICATION_ID_OFFSET // ID único para esta notificação

        // Intent para abrir MainActivity ao clicar na notificação
        val resultIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            // Adicionar extra para possível navegação futura
             putExtra("NAVIGATE_TO_TASK_ID", taskIdAsInt.toLong())
        }
        val resultPendingIntent: PendingIntent? = PendingIntent.getActivity(
            context,
            taskIdAsInt, // Request code diferente para a ação principal
            resultIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // --- Adicionar Ação "Marcar como Concluída" --- 
        val markAsDoneIntent = Intent(context, NotificationActionReceiver::class.java).apply {
            action = NotificationActionReceiver.ACTION_MARK_AS_DONE
            putExtra(AlarmReceiver.EXTRA_TASK_ID, taskIdAsInt.toLong())
            putExtra(NotificationActionReceiver.EXTRA_NOTIFICATION_ID, notificationId)
        }
        val markAsDonePendingIntent: PendingIntent = PendingIntent.getBroadcast(
            context,
            notificationId, // Usar notificationId como request code para a ação
            markAsDoneIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        // ---------------------------------------------

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            // TODO: Criar um ícone dedicado para notificações (ex: ic_notification.xml)
            .setSmallIcon(R.drawable.ic_notification_bell) // Usar ícone placeholder
            .setContentTitle(title)
            .setContentText(description ?: context.getString(R.string.task_reminder_notification_default_text))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(resultPendingIntent)
            .addAction(R.drawable.ic_check, // Usar ícone placeholder para ação
                       context.getString(R.string.notification_action_mark_as_done),
                       markAsDonePendingIntent)

        notificationManager.notify(notificationId, builder.build())
        println("Notificação exibida para tarefa ${taskIdAsInt}")
    }
} 