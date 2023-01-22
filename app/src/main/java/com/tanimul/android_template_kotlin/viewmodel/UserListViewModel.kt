package com.tanimul.android_template_kotlin.viewmodel


import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.tanimul.android_template_kotlin.data.database.TemplateDatabase
import com.tanimul.android_template_kotlin.data.models.response.UserModel
import com.tanimul.android_template_kotlin.data.models.response.UserResponse
import com.tanimul.android_template_kotlin.data.repository.UserListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserListViewModel(application: Application) : AndroidViewModel(application) {
    private val TAG: String = "UserListViewModel"
    private val repository: UserListRepository
    val users = MutableLiveData<List<UserResponse>?>()
    val showAllUser: LiveData<List<UserModel>>

    init {
        val userDao = TemplateDatabase.getDatabase(application).userDao()
        repository = UserListRepository(userDao)
        showAllUser = repository.showAllUser
    }

    fun getUserList(since: Int, per_page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getUserList(since, per_page).body()
            if (response != null) {
//                users.postValue(response)
                addUserList(response)
            }

        }
    }

    private fun addUserList(userList: List<UserResponse>) = viewModelScope.launch(Dispatchers.IO) {
        Log.d(TAG, "addUserList: ${userList.size}")
        for (data in userList) {
            val userModel = UserModel(
                login = data.login,node_id = data.nodeId, avatar_url = data.avatarUrl
            )
            repository.addUser(userModel)
        }
    }
}