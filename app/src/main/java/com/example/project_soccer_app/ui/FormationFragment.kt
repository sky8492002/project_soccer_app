package com.example.project_soccer_app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.project_soccer_app.databinding.FragmentFormationBinding

class FormationFragment: Fragment() {
    lateinit var fragmentFormationBinding: FragmentFormationBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentFormationBinding = FragmentFormationBinding.inflate(layoutInflater)

        val view = fragmentFormationBinding.root
        return view
    }
}