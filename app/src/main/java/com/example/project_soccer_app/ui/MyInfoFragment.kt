package com.example.project_soccer_app.ui

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project_soccer_app.R
import com.example.project_soccer_app.adapter.AdapterProfileImageList
import com.example.project_soccer_app.databinding.DialogProfileImagePickBinding
import com.example.project_soccer_app.databinding.FragmentMyinfoBinding
import com.example.project_soccer_app.data.model.ProfileImageData
import com.example.project_soccer_app.data.model.UserData
import com.example.project_soccer_app.viewModel.UserDataViewModel
import com.example.project_soccer_app.viewModel.UserDataViewModelFactory
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyInfoFragment: Fragment() {
    lateinit var fragmentMyinfoBinding: FragmentMyinfoBinding
    lateinit var dialogProfileImagePickBinding: DialogProfileImagePickBinding

    lateinit var fireStore: FirebaseFirestore
    lateinit var userDataViewModel: UserDataViewModel
    lateinit var myUid: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentMyinfoBinding = FragmentMyinfoBinding.inflate(layoutInflater)
        dialogProfileImagePickBinding = DialogProfileImagePickBinding.inflate(layoutInflater)
        fireStore = FirebaseFirestore.getInstance()
        userDataViewModel = ViewModelProvider(requireActivity(), UserDataViewModelFactory()).get(
            UserDataViewModel::class.java)
        // activity와 viewmodel 공유 (동일한 viewmodel 반환)

        val profileImageSelectRecyclerview = dialogProfileImagePickBinding.profileImageSelectRecyclerview

        updateViewByUserData() // 유저 정보를 viewmodel에서 불러와 view 업데이트

        fragmentMyinfoBinding.userProfileImageView.setOnClickListener(){
            val adapter = setProfileImageSelectRecyclerView(profileImageSelectRecyclerview)
            showProfileImagePickDialog(adapter) // 프로필 이미지 선택 정보를 얻기 위해 필요한 recyclerview adapter 전달
        }


        val view = fragmentMyinfoBinding.root
        return view
    }

    fun setProfileImageSelectRecyclerView(recyclerView: RecyclerView):AdapterProfileImageList{
        recyclerView.adapter = AdapterProfileImageList(requireContext(), setProfileImageList()) // adapter에 uid도 넘겨줌
        val adapter = recyclerView.adapter as AdapterProfileImageList
        val linearLayoutManager = GridLayoutManager(requireContext(), 3) // 한 줄에 3개만 들어가도록 제한
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.setHasFixedSize(true)

        return adapter
    }

    // 사용할 수 있는 프로필 리스트를 만듬
    @SuppressLint("UseCompatLoadingForDrawables")
    fun setProfileImageList():List<ProfileImageData>{
        var list:MutableList<ProfileImageData> = mutableListOf()

        list.add(ProfileImageData(character = "0", image = requireContext().getDrawable(R.drawable.ball)!!))
        list.add(ProfileImageData(character = "1", image = requireContext().getDrawable(R.drawable.player1)!!))
        list.add(ProfileImageData(character = "2", image = requireContext().getDrawable(R.drawable.player2)!!))
        return list.toList()
    }

    fun showProfileImagePickDialog(adapter: AdapterProfileImageList){
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        if(dialogProfileImagePickBinding.root.parent != null){
            (dialogProfileImagePickBinding.root.parent as ViewGroup).removeView(
                dialogProfileImagePickBinding.root
            ) // 쓰기 위해 혹시라도 남아 있는 view 삭제
            dialog.dismiss()
        }

        dialog.setContentView(dialogProfileImagePickBinding.root)
        var params: WindowManager.LayoutParams = dialog.getWindow()!!.getAttributes()
        params.width = (requireContext().getResources()
            .getDisplayMetrics().widthPixels * 0.9).toInt() // device의 가로 길이 비례하여 결정
        params.height = (requireContext().getResources()
            .getDisplayMetrics().heightPixels * 0.8).toInt() // device의 세로 길이에 비례하여  결정
        dialog.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.getWindow()!!.setAttributes(params)
        dialog.getWindow()!!.setGravity(Gravity.CENTER)
        dialog.setCancelable(true)
        dialog.show() // 프로필 이미지 선택할 수 있는 dialog 띄움

        // 확인 버튼 누를 경우 userData 변경 후 서버에 업데이트
        dialogProfileImagePickBinding.okButton.setOnClickListener(){
            val profileImageDataList = adapter.getProfileImageDataList()
            for(profileImageData in profileImageDataList){
                // 사용자가 선택한 프로필 이미지를 찾아 userData 변경 후 서버에 업데이트
                if(profileImageData.selected == true){
                    var updatedUserData: UserData

                    updatedUserData = userDataViewModel.liveUserData.value!!
                    updatedUserData.character = profileImageData.character
                    fireStore.collection("user_data").document(myUid).set(updatedUserData)
                    // 업데이트 이후 다시 받아 viewmodel에 저장
                    loadUserData()
                    // 유저 정보를 viewmodel에서 불러와 view 업데이트
                    updateViewByUserData()

                    // 무한루프->데이터 초과 원인 (observe는 liveuserdata가 업데이트될때마다 실행되기때문)
//                    userDataViewModel.liveUserData.observe(requireActivity()){
//                        userData -> updatedUserData = userData
//                        updatedUserData.character = profileImageData.character
//
//                        fireStore.collection("user_data").document(myUid).set(updatedUserData)
//
//                        // 업데이트 이후 다시 받아 viewmodel에 저장
//                        loadUserData()
//                        // 유저 정보를 viewmodel에서 불러와 view 업데이트
//                        updateViewByUserData()
//
//                    }

                }
            }

            if(dialogProfileImagePickBinding.root.parent != null){
                (dialogProfileImagePickBinding.root.parent as ViewGroup).removeView(
                    dialogProfileImagePickBinding.root
                ) // 쓰기 위해 혹시라도 남아 있는 view 삭제
                dialog.dismiss()
            }
        }
        dialogProfileImagePickBinding.noButton.setOnClickListener(){
            if(dialogProfileImagePickBinding.root.parent != null){
                (dialogProfileImagePickBinding.root.parent as ViewGroup).removeView(
                    dialogProfileImagePickBinding.root
                ) // 쓰기 위해 혹시라도 남아 있는 view 삭제
                dialog.dismiss()
            }
        }
    }

    fun loadUserData(){
        if(getActivity() != null && isAdded()){// fragment가 activity에 attach되었을 때만 실행
            fireStore.collection("user_data").document(myUid).get()
                .addOnCompleteListener(requireActivity()){ it->
                    if(it.isSuccessful){
                        saveUserData(it)
                    } else{
                        Toast.makeText(requireContext(), "사용자 정보 불러오기 실패", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    fun saveUserData(it: Task<DocumentSnapshot>){
        var userData = UserData()
        userData.email = it.result.get("email").toString()
        userData.uid = it.result.get("uid").toString()
        userData.character = it.result.get("character").toString()
        userData.uid = it.result.get("uid").toString()

        // viewModel에 liveData로 userData 객체추가

        // 코루틴 사용(데이터 viewmodel에 저장 시 딜레이 방지)
        CoroutineScope(Dispatchers.Main).launch {
            userDataViewModel.saveUserData(userData)
        }
    }

    fun updateViewByUserData(){
        if(getActivity() != null && isAdded()) {// fragment가 activity에 attach되었을 때만 실행
            userDataViewModel.liveUserData.observe(requireActivity()) { userData ->
                if (userData.character == "0") {
                    fragmentMyinfoBinding.userProfileImageView.setImageResource(R.drawable.ball)
                }
                if (userData.character == "1") {
                    fragmentMyinfoBinding.userProfileImageView.setImageResource(R.drawable.player1)
                }
                if (userData.character == "2") {
                    fragmentMyinfoBinding.userProfileImageView.setImageResource(R.drawable.player2)
                }

                myUid = userData.uid
            }
        }
    }
}