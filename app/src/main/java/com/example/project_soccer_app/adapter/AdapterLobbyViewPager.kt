package com.example.project_soccer_app.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.project_soccer_app.ui.RoomSearchFragment

class AdapterLobbyViewPager(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
        override fun getItemCount(): Int {
            return 3
        }

        override fun createFragment(position: Int): Fragment {
            when (position) {
                0 -> return RoomSearchFragment()
                1 -> return RoomSearchFragment()
                2 -> return RoomSearchFragment()
            }
            return RoomSearchFragment()
        }
    }