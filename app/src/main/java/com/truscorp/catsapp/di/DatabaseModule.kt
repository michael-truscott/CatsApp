package com.truscorp.catsapp.di

import android.content.Context
import androidx.room.Room
import com.truscorp.catsapp.data.db.CatsAppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideCatsAppDatabase(
        @ApplicationContext context: Context
    ): CatsAppDatabase {
        return Room.databaseBuilder(context, CatsAppDatabase::class.java, "CatsApp")
            .build()
    }
}