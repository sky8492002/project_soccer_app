package com.example.project_soccer_app.ui.matchsearch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.project_soccer_app.adapter.MatchAdapter
import com.example.project_soccer_app.databinding.FragmentMatchSearchBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MatchSearchFragment: Fragment() {
    lateinit var fragmentMatchSearchBinding: FragmentMatchSearchBinding
    val viewModel: MatchSearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentMatchSearchBinding = FragmentMatchSearchBinding.inflate(layoutInflater)

        val matchAdapter = MatchAdapter(requireContext())
        fragmentMatchSearchBinding.matchSelectRecyclerView.adapter = matchAdapter

        val sampleDates = mutableListOf<String>()
        sampleDates.add("20221217")
        sampleDates.add("20221218")
        sampleDates.add("20221219")

        viewLifecycleOwner.lifecycleScope.launch(){
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.getMatches(sampleDates)
                viewModel.loadedMatches.collect(){
                    matchAdapter.submitData(it)
                }
            }
        }

        val view = fragmentMatchSearchBinding.root
        return view
    }

}