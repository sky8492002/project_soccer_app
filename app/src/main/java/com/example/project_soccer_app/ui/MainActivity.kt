package com.example.project_soccer_app.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.project_soccer_app.R
import com.example.project_soccer_app.databinding.FragmentFormationBinding
import com.example.project_soccer_app.viewModel.UserDataViewModel
import com.example.project_soccer_app.viewModel.UserDataViewModellFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var viewModel = ViewModelProvider(this, UserDataViewModellFactory()).get(UserDataViewModel::class.java)
        // fragment와 viewmodel 공유 (동일한 viewmodel 반환)
    }
}