package com.example.project_soccer_app.data.datasource

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.project_soccer_app.data.api.SoccerApiService
import com.example.project_soccer_app.data.database.SoccerDatabase
import com.example.project_soccer_app.data.database.match.MatchEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MatchDataSourceImpl@Inject constructor(
    val db: SoccerDatabase,
    val api: SoccerApiService,
): MatchDataSource {

    @OptIn(ExperimentalPagingApi::class)
    override fun getMatches(dates: List<String>): Flow<PagingData<MatchEntity>> {
        return Pager(
            config = PagingConfig(10),
            remoteMediator = MatchRemoteMediator(
                db, api, dates
            ),
        ) {
            db.matchDao.getAllMatches()
        }.flow
    }

}