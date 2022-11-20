package com.example.project_soccer_app.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.project_soccer_app.data.database.match.LoadedMatchDateDao
import com.example.project_soccer_app.data.database.match.LoadedMatchDateEntity
import com.example.project_soccer_app.data.database.match.MatchDao
import com.example.project_soccer_app.data.database.match.MatchEntity

@Database(
    entities = [MatchEntity::class, LoadedMatchDateEntity::class],
    version = 1,
    exportSchema = false
)
abstract class SoccerDatabase: RoomDatabase() {

    companion object {
        fun create(context: Context): SoccerDatabase{
            val databaseBuilder =
                Room.databaseBuilder(context, SoccerDatabase::class.java, "soccer.db")
            return databaseBuilder.fallbackToDestructiveMigration().build()
        }
    }

    abstract val matchDao: MatchDao
    abstract val loadedDateDao: LoadedMatchDateDao

}