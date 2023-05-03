package com.example.project_soccer_app.data.repository

import androidx.paging.PagingData
import com.example.project_soccer_app.data.database.match.MatchEntity
import com.example.project_soccer_app.data.datasource.MatchDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MatchRepositoryImpl @Inject constructor(
    private val matchDataSource: MatchDataSource
) : MatchRepository {

    override fun getMatches(dates: List<String>): Flow<PagingData<MatchEntity>> {
        return matchDataSource.getMatches(dates)
    }

}