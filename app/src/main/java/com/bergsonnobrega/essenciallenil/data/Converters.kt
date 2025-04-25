package com.bergsonnobrega.essenciallenil.data

import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

// Conversores para o Room saber como lidar com tipos não primitivos
class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): LocalDateTime? {
        // Converte Long (timestamp UTC) para LocalDateTime
        return value?.let { LocalDateTime.ofInstant(Instant.ofEpochSecond(it), ZoneOffset.UTC) }
    }

    @TypeConverter
    fun dateToTimestamp(date: LocalDateTime?): Long? {
        // Converte LocalDateTime para Long (timestamp UTC)
        return date?.toEpochSecond(ZoneOffset.UTC)
    }
} 