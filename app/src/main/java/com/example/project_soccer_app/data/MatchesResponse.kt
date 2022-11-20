package com.example.project_soccer_app.data

data class MatchesResponse(
    val leagues: List<LeagueResponse>
)

data class LeagueResponse(
    val id: Int,
    val primaryId: Int?,
    val name: String?,
    val matches: List<MatchResponse>
)

data class MatchResponse(
    val id: Int,
    val leagueId: Int?,
    val time: String?,
    val home: TeamScoreResponse?,
    val away: TeamScoreResponse?,
    val eliminatedTeamId: Int?,
    val statusId: Int,
    val tournamentStage: String?,
    val status: StatusResponse
)

data class TeamScoreResponse(
    val id: Int,
    val score: Int?,
    val name: String?,
    val longName: String?
)

data class StatusResponse(
    val started: Boolean?,
    val cancelled: Boolean?,
    val finished: Boolean?,
    val startTimeStr: String?,
    val startDateStr: String?,
    val startDateStrShort: String?
)