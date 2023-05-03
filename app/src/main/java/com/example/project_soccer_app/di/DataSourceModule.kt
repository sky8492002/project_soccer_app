package com.example.project_soccer_app.di

import com.example.project_soccer_app.data.api.SoccerApiService
import com.example.project_soccer_app.data.database.SoccerDatabase
import com.example.project_soccer_app.data.datasource.MatchDataSource
import com.example.project_soccer_app.data.datasource.MatchDataSourceImpl
import com.example.project_soccer_app.data.datasource.SearchDataSource
import com.example.project_soccer_app.data.datasource.SearchDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {

    @Provides
    @Singleton
    fun getMatchDataSource(
        db: SoccerDatabase,
        api: SoccerApiService
    ): MatchDataSource {
        return MatchDataSourceImpl(db, api)
    }

    @Provides
    @Singleton
    fun getSearchDataSource(
        db: SoccerDatabase,
        api: SoccerApiService
    ): SearchDataSource {
        return SearchDataSourceImpl(db, api)
    }
}