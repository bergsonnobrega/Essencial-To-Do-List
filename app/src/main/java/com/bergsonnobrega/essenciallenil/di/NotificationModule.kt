package com.bergsonnobrega.essenciallenil.di

import android.content.Context
import com.bergsonnobrega.essenciallenil.notification.AlarmManagerAlarmScheduler
import com.bergsonnobrega.essenciallenil.notification.AlarmScheduler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NotificationModule {

    @Provides
    @Singleton
    fun provideAlarmScheduler(@ApplicationContext context: Context): AlarmScheduler {
        return AlarmManagerAlarmScheduler(context)
    }
} 