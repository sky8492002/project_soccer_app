package com.example.project_soccer_app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.project_soccer_app.R
import com.example.project_soccer_app.databinding.FragmentHomeBinding
import com.example.project_soccer_app.data.model.UserData
import com.example.project_soccer_app.viewModel.UserDataViewModel
import com.example.project_soccer_app.viewModel.UserDataViewModellFactory

class HomeFragment: Fragment()  {
    lateinit var fragmentHomeBinding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentHomeBinding = FragmentHomeBinding.inflate(layoutInflater)

        // viewmodel 사용하여 로그인한 userData 참조하여 ui 업데이트

        var viewModel = ViewModelProvider(requireActivity(), UserDataViewModellFactory()).get(
            UserDataViewModel::class.java)
        // activity와 viewmodel 공유 (동일한 viewmodel 반환)

        viewModel.liveUserData.observe(requireActivity()){
            userData -> fragmentHomeBinding.testTextView.setText(userData.email)
        }

        fragmentHomeBinding.minigameButton.setOnClickListener(){
            findNavController().navigate(R.id.action_homeFragment_to_minigameFragment)
        }

        fragmentHomeBinding.myInfoButton.setOnClickListener(){
            findNavController().navigate(R.id.action_homeFragment_to_myInfoFragment)
        }

        fragmentHomeBinding.lobbyButton.setOnClickListener(){
            findNavController().navigate(R.id.action_homeFragment_to_lobbyFragment)
        }

        val view = fragmentHomeBinding.root
        return view
    }
}