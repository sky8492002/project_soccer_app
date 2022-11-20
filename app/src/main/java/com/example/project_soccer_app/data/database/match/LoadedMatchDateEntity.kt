package com.example.project_soccer_app.data.database.match

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "loadedMatchDate")
data class LoadedMatchDateEntity (
    @PrimaryKey
    val matchDate: String
)