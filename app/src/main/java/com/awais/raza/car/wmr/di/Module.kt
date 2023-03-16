package com.awais.raza.car.wmr.di

import android.app.Application
import androidx.room.Room
import com.awais.raza.car.wmr.database.ChatDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Provides
    @Singleton
    fun provideAppDatabase(application: Application): ChatDatabase {
        return Room
            .databaseBuilder(application, ChatDatabase::class.java, "database")
            .fallbackToDestructiveMigrationFrom()
            .build()
    }
}