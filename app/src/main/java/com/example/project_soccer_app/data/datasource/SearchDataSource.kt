package com.example.project_soccer_app.data.datasource

import com.example.project_soccer_app.data.response.SearchResponse

interface SearchDataSource {
    suspend fun getSearchResult(keyword: String): SearchResponse
}