package com.example.project_soccer_app.data.database.match

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MatchDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(matches: List<MatchEntity>)

    @Query("SELECT * FROM matches ORDER BY time")
    fun getAllMatches(): PagingSource<Int, MatchEntity>

    @Query("DELETE FROM matches")
    suspend fun deleteAll()
}