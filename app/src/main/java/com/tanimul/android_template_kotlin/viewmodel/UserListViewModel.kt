package com.tanimul.android_template_kotlin.viewmodel


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tanimul.android_template_kotlin.data.models.response.UserResponse
import com.tanimul.android_template_kotlin.data.repository.UserListRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class UserListViewModel : ViewModel() {
    private val TAG: String = "UserListViewModel"
    private val repository: UserListRepository = UserListRepository()
    val users = MutableLiveData<List<UserResponse>?>()

    fun getUserList(since: Int,per_page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getUserList(since, per_page).body()
            if (response != null) {
                users.postValue(response)
            }

        }
    }

}