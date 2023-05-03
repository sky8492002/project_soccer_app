package com.example.project_soccer_app.data.datasource

import androidx.paging.PagingData
import com.example.project_soccer_app.data.database.match.MatchEntity
import kotlinx.coroutines.flow.Flow

interface MatchDataSource {
    fun getMatches(dates: List<String>) : Flow<PagingData<MatchEntity>>
}