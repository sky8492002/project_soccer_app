package com.example.project_soccer_app.data.response

data class SearchResponse(
    val matches: List<MatchSearchResponse>,
    val teams: List<TeamSearchResponse>,
    val squad: List<SquadSearchResponse>
)

data class MatchSearchResponse(
    val datasetLength: Int?,
    val dataset: List<MatchSummaryResponse>
)

data class TeamSearchResponse(
    val datasetLength: Int?,
    val dataset: List<TeamSummaryResponse>
)

data class SquadSearchResponse(
    val datasetLength: Int?,
    val dataset: List<PlayerSummaryResponse>
)

data class TeamSummaryResponse(
    val id : Int?,
    val name: String?
)

data class PlayerSummaryResponse(
    val id : Int?,
    val name: String?,
    val timeName: String?
)