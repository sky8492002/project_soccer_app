package com.example.project_soccer_app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project_soccer_app.R
import com.example.project_soccer_app.adapter.AdapterRoomList
import com.example.project_soccer_app.databinding.FragmentRoomSearchBinding

class RoomSearchFragment: Fragment() {
    lateinit var fragmentRoomSearchBinding: FragmentRoomSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentRoomSearchBinding = FragmentRoomSearchBinding.inflate(layoutInflater)

        var adapterRoomList = setRoomListRecyclerView(fragmentRoomSearchBinding.roomSelectRecyclerView)

        fragmentRoomSearchBinding.addRoomButton.setOnClickListener(){
            findNavController().navigate(R.id.action_lobbyFragment_to_makeRoomFragment)
        }

        val view = fragmentRoomSearchBinding.root
        return view
    }

    fun setRoomListRecyclerView(recyclerView: RecyclerView):AdapterRoomList{
        recyclerView.adapter = AdapterRoomList(requireContext())
        val adapter = recyclerView.adapter as AdapterRoomList
        val linearLayoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.setHasFixedSize(true)
        return adapter
    }

}