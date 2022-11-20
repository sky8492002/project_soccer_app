package com.example.project_soccer_app.ui.matchsearch

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.example.project_soccer_app.data.database.match.MatchEntity
import com.example.project_soccer_app.data.repository.MatchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MatchSearchViewModel @Inject constructor(
    private val matchRepository: MatchRepository
): ViewModel(){
    lateinit var loadedMatches: Flow<PagingData<MatchEntity>>

    fun getMatches(dates: List<String>){
        loadedMatches = matchRepository.getMatches(dates)
    }
}