package com.example.project_soccer_app.data.api

import com.example.project_soccer_app.data.response.MatchesResponse
import com.example.project_soccer_app.data.response.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SoccerApiService {

    @GET("api/matches")
    suspend fun getMatchesByDate(
        @Query("date") date: String,
    ): MatchesResponse

    @GET("api/searchData")
    suspend fun getSearchResultByKeyword(
        @Query("term") keyword: String
    ): SearchResponse


}