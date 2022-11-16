package com.example.project_soccer_app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.project_soccer_app.R
import com.example.project_soccer_app.databinding.FragmentSigninBinding
import com.example.project_soccer_app.data.model.UserData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class SignFragment: Fragment() {
    private var auth : FirebaseAuth? = null
    private var fireStore: FirebaseFirestore? = null

    lateinit var fragmentSigninBinding: FragmentSigninBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentSigninBinding = FragmentSigninBinding.inflate(layoutInflater)
        auth = Firebase.auth
        fireStore = FirebaseFirestore.getInstance()

        fragmentSigninBinding.okButton.setOnClickListener(){

            val email = fragmentSigninBinding.editEmail.getText().toString()
            val pw = fragmentSigninBinding.editPassword.getText().toString()

            createAccount(email, pw)
        }
        fragmentSigninBinding.noButton.setOnClickListener(){
            findNavController().popBackStack()
        }

        val view = fragmentSigninBinding.root
        return view
    }

    fun createAccount(email: String, pw: String){
        auth?.createUserWithEmailAndPassword(email, pw)?.addOnCompleteListener(requireActivity()){ it->
            if(it.isSuccessful){
                var userInitData = UserData()
                userInitData.uid = auth?.uid.toString()
                userInitData.email = auth?.currentUser?.email.toString()
                userInitData.character = "0"
                userInitData.nickname = "닉네임"

                fireStore?.collection("user_data")?.document(userInitData.uid)?.set(userInitData)

                Toast.makeText(requireContext(),"회원가입 성공", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }
            else{
                Toast.makeText(requireContext(),"회원가입 실패", Toast.LENGTH_SHORT).show()
            }
        }
    }
}