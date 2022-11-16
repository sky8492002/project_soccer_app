package com.example.project_soccer_app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.project_soccer_app.R
import com.example.project_soccer_app.adapter.AdapterLobbyViewPager
import com.example.project_soccer_app.databinding.FragmentLobbyBinding
import com.google.android.material.tabs.TabLayoutMediator

class LobbyFragment: Fragment() {
    lateinit var fragmentLobbyBinding: FragmentLobbyBinding

    private val tabTitleArray = arrayOf(
        "경기 찾기",
        "맵",
        "조건설정"
    )
    private val tabIconArray = arrayOf(
        R.drawable.player1,
        R.drawable.ball,
        R.drawable.player2
    )


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentLobbyBinding = FragmentLobbyBinding.inflate(layoutInflater)

        initTabLayout()


        val view = fragmentLobbyBinding.root
        return view
    }

    fun initTabLayout(){
        val viewPager = fragmentLobbyBinding.lobbyViewPager2
        val tabLayout = fragmentLobbyBinding.lobbyTabLayout

        viewPager.adapter = AdapterLobbyViewPager(requireActivity().supportFragmentManager, lifecycle)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabTitleArray[position]
            tab.icon = ContextCompat.getDrawable(requireContext(), tabIconArray[position])
        }.attach()
    }
}