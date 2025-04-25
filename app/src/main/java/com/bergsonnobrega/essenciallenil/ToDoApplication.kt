package com.bergsonnobrega.essenciallenil

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ToDoApplication : Application() {
    // O Hilt cuidará da inicialização necessária aqui.
    // Podemos adicionar inicializações personalizadas se necessário (ex: Timber, Notificações Channels)
    override fun onCreate() {
        super.onCreate()
        // Inicializações futuras aqui
    }
} 