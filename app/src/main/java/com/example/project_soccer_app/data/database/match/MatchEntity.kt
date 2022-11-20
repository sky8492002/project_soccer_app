package com.example.project_soccer_app.data.database.match

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "matches")
data class MatchEntity(
    @PrimaryKey
    val id: Int,
    val leagueId: Int?,
    val leagueName: String?,
    val time: String?,
    val homeTeamId: Int?,
    val homeTeamName: String?,
    val homeTeamScore: Int?,
    val awayTeamId: Int?,
    val awayTeamName: String?,
    val awayTeamScore: Int?,
    val eliminatedTeamId: Int?,
    val statusId: Int,
    val tournamentStage: String?
)