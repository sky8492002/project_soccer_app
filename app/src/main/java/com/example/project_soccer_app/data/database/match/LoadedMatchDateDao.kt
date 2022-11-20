package com.example.project_soccer_app.data.database.match

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface LoadedMatchDateDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(loadedMatchDateEntity: LoadedMatchDateEntity)

    @Query("SELECT * FROM loadedMatchDate ORDER BY matchDate")
    suspend fun getLoadedMatchDates(): List<LoadedMatchDateEntity>

    @Query("DELETE FROM loadedMatchDate")
    suspend fun deleteAll()
}