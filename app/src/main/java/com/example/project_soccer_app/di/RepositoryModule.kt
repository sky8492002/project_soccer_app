package com.example.project_soccer_app.di

import com.example.project_soccer_app.data.datasource.MatchDataSource
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
        matchDataSource: MatchDataSource
    ): MatchRepository {
        return MatchRepositoryImpl(matchDataSource)
    }
}