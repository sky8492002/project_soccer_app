package com.example.project_soccer_app.data.datasource

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.project_soccer_app.data.SoccerApiService
import com.example.project_soccer_app.data.database.SoccerDatabase
import com.example.project_soccer_app.data.database.match.LoadedMatchDateEntity
import com.example.project_soccer_app.data.database.match.MatchEntity
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class MatchRemoteMediator(
    val db: SoccerDatabase,
    val api: SoccerApiService,
    val dates: List<String>,
) : RemoteMediator<Int, MatchEntity>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.SKIP_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MatchEntity>
    ): RemoteMediator.MediatorResult {
        try {
            val sortedDates = dates.sorted()
            var orderToLoadDate: String = ""
            when (loadType) {
                LoadType.REFRESH -> orderToLoadDate = sortedDates[0]
                LoadType.PREPEND -> return RemoteMediator.MediatorResult.Success(
                    endOfPaginationReached = true
                )
                LoadType.APPEND -> {
                    val loadedDates = mutableListOf<String>()
                    for (loadedDateEntity in db.loadedDateDao.getLoadedMatchDates()) {
                        loadedDates.add(loadedDateEntity.matchDate)
                    }
                    for (date in sortedDates) {
                        if (date !in loadedDates) {
                            orderToLoadDate = date
                            Log.d("loadOrder", orderToLoadDate)
                            break
                        }
                    }
                }
            }

            if(orderToLoadDate == ""){
                return RemoteMediator.MediatorResult.Success(
                    endOfPaginationReached = true
                )
            }

            val matchEntities = mutableListOf<MatchEntity>()
            val matchesResponse = api.getMatchesByDate(
                date = orderToLoadDate
            )
            for (league in matchesResponse.leagues) {
                for (matchResponse in league.matches) {
                    matchEntities.add(
                        MatchEntity(
                            matchResponse.id,
                            matchResponse.leagueId,
                            league.name,
                            matchResponse.time,
                            matchResponse.home?.id,
                            matchResponse.home?.name,
                            matchResponse.home?.score,
                            matchResponse.away?.id,
                            matchResponse.away?.name,
                            matchResponse.away?.score,
                            matchResponse.eliminatedTeamId,
                            matchResponse.statusId,
                            matchResponse.tournamentStage
                        )
                    )
                }
            }

            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    db.matchDao.deleteAll()
                    db.loadedDateDao.deleteAll()
                }

                matchEntities.let { matchEntities ->
                    db.matchDao.insertAll(matchEntities)
                    db.loadedDateDao.insert(LoadedMatchDateEntity(orderToLoadDate))
                }
            }

            return RemoteMediator.MediatorResult.Success(
                endOfPaginationReached = matchEntities.isEmpty() ?: true
            )
        } catch (e: IOException) {
            return RemoteMediator.MediatorResult.Error(e)
        } catch (e: HttpException) {
            return RemoteMediator.MediatorResult.Error(e)
        }
    }
}