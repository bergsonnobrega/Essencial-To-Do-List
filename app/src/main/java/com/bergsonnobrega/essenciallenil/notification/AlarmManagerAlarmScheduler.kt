package com.bergsonnobrega.essenciallenil.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.bergsonnobrega.essenciallenil.data.model.Task
import dagger.hilt.android.qualifiers.ApplicationContext
import java.time.ZoneId
import javax.inject.Inject

class AlarmManagerAlarmScheduler @Inject constructor(
    @ApplicationContext private val context: Context
) : AlarmScheduler {

    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    override fun schedule(task: Task) {
        val taskDateTime = task.dateTime ?: return
        val now = java.time.LocalDateTime.now()
        if (taskDateTime.isBefore(now)) {
            return
        }

        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra(AlarmReceiver.EXTRA_TASK_ID, task.id)
            putExtra(AlarmReceiver.EXTRA_TASK_TITLE, task.title)
            putExtra(AlarmReceiver.EXTRA_TASK_DESC, task.description)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            task.id.toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val triggerAtMillis = taskDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

        try {
             if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                 if (alarmManager.canScheduleExactAlarms()) {
                     alarmManager.setExactAndAllowWhileIdle(
                         AlarmManager.RTC_WAKEUP,
                         triggerAtMillis,
                         pendingIntent
                     )
                     println("Alarme EXATO agendado para tarefa ${task.id} em $taskDateTime")
                 } else {
                     // Fallback: Permissão não concedida, tentar agendar alarme inexato.
                     // O sistema pode atrasar um pouco a entrega.
                     alarmManager.setAndAllowWhileIdle(
                         AlarmManager.RTC_WAKEUP,
                         triggerAtMillis,
                         pendingIntent
                     )
                     println("WARN: Permissão SCHEDULE_EXACT_ALARM não concedida. Alarme INEXATO agendado para tarefa ${task.id} aproximadamente em $taskDateTime.")
                     // TODO: Idealmente, informar o usuário sobre a falta de permissão.
                 }
             } else {
                 // Versões anteriores
                 alarmManager.setExactAndAllowWhileIdle(
                     AlarmManager.RTC_WAKEUP,
                     triggerAtMillis,
                     pendingIntent
                 )
                 println("Alarme EXATO agendado (pré-Android S) para tarefa ${task.id} em $taskDateTime")
             }
        } catch (se: SecurityException) {
             println("### SecurityException ao agendar alarme para tarefa ${task.id}: ${se.message}")
             // Tratar falha de permissão aqui, talvez notificar o usuário
        }
    }

    override fun cancel(task: Task) {
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            task.id.toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.cancel(pendingIntent)
         println("Alarme cancelado para tarefa ${task.id}")
    }
} 