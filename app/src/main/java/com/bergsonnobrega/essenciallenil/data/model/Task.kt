package com.bergsonnobrega.essenciallenil.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val description: String? = null,
    // Mudar tipo para LocalDateTime?
    val dateTime: LocalDateTime? = null,
    val isDone: Boolean = false
) 