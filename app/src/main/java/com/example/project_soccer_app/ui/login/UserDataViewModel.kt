package com.example.project_soccer_app.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.project_soccer_app.data.model.UserData

class UserDataViewModel() : ViewModel() {
    var liveUserData = MutableLiveData<UserData>()

    suspend fun saveUserData(userData: UserData){
        liveUserData.postValue(userData)
    }

}

class UserDataViewModelFactory(): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserDataViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserDataViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}