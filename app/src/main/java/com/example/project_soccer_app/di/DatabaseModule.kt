package com.example.project_soccer_app.di

import android.content.Context
import com.example.project_soccer_app.data.database.SoccerDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideSoccerDB(
        @ApplicationContext context: Context
    ): SoccerDatabase{
        return SoccerDatabase.create(context)
    }
}