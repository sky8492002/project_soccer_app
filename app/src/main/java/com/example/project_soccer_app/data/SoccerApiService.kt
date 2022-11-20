package com.example.project_soccer_app.data

import retrofit2.http.GET
import retrofit2.http.Query

interface SoccerApiService {

    @GET("api/matches")
    suspend fun getMatchesByDate(
        @Query("date") date: String,
    ): MatchesResponse
}