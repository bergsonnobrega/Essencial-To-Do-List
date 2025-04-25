package com.bergsonnobrega.essenciallenil.notification

import com.bergsonnobrega.essenciallenil.data.model.Task

interface AlarmScheduler {
    fun schedule(task: Task)
    fun cancel(task: Task)
} 