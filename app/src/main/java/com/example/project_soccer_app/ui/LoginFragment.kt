package com.example.project_soccer_app.ui

import android.app.Activity.RESULT_OK
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.project_soccer_app.R
import com.example.project_soccer_app.databinding.FragmentLoginBinding
import com.example.project_soccer_app.data.model.UserData
import com.example.project_soccer_app.viewModel.UserDataViewModel
import com.example.project_soccer_app.viewModel.UserDataViewModellFactory
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {
    lateinit var binding: FragmentLoginBinding
    private var auth: FirebaseAuth? = null
    private var fireStore: FirebaseFirestore? = null

    val getGoogleLoginResultText =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                var account: GoogleSignInAccount? = null
                try {
                    account = task.getResult(ApiException::class.java)
                    firebaseAuthWithGoogle(account!!.idToken)
                    Log.d("account.idToken", account.idToken.toString())
                } catch (e: ApiException) {
                    Toast.makeText(requireContext(), "Failed Google Login", Toast.LENGTH_SHORT).show()
                }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        auth = Firebase.auth
        fireStore = FirebaseFirestore.getInstance()

        binding.buttonLogin.setOnClickListener() {
            val email = binding.editEmail.getText().toString()
            val pw = binding.editPassword.getText().toString()
            login(email, pw)
        }
        binding.buttonGoogleLogin.setOnClickListener() {
            googleLogin()
        }
        binding.buttonSignin.setOnClickListener() {
            findNavController().navigate(R.id.action_loginFragment_to_signFragment)
        }

        val view = binding.root
        return view
    }

    fun googleLogin() {

        val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail().build()
        val signInIntent = GoogleSignIn.getClient(requireActivity(), options).signInIntent

        getGoogleLoginResultText.launch(signInIntent)
    }

    private fun firebaseAuthWithGoogle(idToken: String?) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth?.signInWithCredential(credential)?.addOnCompleteListener(requireActivity()
        ) { task ->
            if (task.isSuccessful) {
                Log.d("auth.uid", auth?.uid.toString())
                loadUserData()
                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
            }
        }
    }

    fun login(email: String, pw: String) {
        auth?.signInWithEmailAndPassword(email, pw)
            ?.addOnCompleteListener(requireActivity()) { it ->
                if (it.isSuccessful) {
                    Toast.makeText(requireContext(), "로그인 성공", Toast.LENGTH_SHORT).show()

                    loadUserData()

                    findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                } else {
                    Toast.makeText(requireContext(), "로그인 실패", Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun loadUserData() {

        fireStore?.collection("user_data")?.document(auth?.uid.toString())?.get()
            ?.addOnCompleteListener(requireActivity()) { it ->
                if (it.isSuccessful) {

                    if (it.result.get("uid") != auth?.uid.toString()) {
                        Toast.makeText(requireContext(), "사용자 정보 불러오기 실패", Toast.LENGTH_SHORT)
                            .show()
                        initUserData()
                        loadUserData()
                    } else {
                        saveUserData(it)
                    }
                } else {
                    Toast.makeText(requireContext(), "사용자 정보 불러오기 실패", Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun initUserData() {
        var userInitData = UserData()
        userInitData.uid = auth?.uid.toString()
        userInitData.email = auth?.currentUser?.email.toString()
        fireStore?.collection("user_data")?.document(userInitData.uid)?.set(userInitData)
    }

    fun saveUserData(it: Task<DocumentSnapshot>) {
        var userData = UserData()
        userData.email = it.result.get("email").toString()
        userData.uid = it.result.get("uid").toString()
        userData.character = it.result.get("character").toString()
        userData.uid = it.result.get("uid").toString()

        // viewModel에 liveData로 userData 객체추가

        var viewModel = ViewModelProvider(
            requireActivity(),
            UserDataViewModellFactory()
        ).get(UserDataViewModel::class.java)
        // activity와 viewmodel 공유 (동일한 viewmodel 반환)


        // 코루틴 사용(데이터 viewmodel에 저장 시 딜레이 방지)
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.saveUserData(userData)
        }
    }
}