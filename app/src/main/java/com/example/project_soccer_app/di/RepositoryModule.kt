package com.example.project_soccer_app.di

import com.example.project_soccer_app.data.SoccerApiService
import com.example.project_soccer_app.data.database.SoccerDatabase
import com.example.project_soccer_app.data.repository.MatchRepository
import com.example.project_soccer_app.data.repository.MatchRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun getMatchRepository(
        db: SoccerDatabase,
        api: SoccerApiService
    ): MatchRepository {
        return MatchRepositoryImpl(db, api)
    }
}