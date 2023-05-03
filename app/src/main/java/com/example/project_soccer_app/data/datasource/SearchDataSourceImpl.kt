package com.example.project_soccer_app.data.datasource

import com.example.project_soccer_app.data.api.SoccerApiService
import com.example.project_soccer_app.data.database.SoccerDatabase
import com.example.project_soccer_app.data.response.SearchResponse
import javax.inject.Inject

class SearchDataSourceImpl@Inject constructor(
    val db: SoccerDatabase,
    val api: SoccerApiService,
): SearchDataSource {
    override suspend fun getSearchResult(keyword: String): SearchResponse {
        return api.getSearchResultByKeyword(keyword)
    }

}